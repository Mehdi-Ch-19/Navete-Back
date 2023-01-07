package com.autocars.navete_backend.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ville")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "villeId")
    private Long id;

    @Column(name = "villeNom")
    private String nom;
}
