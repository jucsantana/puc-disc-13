package com.example.puc.demo_mongo.repository;

import com.example.puc.demo_mongo.model.Albuns;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbunsRepository extends MongoRepository<Albuns, String> {
}
