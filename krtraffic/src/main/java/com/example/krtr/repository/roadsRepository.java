package com.example.krtr.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.krtr.domain.entity.roadsEntity;

@Repository
public interface roadsRepository extends JpaRepository<roadsEntity, String>{
	
	// roadName으로 road 테이블에 있는 정보 추출 
	public List<roadsEntity> findByRoadName(String roadName);
	
}