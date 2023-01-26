package com.autocars.navete_backend.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "logo")
public class Logo {
    /*chaque societe peut avoir un Logo*/
    @Id
    @Column(name = "logId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logoName")
    private String name;

    @Column(name = "logoType")
    private String type;

    @Column(name = "logo",  length = 1000000)
    private byte[] image;

    @OneToOne
    @JoinColumn(name = "societeId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Societe societe;
}
