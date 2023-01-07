package com.autocars.navete_backend.Repository;

import com.autocars.navete_backend.Entity.Demande;
import com.autocars.navete_backend.Entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface DemandeRepository extends JpaRepository<Demande,Long> {
    Demande findDemandesByVilleDepartAndVilleArriveAndAndHeuredepartAndAndHeurearrive(
            Ville villedepart ,
            Ville villearrive ,
            LocalTime  heuredepart ,
            LocalTime heurearrive);
}
