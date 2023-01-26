package com.autocars.navete_backend.Web;


import com.autocars.navete_backend.Entity.Demande;
import com.autocars.navete_backend.Service.GestionDemandeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/demande")
@AllArgsConstructor

public class Demandecontroller {
    private final GestionDemandeService demandeService;

    @GetMapping()
    public ResponseEntity<List<Demande>> getalldemande(){
        List<Demande> demandeList = demandeService.getalldemande();
        return new ResponseEntity<>(demandeList, HttpStatus.OK);
    }


}
