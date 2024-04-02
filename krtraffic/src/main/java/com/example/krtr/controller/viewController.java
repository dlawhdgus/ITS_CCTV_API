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

	private citiesService citiesService;

	public viewController(citiesService citiesService, citiesRepository citieisRepo) {
		this.citiesService = citiesService;
	}

	Logger LOGGER = LoggerFactory.getLogger(viewController.class);

	@GetMapping("/cctv")
	public String showCCTV(Model model, @RequestParam(name = "city", defaultValue = "") String city) throws Exception {

		if (city.isEmpty()) {
			return "citiesSelect.html";
		} else {
			// API의 데이터
			String apiData = citiesService.getApiData(city);
			if (apiData.isEmpty() || apiData == null)
				LOGGER.error("apiData is Empty");
			// apiData를 이용해 URL 추출
			String url = citiesService.getCityUrl(city, apiData);
			if (url.isEmpty() || url == null)
				LOGGER.error("url is wrong");

			// model 에 넣어 cctv.html로 리턴
			model.addAttribute("cctvUrl", url);
			return "cctv.html";
		}

	}
}