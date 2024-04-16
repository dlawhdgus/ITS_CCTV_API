package com.example.krtr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.krtr.domain.entity.roadsEntity;
import com.example.krtr.service.roadsService;

@Controller
@RequestMapping("/cctv")
public class roadsViewController {
	
	private final roadsService roasdService;
	
	@Autowired
	public roadsViewController(roadsService roadsService) {
		this.roasdService = roadsService;
	}
	
	@GetMapping("/a")
	public String getAllData() {
		
		System.out.print("road" + roasdService.getAllData());
		
		return "index.html"; 
	}
	
	
}
