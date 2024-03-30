package com.example.krtr.controller;

import java.io.IOException;


import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.krtr.controller.service.citiesService;


@Controller
public class mainUrlController {	
	
	@GetMapping("/")
	public String indexView() {
		
		System.out.print("hello");
		
		return "../static/index";
	}
	
	@GetMapping("/cctv1")
	public String cctvView(Model model) throws IOException {
		return "cctv.html";
	}
	
}
