package com.autocars.navete_backend.Entity;


import com.autocars.navete_backend.Enum.OffreStatus;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offreId")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateDebutOffre")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date datedebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateFinOffre")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date datefin;

    @Column(name = "heureDebut")
    @JsonFormat(pattern = "HH:mm",shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime heuredepart;


    @Column(name = "heureArrive")
    @JsonFormat(pattern = "HH:mm",shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime heurearrive;

    @ManyToOne
    @JoinColumn(name = "villeDepartId")
    private Ville villeDepart;

    @ManyToOne
    @JoinColumn(name = "villeArriveId")
    private Ville villeArrive;

    @Column(name = "nombreAbonnesVollue ")
    private int nombreAbonnesVollue ;

    @Column(name = "nombreClientAbonne")
    private int nombreClientAbonne = 0;

    @ManyToOne
    @JoinColumn(name = "autocarId")
    private AutoCar autoCar;

    @Column(name = "offreStatus")
    @Enumerated(EnumType.STRING)
    private OffreStatus status;

    @ManyToMany(mappedBy = "offreList",fetch = FetchType.LAZY)
    private Collection<Client> client  = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Societe societe;

}
