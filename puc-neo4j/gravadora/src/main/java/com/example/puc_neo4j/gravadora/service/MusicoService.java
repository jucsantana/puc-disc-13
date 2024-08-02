package com.example.puc_neo4j.gravadora.service;

import com.example.puc_neo4j.gravadora.model.Musica;
import com.example.puc_neo4j.gravadora.model.Musico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MusicoService {

    private final Neo4jClient neo4jClient;

    @Autowired
    public MusicoService(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Optional<Musico> findById(String elementId) {
        return neo4jClient.query(cypherQuery())
                .bind(elementId)
                .to("elementId")
                .fetchAs(Musico.class)
                .mappedBy((typeSystem, record) -> {
                    return new Musico(
                            record.get("m").get("nome").asString(),
                            record.get("m").get("data_de_nascimento").asString(),
                            record.get("musicas").asList(
                                    value -> new Musica(value.get("nome").asString())
                            )
                    );
                })
                .one();
    }

    private static String cypherQuery() {
        return """
                    MATCH (m:Musico) WHERE elementId(m) = $elementId
                    OPTIONAL MATCH (m)-[r:GRAVOU]->(s:Musica)
                    RETURN m, collect(s) as musicas
                """;
    }
}
