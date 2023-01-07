package com.autocars.navete_backend.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "societe")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Societe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "societeId")
    private Long id;

    @Column(name = "societeNom",nullable = false,length = 100)
    private String nom;

    @Column(name = "societeEmail",nullable = false)
    private String email;

    @Column(name = "societePassword",nullable = false)
    private String password;

    @OneToOne( mappedBy = "societe",cascade = CascadeType.ALL )
    private Logo logo;

    @OneToMany(mappedBy = "societe",cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true )
    private List<AutoCar> autoCarList = new ArrayList<>();

    @OneToMany(mappedBy = "societe",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Offre> offreList = new ArrayList<>();
}
