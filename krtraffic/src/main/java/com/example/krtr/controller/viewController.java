package com.example.krtr.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.krtr.controller.service.citiesService;
import com.example.krtr.domain.entity.citiesEntity;
import com.example.krtr.repository.citiesRepository;

import org.springframework.ui.Model;

@Controller
public class viewController {
	
	private citiesService citiesService;
	
	@Autowired
	private final citiesRepository citieisRepo;
	
	private Logger LOGGER = LoggerFactory.getLogger(citiesService.class);
	
	public viewController(citiesService citiesService, citiesRepository citieisRepo) {
		this.citieisRepo = citieisRepo;
		this.citiesService = citiesService;
	}


	@GetMapping("/cctv")
	public String showCCTV(Model model, @RequestParam(name = "city", defaultValue = "") String city) throws IOException {
		LOGGER.info("city : {}", city);
		
	    if(city.isEmpty()) {
	    	return "test.html";
	    } else {
	    	String apiData = citiesService.getCCTVUrl(city);
	    	String url = citiesService.getCityUrl(city, apiData);
	    	LOGGER.info("CCTV URL: {}", url);
	    	model.addAttribute("cctvUrl", url);
	    	return "cctv.html"; // CCTV URL을 직접 반환
	    }
		
	}


	
	
	
	
//	@GetMapping("/cctv")    
//	public String showCCTV1(@RequestParam("city") String city, Model model) throws IOException {
//		
//		
//		LOGGER.info("{}", city);
//		
//		
//		
//		String a = citiesService.getCCTVUrl(city);
//		
//		
//		model.addAttribute("result", a);
//		
//		return "test.html";
//	}
	
}