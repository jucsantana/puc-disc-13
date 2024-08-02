package com.example.redis.service;

import com.example.redis.model.MeuObjeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setObject(String key, MeuObjeto objeto) {
        redisTemplate.opsForValue().set(key, objeto);
    }

    public MeuObjeto getObject(String key) {
        return (MeuObjeto) redisTemplate.opsForValue().get(key);
    }

    public Set<String> getAllKeys(){
        return redisTemplate.keys("*").stream().sorted().collect(Collectors.toSet());
    }
}
