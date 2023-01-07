package com.autocars.navete_backend.Utility.Mapper;

import com.autocars.navete_backend.Entity.Client;
import com.autocars.navete_backend.Utility.Dto.ClientCreationDto;
import org.springframework.stereotype.Service;

@Service
public class ClientCreationMapper {

    public ClientCreationDto convertClientToDto(Client client){
        ClientCreationDto clientDto = new ClientCreationDto();
        clientDto.setNom(client.getNom());
        clientDto.setEmail(client.getEmail());
        clientDto.setPassword(client.getPassword());
        clientDto.setPrenom(client.getPrenom());
        return clientDto;
    }
    public Client convertDtoToClient(ClientCreationDto clientDto){
        return Client.builder()
                .nom(clientDto.getNom())
                .email(clientDto.getEmail())
                .password(clientDto.getPassword())
                .prenom(clientDto.getPrenom())
                .build();


    }
}
