package com.autocars.navete_backend.Repository;

import com.autocars.navete_backend.Entity.Client;
import com.autocars.navete_backend.Entity.LogDemande;
import com.autocars.navete_backend.Entity.Ville;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface LogDemandeRepository extends JpaRepository<LogDemande,Long> {

    LogDemande findLogDemandeByVilleDepartAndVilleArriveAndAndHeuredepartAndAndHeurearriveAndClient(
            Ville villedepart ,
            Ville villearrive ,
            LocalTime heuredepart ,
            LocalTime heurearrive,
            Client client);
}
