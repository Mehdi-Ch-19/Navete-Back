package com.autocars.navete_backend.Entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientId")
    private Long id;
    @Column(name = "clientNom")
    private String nom;
    @Column(name = "clientPrenom")
    private String prenom;
    @Column(name = "clientEmail")
    private String email;
    @Column(name = "clientPassword")
    private String password;

    @ManyToMany
    @JoinTable(name = "Client_Abonnee",
            joinColumns = @JoinColumn(name = "clientId"),inverseJoinColumns = @JoinColumn(name = "offreId")
    )
    private Collection<Offre> offreList = new ArrayList<>();

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<LogDemande> demandes = new ArrayList<>();
}
