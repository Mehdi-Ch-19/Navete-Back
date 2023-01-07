package com.autocars.navete_backend.Repository;

import com.autocars.navete_backend.Entity.AutoCar;
import com.autocars.navete_backend.Entity.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoCarRepository  extends JpaRepository<AutoCar,Long> {
    List<AutoCar> findAllBySociete(Societe societe);
}
