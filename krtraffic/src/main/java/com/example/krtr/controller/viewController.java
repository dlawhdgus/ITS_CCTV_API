package com.example.krtr.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
//	@GetMapping("/cctv")
//	public String basciView() {
//		return "cctv.html";
//	}
	
	@GetMapping("/cctv")
	public String showCCTV(Model model, @RequestParam("city") String city) throws IOException {
		
		

		String apiData = citiesService.getCCTVUrl(city);
		
		LOGGER.info("apiData : {}", apiData);
		
		String url = citiesService.getCityUrl(city, apiData);
		
		
		
		model.addAttribute("cctvUrl", url);
		
		
		return "cctv.html";
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