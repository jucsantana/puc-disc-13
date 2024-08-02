package com.example.puc.demo_mongo.model;



import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

@Document(collection="albuns")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Albuns {
    @Id
    private String id;
    private String nome;
    private LocalDate data;
    private String duracao;
    private String estudiodegravacao;
    private String artista;


    public Albuns(AlbunsDetails albunsDetails) {
        this(null, albunsDetails.nome(), albunsDetails.data(), albunsDetails.duracao(), null, null);
    }

}
