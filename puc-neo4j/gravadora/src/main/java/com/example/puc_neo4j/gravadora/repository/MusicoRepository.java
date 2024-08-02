package com.example.puc_neo4j.gravadora.repository;

import com.example.puc_neo4j.gravadora.model.Musico;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MusicoRepository extends Neo4jRepository<Musico, String> {
}
