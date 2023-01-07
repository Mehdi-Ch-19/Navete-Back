package com.autocars.navete_backend.Utility.Mapper;

import com.autocars.navete_backend.Entity.Societe;
import com.autocars.navete_backend.Utility.Dto.SocietCreationDto;
import org.springframework.stereotype.Service;

@Service
public class SocieteCreationMapper {
    public Societe convertDtoToSociete(SocietCreationDto societCreationDto){
        return Societe.builder()
                .nom(societCreationDto.getNom())
                .email(societCreationDto.getEmail())
                .password(societCreationDto.getPassword())
                .logo(societCreationDto.getLogo())
                .build();
    }

}
