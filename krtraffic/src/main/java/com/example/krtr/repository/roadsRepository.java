package com.example.krtr.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.krtr.domain.entity.roadsEntity;

@Repository
public interface roadsRepository extends MongoRepository<roadsEntity, String>{
	
}