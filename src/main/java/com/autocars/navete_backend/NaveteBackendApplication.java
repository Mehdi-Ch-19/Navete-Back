package com.autocars.navete_backend;

import com.autocars.navete_backend.Entity.AutoCar;
import com.autocars.navete_backend.Entity.Societe;
import com.autocars.navete_backend.Entity.Ville;
import com.autocars.navete_backend.Service.AutoCarService;
import com.autocars.navete_backend.Service.OffreService;
import com.autocars.navete_backend.Service.SocieteService;
import com.autocars.navete_backend.Service.VilleService;
import com.autocars.navete_backend.Utility.Dto.SocietCreationDto;
import com.autocars.navete_backend.Utility.Mapper.SocieteCreationMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class NaveteBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NaveteBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(VilleService villeService,
                            SocieteService societeService,
                            AutoCarService autoCarService,
                            OffreService offreService,
                            SocieteCreationMapper societeCreationMapper
                            )
    {
        return args ->{
            SocietCreationDto societe = SocietCreationDto.builder()
                    .nom("Tesla")
                    .email("Tesla@gmail.com")
                    .password("tesla123")
                    .build();
            societeService.createSociete(societe,null);
            Stream.of("Rabat", "Fes", "Kenitra").forEach(name -> {
                Ville ville = Ville.builder()
                        .nom(name)
                        .build();
                villeService.addVille(ville);
            });
            Societe societe1 = societeCreationMapper.convertDtoToSociete(societe);
            AutoCar autoCar = AutoCar.builder()
                    .modele("Mercedes")
                    .nombresiege(30)
                    .haveWifi(true)
                    .societe(societe1)
                    .build();
            //autoCarService.addAutoCar(autoCar);
        };


    }
}
