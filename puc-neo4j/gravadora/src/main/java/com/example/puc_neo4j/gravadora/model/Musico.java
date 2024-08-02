package com.example.puc_neo4j.gravadora.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public record Musico(
        @Id
        String nome,
        String data_de_nascimento,
        @Relationship(type = "GRAVOU", direction = Relationship.Direction.OUTGOING)
        List<Musica> musicas) {}
