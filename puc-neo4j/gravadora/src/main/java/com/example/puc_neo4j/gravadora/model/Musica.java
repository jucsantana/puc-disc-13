package com.example.puc_neo4j.gravadora.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node
public record Musica(@Id String nome){}
