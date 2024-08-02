package com.example.puc_neo4j.gravadora.repository;

import com.example.puc_neo4j.gravadora.model.Musica;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MusicaRepository extends Neo4jRepository<Musica, String> {
}

