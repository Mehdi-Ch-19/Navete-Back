package com.autocars.navete_backend.Repository;

import com.autocars.navete_backend.Entity.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocieteRepository extends JpaRepository<Societe,Long> {
    Societe findSocieteByEmailAndPassword(String email,String psaaword);
}
