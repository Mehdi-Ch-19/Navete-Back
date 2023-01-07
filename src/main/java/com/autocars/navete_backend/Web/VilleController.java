package com.autocars.navete_backend.Web;


import com.autocars.navete_backend.Entity.Ville;
import com.autocars.navete_backend.Service.VilleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ville")
public class VilleController {

    private final VilleService villeService;

    public VilleController(VilleService villeService1){
        this.villeService = villeService1;
    }
    @PostMapping
    public ResponseEntity<Ville> createVille(@RequestBody Ville ville){
        villeService.addVille(ville);
        return new ResponseEntity<>(ville, HttpStatus.OK);

    }
    @GetMapping
    public ResponseEntity<List<Ville>> getVilles(){
        List<Ville> villeList = villeService.getAllVille();
        return  new ResponseEntity<>(villeList,HttpStatus.OK);
    }

}
