package com.autocars.navete_backend.Utility.Dto;

import com.autocars.navete_backend.Entity.Logo;
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
public class SocietCreationDto {
    @NotNull
    private String nom;
    @Email
    private String email;
    @NotNull
    private String password;

    private Logo logo;

}
