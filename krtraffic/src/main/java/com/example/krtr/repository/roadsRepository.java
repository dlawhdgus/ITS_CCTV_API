package com.example.krtr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.krtr.domain.dto.roadDto;
import com.example.krtr.domain.entity.roadsEntity;

@Repository
public interface roadsRepository extends JpaRepository<roadsEntity, String>{
	
}

// select roads.road_name, roads.maxx, roads.minx, roads.maxy, roads.miny from roads left join cities on roads.road_name = cities.road_id  where cities.city_name = "서초";