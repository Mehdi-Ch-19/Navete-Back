package com.autocars.navete_backend.Service;


import com.autocars.navete_backend.Entity.*;
import com.autocars.navete_backend.Enum.OffreStatus;
import com.autocars.navete_backend.Repository.DemandeRepository;
import com.autocars.navete_backend.Repository.OffreRepository;
import com.autocars.navete_backend.Repository.SocieteRepository;
import com.autocars.navete_backend.Utility.Dto.SocietCreationDto;
import com.autocars.navete_backend.Utility.Exeption.AutoCarNotFoundExeption;
import com.autocars.navete_backend.Utility.Exeption.OffreNotFoundExeption;
import com.autocars.navete_backend.Utility.Exeption.SocieteNoFoundExeption;
import com.autocars.navete_backend.Utility.Exeption.VilleNotFoundExeption;
import com.autocars.navete_backend.Utility.Mapper.SocieteCreationMapper;
import lombok.AllArgsConstructor;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OffreService {

    private final OffreRepository offreRepository;
    private final VilleService villeService;
    private final SocieteService societeService;
    private final SocieteRepository societeRepository;
    private  final SocieteCreationMapper societeCreationMapper;
    private final DemandeRepository demandeRepository;
    private final AutoCarService autoCarService;
    public Offre createOffre(Offre offre , Long societeid){


        Societe societe = societeService.getSociete(societeid);
        offre.setSociete(societe);
        societeRepository.save(societe);
        return offreRepository.save(offre);
    }

    public Offre getOffre(Long id) throws OffreNotFoundExeption {
        return offreRepository.findById(id).orElseThrow(()->new OffreNotFoundExeption("offre not found"));
    }
    public SocietCreationDto getsocitetebyoffeeid(Long offreid){
        Long societe_id = offreRepository.findsocietebyoffreid(offreid);
        return societeCreationMapper.convertSociteToDto( societeService.getSociete(societe_id));

    }
    public AutoCar getautocarbyoffre(Long offreid) throws OffreNotFoundExeption {
        return getOffre(offreid).getAutoCar();
    }
    public boolean addClientToOffre(Client client , Offre offre){
        /*le nombre des client qui v'ont abonnes doit etre inferieur ou eguale le nombre des abonnes volu
        par la societe
        * */
        if(offre.getNombreClientAbonne() < offre.getNombreAbonnesVollue()){
            offre.getClient().add(client);
            offre.setNombreClientAbonne(offre.getNombreClientAbonne() + 1 );
            offreRepository.save(offre);
            return true;
        }else {
            offre.setStatus(OffreStatus.FERMEE);
            offreRepository.save(offre);
            return false;
        }

    }
    public void deleteoffre(Long offreid) throws OffreNotFoundExeption {
        offreRepository.delete(getOffre(offreid));
    }
    public List<Offre> rechercherParVille(Long villedepartt,Long villearivee , int page,int size) throws VilleNotFoundExeption {
        Ville villedepart = villeService.getVille(villedepartt);
        Ville villearive = villeService.getVille(villearivee);
        Page<Offre> offrePage = offreRepository.findOffreByVilleDepartAndVilleArrive(villedepart,villearive, PageRequest.of(page,size));
        int TotalPages = offrePage.getTotalPages();
        return offrePage.stream().collect(Collectors.toList());
    }
    public List<Offre> rechercherParVille2(Long villedepartt,Long villearivee ) throws VilleNotFoundExeption {
        Ville villedepart = villeService.getVille(villedepartt);
        Ville villearive = villeService.getVille(villearivee);
        List<Offre> offreList = offreRepository.findOffreByVilleDepartAndVilleArrive(villedepart,villearive);
         return offreList;
    }

}
