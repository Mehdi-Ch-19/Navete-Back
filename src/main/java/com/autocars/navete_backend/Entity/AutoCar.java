package com.autocars.navete_backend.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "autoCar")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AutoCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "autocarId")
    private Long id;

    @Column(name = "autocarModele")
    private String modele;

    @Column(name = "autocarNombreSiege")
    private int nombresiege;

    @Column(name = "haveWifi")
    private boolean haveWifi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "societeId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Societe societe;
}
