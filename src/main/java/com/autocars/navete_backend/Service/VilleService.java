package com.autocars.navete_backend.Service;


import com.autocars.navete_backend.Entity.Ville;
import com.autocars.navete_backend.Repository.VilleRepository;
import com.autocars.navete_backend.Utility.Exeption.AutoCarNotFoundExeption;
import com.autocars.navete_backend.Utility.Exeption.VilleNotFoundExeption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VilleService {

    private final VilleRepository villeRepository;

    public List<Ville> getAllVille(){
        return villeRepository.findAll();
    }

    public Ville addVille(Ville ville){
        return villeRepository.save(ville);
    }

    public Ville getVille(Long id) throws VilleNotFoundExeption {
        return villeRepository.findById(id).orElseThrow(()-> new VilleNotFoundExeption("AutoCar not Found"));
    }
    public void deleteVille(Long id) throws VilleNotFoundExeption {
        villeRepository.delete(getVille(id));
    }


}
