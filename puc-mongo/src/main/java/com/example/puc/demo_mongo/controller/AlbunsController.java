package com.example.puc.demo_mongo.controller;


import com.example.puc.demo_mongo.model.Albuns;
import com.example.puc.demo_mongo.model.AlbunsDetails;
import com.example.puc.demo_mongo.repository.AlbunsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albuns")
public class AlbunsController {

    @Autowired
    private AlbunsRepository repository;

    @GetMapping
    public List<Albuns> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Albuns> getUserById(@PathVariable String id) {
        return repository.findById(id)
                .map(albuns -> ResponseEntity.ok().body(albuns))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Albuns createUser(@RequestBody Albuns user) {
        return repository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Albuns> updateUser(@PathVariable String id, @RequestBody AlbunsDetails albunsDetails) {
        return repository.findById(id)
                .map(albuns -> {
                    var newAlbum = new Albuns(albunsDetails);
                    Albuns updatedUser = repository.save(newAlbum);
                    return ResponseEntity.ok().body(updatedUser);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return repository.findById(id)
                .map(albuns -> {
                    repository.delete(albuns);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
