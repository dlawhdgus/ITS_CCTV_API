package com.example.krtr.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.krtr.domain.entity.citiesEntity;


@Repository
public interface citiesRepository extends JpaRepository<citiesEntity, Integer> {

	// cityName으로 city 테이블 데이터 추출
	public List<citiesEntity> findByCityName(String cityName);

}