package com.example.puc_neo4j.gravadora.controller;

import com.example.puc_neo4j.gravadora.exceptions.ResourceNotFoundException;
import com.example.puc_neo4j.gravadora.model.GravouDTO;
import com.example.puc_neo4j.gravadora.model.Musica;
import com.example.puc_neo4j.gravadora.model.Musico;
import com.example.puc_neo4j.gravadora.repository.MusicaRepository;
import com.example.puc_neo4j.gravadora.repository.MusicoRepository;
import com.example.puc_neo4j.gravadora.service.MusicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/musicos")
public class MusicoController {

    @Autowired
    private MusicoRepository musicoRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private MusicoService musicoService;

    @GetMapping
    public List<Musico> getAllMusicos() {
        return musicoRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Musico> getMusico(@PathVariable String id) {
        var musicoOp = musicoService.findById(id);
        return musicoOp.map(musico -> ResponseEntity.ok(musico))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    public Musico createMusico(@RequestBody Musico musico) {
        return musicoRepository.save(musico);
    }

    @PostMapping("/{musicoNome}/gravou/{musicaNome}")
    public Musico createGravouRelationship(@PathVariable String musicoNome, @PathVariable String musicaNome) {
        Musico musico = musicoRepository.findById(musicoNome).orElseThrow(() -> new ResourceNotFoundException("Musico não encontrado: " + musicoNome));
        Musica musica = musicaRepository.findById(musicaNome).orElseThrow(() -> new ResourceNotFoundException("Musica não encontrada: " + musicaNome));

        musico.musicas().add(musica);
        return musicoRepository.save(musico);
    }

    @GetMapping("/relacionamentos")
    public List<GravouDTO> getAllGravouRelationships() {
        return musicoRepository.findAll().stream()
                .flatMap(musico -> musico.musicas().stream()
                        .map(musica -> new GravouDTO(musico.nome(), musica.nome())))
                .collect(Collectors.toList());
    }
}
