package com.example.bd_nosql.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciasDTO {

    private String nome;
    private String generoMusicalFavorito;
    private int volume;
}