package com.autocars.navete_backend.Repository;

import com.autocars.navete_backend.Entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VilleRepository extends JpaRepository<Ville,Long> {
}
