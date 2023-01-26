package com.autocars.navete_backend.Web;


import com.autocars.navete_backend.Entity.Client;
import com.autocars.navete_backend.Entity.LogDemande;
import com.autocars.navete_backend.Service.ClientService;
import com.autocars.navete_backend.Service.GestionDemandeService;
import com.autocars.navete_backend.Utility.Dto.ClientCreationDto;
import com.autocars.navete_backend.Utility.Exeption.ClientDemandeExistsDejaExeption;
import com.autocars.navete_backend.Utility.Exeption.ClientNotFoundExeption;
import com.autocars.navete_backend.Utility.Mapper.ClientCreationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/client")
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final GestionDemandeService gestionDemandeService;
    private final ClientCreationMapper clientCreationMapper;

    public ClientController(ClientService service , GestionDemandeService gestionDemandeService ,  ClientCreationMapper clientCreationMapper) {
        this.clientService = service;
        this.gestionDemandeService = gestionDemandeService;
        this.clientCreationMapper = clientCreationMapper;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientCreationDto> getclient(@PathVariable Long id){
        Client client = clientService.getClient(id);
        ClientCreationDto clientCreationDto = clientCreationMapper.convertClientToDto(client);
        return new ResponseEntity<>(clientCreationDto,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> signup(@RequestBody ClientCreationDto clientCreationDto){
        Long id =   clientService.addClient(clientCreationDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }/*
    @GetMapping
    public ResponseEntity<>
*/

    @PostMapping("/login")
    public ResponseEntity<Object> AuthClient(@RequestBody Map<String ,Object> userMap){
        Map<String,Object> responce = new HashMap<>();
        try {
            String email = (String) userMap.get("email");
            String password = (String) userMap.get("password");
            Long id = clientService.validateClient(email,password);
            responce.put("id",id);
            responce.put("success",true);
            responce.put("message","Client is authenticated");
            return new ResponseEntity<>(responce,HttpStatus.OK);
        }catch (ClientNotFoundExeption clientNotFoundExeption){
            log.error(clientNotFoundExeption.getMessage());
            responce.put("success",false);
            responce.put("message",clientNotFoundExeption.getMessage());
            return new ResponseEntity<>(responce,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{clientid}/offre/{offreid}")
    public ResponseEntity<Object> AbonnerOffre(@PathVariable Long clientid ,@PathVariable Long offreid ){
        String result = clientService.AbonnerOffre(clientid,offreid);
        Map<String ,Object> objectHashMap = new HashMap<>();

        if(Objects.equals(result, "OK")){
            objectHashMap.put("resultat",result);
            objectHashMap.put("offreId",offreid);
            objectHashMap.put("clientId",clientid);
            return new ResponseEntity<>(objectHashMap,HttpStatus.OK);
        }else if ( Objects.equals(result, "les Places sont remplis")){
            objectHashMap.put("resultat",result);
            return new ResponseEntity<>(objectHashMap,HttpStatus.CONFLICT);
        }else {
            objectHashMap.put("resultat",result);
            return new ResponseEntity<>(objectHashMap,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{clientid}/demande")
    public ResponseEntity<Object> addDemande(@RequestBody LogDemande demande , @PathVariable Long clientid){
        Map<String ,Object> result = new HashMap<>();

        try {
            gestionDemandeService.ajouterDemande(demande , clientid);
            result.put("demande",demande.getId());
            result.put("message","succes");
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (ClientDemandeExistsDejaExeption existsDejaExeption){
            String message = "Client a fait deja une demande avec cette critere";
            result.put("message",message);
            return new ResponseEntity<>(result,HttpStatus.CONFLICT);
        }
    }
    /*get all the subscribe offres id */
    @GetMapping("{clientid}/offre")
    public ResponseEntity<List<Long>> abonneeoffreid(@PathVariable Long clientid){
        List<Long> offresid = clientService.toutlesoffreabonne(clientid);
        return new ResponseEntity<>(offresid,HttpStatus.OK);
    }
}
