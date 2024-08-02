package com.example.puc_neo4j.gravadora.controller;

import com.example.puc_neo4j.gravadora.model.Musica;
import com.example.puc_neo4j.gravadora.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicas")
public class MusicaController {

    @Autowired
    private MusicaRepository musicaRepository;

    @GetMapping
    public List<Musica> getAllMusicas() {
        return musicaRepository.findAll();
    }

    @PostMapping
    public Musica createMusica(@RequestBody Musica musica) {
        return musicaRepository.save(musica);
    }
}
