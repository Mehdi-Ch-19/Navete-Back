package com.autocars.navete_backend.Service;

import com.autocars.navete_backend.Entity.AutoCar;
import com.autocars.navete_backend.Entity.Societe;
import com.autocars.navete_backend.Repository.AutoCarRepository;
import com.autocars.navete_backend.Utility.Exeption.AutoCarNotFoundExeption;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AutoCarService {

    private final AutoCarRepository autoCarRepository;
    private final  SocieteService societeService;

    public List<AutoCar> getAllAutocarsBySociete(Long societeid){
        Societe societe = societeService.getSociete(societeid);
        return autoCarRepository.findAllBySociete(societe);
    }

    public AutoCar addAutoCar(AutoCar autoCar){
        return autoCarRepository.save(autoCar);
    }

    public AutoCar getAutocar(Long id) throws AutoCarNotFoundExeption {
        return autoCarRepository.findById(id).orElseThrow(()-> new AutoCarNotFoundExeption("AutoCar not Found"));
    }
    public void deleteAutoCar(Long id) throws AutoCarNotFoundExeption {
        autoCarRepository.delete(getAutocar(id));
    }
    /* autocar est la nouvelle instance (les nouveaux info ) , autocarid est old instance
    * */
    public AutoCar updateAutoCar(AutoCar autoCar , Long autocarid , Long societeid) throws AutoCarNotFoundExeption {
        try {
            AutoCar autoCar1 = getAutocar(autocarid);
            Societe societe = societeService.getSociete(societeid);
            autoCar1.setModele(autoCar.getModele());
            autoCar1.setHaveWifi(autoCar.isHaveWifi());
            autoCar1.setNombresiege(autoCar.getNombresiege());
            autoCar1.setSociete(societe);
            autoCarRepository.save(autoCar1);
            return autoCar1;
        }catch (AutoCarNotFoundExeption autoCarNotFoundExeption){
            log.error(autoCarNotFoundExeption.getMessage());
            return null;
        }

    }
}
