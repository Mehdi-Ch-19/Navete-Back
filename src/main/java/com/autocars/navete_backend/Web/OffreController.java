package com.autocars.navete_backend.Web;


import com.autocars.navete_backend.Entity.AutoCar;
import com.autocars.navete_backend.Entity.Offre;
import com.autocars.navete_backend.Service.OffreService;
import com.autocars.navete_backend.Utility.Dto.SocietCreationDto;
import com.autocars.navete_backend.Utility.Exeption.OffreNotFoundExeption;
import com.autocars.navete_backend.Utility.Exeption.VilleNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offre")
public class OffreController {

    private final OffreService offreService;

    public OffreController(OffreService offreService){
        this.offreService = offreService;
    }

    @GetMapping("{offreid}")
    public ResponseEntity<?> getOffre(@PathVariable Long offreid){
        try {
            Offre offre = offreService.getOffre(offreid);
            return new ResponseEntity<>(offre,HttpStatus.OK);
        }catch (OffreNotFoundExeption offreNotFoundExeption){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("{offreid}/societe")
    public ResponseEntity<SocietCreationDto> getSocieteByOffreid(@PathVariable Long offreid){
        SocietCreationDto societCreationDto = offreService.getsocitetebyoffeeid(offreid);
        return new ResponseEntity<>(societCreationDto,HttpStatus.OK);
    }
    @GetMapping("{offreid}/autocar")
    public ResponseEntity<AutoCar> getautocarbyoffre(@PathVariable Long offreid){
        try {

            AutoCar autoCar = offreService.getautocarbyoffre(offreid);
            return new ResponseEntity<>(autoCar,HttpStatus.OK);
        }catch (OffreNotFoundExeption offreNotFoundExeption){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/filter/villedepart/{v1}/villearrive/{v2}")
    public ResponseEntity<List<Offre>> getoffrebyvilles(
            @PathVariable Long v1 ,
            @PathVariable Long v2 ) throws VilleNotFoundExeption {
        List<Offre> offres = offreService.rechercherParVille2(v1,v2);
        return new ResponseEntity<>(offres,HttpStatus.OK);
    }

}
