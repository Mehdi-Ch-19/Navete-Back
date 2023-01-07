package com.autocars.navete_backend.Service;

import com.autocars.navete_backend.Entity.Client;
import com.autocars.navete_backend.Entity.Offre;
import com.autocars.navete_backend.Enum.OffreStatus;
import com.autocars.navete_backend.Repository.ClientRepository;
import com.autocars.navete_backend.Utility.Dto.ClientCreationDto;
import com.autocars.navete_backend.Utility.Exeption.ClientNotFoundExeption;
import com.autocars.navete_backend.Utility.Exeption.OffreNotFoundExeption;
import com.autocars.navete_backend.Utility.Mapper.ClientCreationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@AllArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientCreationMapper mapper;
    private final OffreService offreService;
    public Long addClient(ClientCreationDto clientDto){
        Client client = mapper.convertDtoToClient(clientDto);
        clientRepository.save(client);
        return client.getId();
    }

    public Client getClient(Long id) throws ClientNotFoundExeption{
        return clientRepository.findById(id).orElseThrow(()->new ClientNotFoundExeption("client not found"));
    }
    public Long validateClient( String email ,String password ) throws ClientNotFoundExeption{
        Client client = clientRepository.findClientByEmailAndPassword(email,password);
        if(client == null){
            throw new ClientNotFoundExeption("Client not found");
        }else return client.getId();
    }

    public void addOffreToClient(Client client , Offre offre){
        if(offre.getNombreClientAbonne() < offre.getNombreAbonnesVollue()) {
            client.getOffreList().add(offre);
            clientRepository.save(client);
        }

    }
    public String AbonnerOffre(Long clientid , Long offreid){
        String result = "";
        try {
            Client client = getClient(clientid);
            Offre offre = offreService.getOffre(offreid);
            addOffreToClient(client,offre);
            boolean abonnment =  offreService.addClientToOffre(client,offre);
            if (abonnment){
                result = "OK";
            }else {
                result = "les Places sont remplis";
            }
        }catch (ClientNotFoundExeption | OffreNotFoundExeption exception){
            log.error(exception.getMessage());
            result = exception.getMessage();

        }
        return result;
    }



}
