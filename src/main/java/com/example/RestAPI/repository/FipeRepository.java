package com.example.RestAPI.repository;

import com.example.RestAPI.model.FipeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FipeRepository extends MongoRepository<FipeEntity, String> {
}
