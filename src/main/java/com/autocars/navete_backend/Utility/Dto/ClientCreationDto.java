package com.autocars.navete_backend.Utility.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreationDto {
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @Email
    private String email;
    @NotNull
    private String password;

}
