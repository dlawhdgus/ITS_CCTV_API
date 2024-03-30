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
	
	
	
	@PostMapping("/cctv")
	public String showCCTV(@RequestParam("city") String city) throws IOException {
		
		
		LOGGER.info("{}", city);
		
		String result ="";
		
		citiesService.getCCTVUrl(city);
		
		for (citiesEntity citiesEntity : citieisRepo.findByCityName(city)) {
			result += citiesEntity.toString();
		}
		
		
		return result;
	}
	
//	@GetMapping("/cctv")      -- success
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

//User
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) {
//        // 경부선에 해당하는 하위 옵션들
//        Map<String, String[]> optionsMap = new HashMap<>();
//        optionsMap.put("경부선", new String[]{"서초", "원지동", "양재", "상적교", "달래내2", "금현동", "달래내1", "금토분기점1", "금토분기점2"});
//        // 수도권제1순환선에 해당하는 하위 옵션들 추가
//
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("선택하세요:");
//        System.out.println("1. 경부선");
//        System.out.println("2. 수도권제1순환선");
//        int choice = scanner.nextInt();
//
//        if (choice == 1 || choice == 2) {
//            String selectedLine = (choice == 1) ? "경부선" : "수도권제1순환선";
//            String[] options = optionsMap.get(selectedLine);
//
//            System.out.println(selectedLine + "의 하위 옵션들:");
//            for (String option : options) {
//                System.out.println(option);
//            }
//        } else {
//            System.out.println("올바른 선택이 아닙니다.");
//        }
//    }
//}