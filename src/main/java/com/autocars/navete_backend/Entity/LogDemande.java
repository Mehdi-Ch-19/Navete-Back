package com.autocars.navete_backend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "logDemande")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class LogDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logdemandeid")
    private Long id;

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

    @ManyToOne
    @JoinColumn(name = "clientid")
    private Client client;

}
