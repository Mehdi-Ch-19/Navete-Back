package com.autocars.navete_backend.Repository;

import com.autocars.navete_backend.Entity.Offre;
import com.autocars.navete_backend.Entity.Societe;
import com.autocars.navete_backend.Entity.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffreRepository extends JpaRepository<Offre,Long> {

    Page<Offre> findOffreByVilleDepartAndVilleArrive(Ville villedepart, Ville villearrive , Pageable pageable);
    List<Offre> findOffreByVilleDepartAndVilleArrive(Ville villedepart, Ville villearrive );
    @Query(value = "select societe_id  from offre o where o.offre_Id = ?1 ",nativeQuery = true)
    Long findsocietebyoffreid(Long offreid);
}
