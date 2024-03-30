package com.example.krtr.controller.service;

import org.hibernate.type.BasicTypeReference;
import org.slf4j.Logger;



import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.krtr.domain.dto.roadDto;
import com.example.krtr.domain.entity.citiesEntity;
import com.example.krtr.domain.entity.roadsEntity;
import com.example.krtr.repository.citiesRepository;
import com.example.krtr.repository.roadsRepository;
import com.example.krtr.util.getCCTVUrl;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.IOException;

@Service
public class citiesService {
	@Value("${ITS.key}")
	private String itsKey;
	
	private final roadsRepository roadsRepo;
	private final citiesRepository citiesRepo;
	private final getCCTVUrl util;
	
	private Logger LOGGER = LoggerFactory.getLogger(citiesService.class);

	public citiesService(roadsRepository roadsRepo, citiesRepository citiesRepo, getCCTVUrl api) {
		this.roadsRepo = roadsRepo;
		this.util = api;
		this.citiesRepo = citiesRepo;
	}
	
	public String getCCTVUrl(String city) throws IOException, JsonMappingException, JsonProcessingException{
		
		String result ="";
		
		LOGGER.info("{}", city);
		
		for (citiesEntity citiesEntity : citiesRepo.findByCityName(city)) {
			result += citiesEntity.toString();
		}
		
		return result;
		
//		String StringUrl = util.getAPIData(itsKey, 0, 0, 0, 0);
//		
//		URL url = new URL(StringUrl);
//		  
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestMethod("GET");
//		conn.setRequestProperty("Content-type", "text/xml;charset=UTF-8");
//		  
//		System.out.println("Response code: " + conn.getResponseCode());
//		System.out.println("Request url: " + url);
//		BufferedReader rd;
//		  
//		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//		} else {
//			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//		}
//		  
//		StringBuilder sb = new StringBuilder();
//		String line;
//		  
//		while ((line = rd.readLine()) != null) {
//			sb.append(line);
//		}
//		  
//		rd.close();
//		conn.disconnect();
//		System.out.println(sb.toString());
//		  
//		ObjectMapper objectMapper = new ObjectMapper();
//		JsonNode rootNode = objectMapper.readTree(sb.toString());
//
//        // CCTV URL 추출
//        JsonNode cctvUrlNode = rootNode.path("response").path("data").get("cctvurl");
//        String cctvUrl = cctvUrlNode.toString().replace("\"", " ");
//    	
//		return cctvUrl;
		
	}

	public roadsRepository getRoadsRepo() {
		return roadsRepo;
	}

	public getCCTVUrl getUtil() {
		return util;
	}
}
