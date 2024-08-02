package com.example.redis.controller;

import com.example.redis.model.MeuObjeto;
import com.example.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/setObject")
    public void setObject(@RequestParam String key, @RequestBody MeuObjeto objeto) {
        redisService.setObject(key, objeto);
    }

    @GetMapping("/getObject")
    public ResponseEntity getObject(@RequestParam String key) {
        var object = redisService.getObject(key);
        return object==null? ResponseEntity.noContent().build():ResponseEntity.ok(object);
    }

    @GetMapping("/keys")
    public Set<String> getKeys() {
        return redisService.getAllKeys();
    }
}
