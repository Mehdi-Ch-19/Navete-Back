package com.autocars.navete_backend.Service;


import com.autocars.navete_backend.Entity.*;
import com.autocars.navete_backend.Repository.DemandeRepository;
import com.autocars.navete_backend.Repository.LogDemandeRepository;
import com.autocars.navete_backend.Utility.Exeption.ClientDemandeExistsDejaExeption;
import com.autocars.navete_backend.Utility.Exeption.ClientNotFoundExeption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class GestionDemandeService {
    /*
    * Solution avec deux table (LogDemande et Demande)
    * --- WorkFlow de la gestion des Demande
    * ---------- 1-  le client va creer des demandes
    * ---------- 2-  on va stocker cette demande dans la table Log_Demande (qui a Foreign key avec table client)
    * ---------- 3-  on va prendre les info du Client_demande et va tester si il y a les meme info dans la table Demande
    * ---------- 4-  si non on va l' inserer (Dans la table Demande ) et initializer le champs (nombreClientPropose) par  1
    * ---------- 5-  si oui on va pas inserer mais on va directement incrementer (nombreClientPropose)++
    * ---------- 6- les societe va consulter seulement la table demande
    * */

    private final LogDemandeRepository logDemandeRepository;
    private final DemandeRepository demandeRepository;
    private final ClientService clientService;
    private final  OffreService offreService;
    public void ajouterDemande(LogDemande logDemande , Long clientid) throws ClientDemandeExistsDejaExeption {

        LogDemande logDemande1 = logDemandeRepository.
                findLogDemandeByVilleDepartAndVilleArriveAndAndHeuredepartAndAndHeurearriveAndClient(
                        logDemande.getVilleDepart(),
                        logDemande.getVilleArrive(),
                        logDemande.getHeuredepart(),
                        logDemande.getHeurearrive(),
                        clientService.getClient(clientid)
                );
        /* check if demande aleardy exists
         * */
        if (logDemande1 == null ){
            Demande demande = demandeRepository
                    .findDemandesByVilleDepartAndVilleArriveAndAndHeuredepartAndAndHeurearrive(
                            logDemande.getVilleDepart(),
                            logDemande.getVilleArrive(),
                            logDemande.getHeuredepart(),
                            logDemande.getHeurearrive());
            Client client = clientService.getClient(clientid);
            if(demande != null){
                demande.setNombrePersonneIntesrese(demande.getNombrePersonneIntesrese()+1);
                demandeRepository.save(demande);
                logDemande.setClient(client);
                logDemandeRepository.save(logDemande);
            }else {
                Demande demande1 = Demande.builder()
                        .heurearrive(logDemande.getHeurearrive())
                        .heuredepart(logDemande.getHeuredepart())
                        .villeArrive(logDemande.getVilleArrive())
                        .villeDepart(logDemande.getVilleDepart())
                        .nombrePersonneIntesrese(1)
                        .build();
                demandeRepository.save(demande1);
                logDemande.setClient(client);
                logDemandeRepository.save(logDemande);
            }
        }else {
            throw new ClientDemandeExistsDejaExeption("La demande fait par le client exists deja");
        }

    }
    public List<Demande> getalldemande(){
        return demandeRepository.findAll();
    }
    public void prendenchargedemande(Demande demande , Long societeid  , AutoCar autoCar , int nombreabvolue
            , Date datedebut , Date datefin){
        Offre offre = Offre.
                builder()
                .villeDepart(demande.getVilleDepart())
                .villeArrive(demande.getVilleArrive())
                .autoCar(autoCar)
                .nombreAbonnesVollue(nombreabvolue)
                .datedebut(datedebut)
                .datefin(datefin)
                .build();
         offreService.createOffre(offre,societeid);
         demandeRepository.delete(demande);

    }

}
