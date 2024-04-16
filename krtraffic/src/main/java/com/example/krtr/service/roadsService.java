package com.example.krtr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.krtr.domain.entity.roadsEntity;
import com.example.krtr.repository.roadsRepository;

@Service
public class roadsService implements roadsInterface{
	
	private final roadsRepository roadsRepo;
	
	@Autowired
	public roadsService(roadsRepository roadsRepo) {
		this.roadsRepo = roadsRepo;
	}
	
	
	@Override
	public List<roadsEntity> getAllData() {
		return roadsRepo.findAll();
	}
}
