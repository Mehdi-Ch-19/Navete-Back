package com.autocars.navete_backend.Service;


import com.autocars.navete_backend.Entity.Logo;
import com.autocars.navete_backend.Entity.Societe;
import com.autocars.navete_backend.Repository.SocieteRepository;
import com.autocars.navete_backend.Utility.Dto.SocietCreationDto;
import com.autocars.navete_backend.Utility.Exeption.ClientNotFoundExeption;
import com.autocars.navete_backend.Utility.Exeption.SocieteNoFoundExeption;
import com.autocars.navete_backend.Utility.Mapper.SocieteCreationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SocieteService {

    private final SocieteRepository societeRepository;
    private final SocieteCreationMapper mapper;

    public Societe getSociete(Long id){
        return societeRepository.findById(id).orElseThrow(()->new SocieteNoFoundExeption("societe not found"));
    }



    public Long createSociete(SocietCreationDto societCreationDto, MultipartFile logo2) throws IOException {
        Societe societe = mapper.convertDtoToSociete(societCreationDto);
        if(logo2 != null){
                Logo logo1 = Logo.builder()
                        .name(logo2.getOriginalFilename())
                        .type(logo2.getContentType())
                        .image(logo2.getBytes())
                        .societe(societe)
                        .build();
                societe.setLogo(logo1);
            societeRepository.save(societe);
        }else {
            Logo logo = Logo.builder()
                    .societe(societe)
                    .build();
            societe.setLogo(logo);
            societeRepository.save(societe);
        }

        return societe.getId();
    }
    public Long validateSociete( String email ,String password ) throws ClientNotFoundExeption {
        Societe societe = societeRepository.findSocieteByEmailAndPassword(email,password);
        if(societe == null){
            throw new ClientNotFoundExeption("Client not found");
        }else return societe.getId();
    }

}
