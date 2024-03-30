package com.example.krtr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.krtr.domain.entity.citiesEntity;


@Repository
public interface citiesRepository extends JpaRepository<citiesEntity, Integer> {
	
	
//	List<citiesEntity> findByCityName(String cityname);
	
}