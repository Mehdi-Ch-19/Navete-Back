package com.autocars.navete_backend.Repository;

import com.autocars.navete_backend.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findClientByEmailAndPassword(String email , String password);
}
