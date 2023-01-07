package com.autocars.navete_backend.Web;

import com.autocars.navete_backend.Entity.AutoCar;
import com.autocars.navete_backend.Entity.Offre;
import com.autocars.navete_backend.Entity.Societe;
import com.autocars.navete_backend.Service.AutoCarService;
import com.autocars.navete_backend.Service.OffreService;
import com.autocars.navete_backend.Service.SocieteService;
import com.autocars.navete_backend.Utility.Dto.SocietCreationDto;
import com.autocars.navete_backend.Utility.Exeption.AutoCarNotFoundExeption;
import com.autocars.navete_backend.Utility.Exeption.ClientNotFoundExeption;
import com.autocars.navete_backend.Utility.Exeption.SocieteNoFoundExeption;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/societe")
@Slf4j
public class SocieteController {

    private final SocieteService service;
    private final AutoCarService autoCarService;
    private final OffreService offreService;
    public SocieteController(SocieteService societeService , AutoCarService service , OffreService offreService1)
    {
        this.service= societeService;
        this.autoCarService = service;
        this.offreService = offreService1;
    }

    /*
    * on va recuperer les donnes sous format key - value (form data)
    * le Client peut affecter un logo (requierd = false )
    * si tout va bien on va retourner le id de la societe
    * */
    @PostMapping()
    public ResponseEntity<Long> createSociete(@RequestParam("model") String societCreation,
                                                @RequestParam(value = "image",required = false) MultipartFile logo) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        SocietCreationDto societCreationDto = objectMapper.readValue(societCreation,SocietCreationDto.class);
        Long id =  service.createSociete(societCreationDto,logo);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> AuthSociete(@RequestBody Map<String ,Object> societemap){
        Map<String,Object> responce = new HashMap<>();
        try {
            String email = (String) societemap.get("email");
            String password = (String) societemap.get("password");
            Long id = service.validateSociete(email,password);
            responce.put("id",id);
            responce.put("success",true);
            responce.put("message","Client is authenticated");
            return new ResponseEntity<Object>(responce,HttpStatus.OK);
        }catch (ClientNotFoundExeption clientNotFoundExeption){
            log.error(clientNotFoundExeption.getMessage());
            responce.put("success",false);
            responce.put("message",clientNotFoundExeption.getMessage());
            return new ResponseEntity<Object>(responce,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSociete(@PathVariable Long id){
        try {
            Societe societe = service.getSociete(id);
            Map<String,Object> data = new HashMap<>();
            data.put("data",societe);
            return new ResponseEntity<Object>(data,HttpStatus.OK);
        }catch (SocieteNoFoundExeption societeNoFoundExeption){
            log.error(societeNoFoundExeption.getMessage());
            HashMap<String ,String> hashMap = new HashMap<>();
            hashMap.put("message",societeNoFoundExeption.getMessage());
            return new ResponseEntity<>(hashMap,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("{id}/Autocar")
    public ResponseEntity<Object> allAutocarBySociete(@PathVariable Long id){
        List<AutoCar> autoCars = autoCarService.getAllAutocarsBySociete(id);
        Map<String,Object> map = new HashMap<>();
        map.put("data",autoCars);
        map.put("Nombre des AutoCar",autoCars.size());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    @PostMapping("/{id}/AutoCar")
    public ResponseEntity<Object> addAutoCarToSociete(@RequestBody  AutoCar autoCar , @PathVariable Long id ){
        Societe societe = service.getSociete(id);
        AutoCar autoCar1 = AutoCar.builder()
                .id(autoCar.getId())
                .modele(autoCar.getModele())
                .haveWifi(autoCar.isHaveWifi())
                .nombresiege(autoCar.getNombresiege())
                .societe(societe)
                .build();
        autoCarService.addAutoCar(autoCar1);
        return new ResponseEntity<>(autoCar1,HttpStatus.OK);
    }

    @DeleteMapping("{id}/AutoCar/{autocarid}")
    public ResponseEntity<?> deleteAutoCar( @PathVariable Long autocarid) throws AutoCarNotFoundExeption {
        try {
            autoCarService.deleteAutoCar(autocarid);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (AutoCarNotFoundExeption autoCarNotFoundExeption){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}/AutoCar/{autocarid}")
    public ResponseEntity<AutoCar> updateAutoCar(@RequestBody  AutoCar autoCar , @PathVariable Long autocarid ,
                                                 @PathVariable Long id){
        try {
            autoCarService.updateAutoCar(autoCar,autocarid,id) ;
            return new ResponseEntity<>(autoCar,HttpStatus.OK);
        } catch (AutoCarNotFoundExeption e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{id}/Offre")
    public ResponseEntity<Offre> createOffre(@RequestBody Offre offre ,@PathVariable Long id ){
        try {
            Offre offree  = offreService.createOffre(offre,id);
            return new ResponseEntity<>(offree,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
