package com.example.krtr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.krtr.repository.citiesRepository;
import com.example.krtr.service.citiesService;

import org.springframework.ui.Model;

@Controller
public class viewController {

	private final citiesService citiesService;
	private final citiesRepository citiesRepo;

	public viewController(citiesService citiesService, citiesRepository citiesRepo) {
		this.citiesService = citiesService;
		this.citiesRepo = citiesRepo;
	}

	Logger LOGGER = LoggerFactory.getLogger(viewController.class);

	@GetMapping("/cctv")
	public String showCCTV(Model model, @RequestParam(name = "city", defaultValue = "") String city) throws Exception {
		if (city.isEmpty()) {
			return "citiesSelect.html";
		} else {
			// API의 데이터
			String apiData = citiesService.getApiData(city);
			if (apiData == null || apiData.isEmpty()) {
				LOGGER.error("apiData is Empty");
				model.addAttribute("error", "No data available for the specified city.");
				return "error.html"; // Assuming there's an error.html to handle such cases
			}

			// apiData를 이용해 URL 추출
			String url = citiesService.getCityUrl(city, apiData);
			if (url == null || url.isEmpty()) {
				LOGGER.error("URL is wrong or empty for city: {}", city);
				model.addAttribute("error", "No CCTV URL found for the specified city.");
				return "error.html"; // Assuming there's an error.html to handle such cases
			}

			// model 에 넣어 cctv.html로 리턴
			model.addAttribute("cctvUrl", url);
			return "cctv.html";
		}
	}
}
