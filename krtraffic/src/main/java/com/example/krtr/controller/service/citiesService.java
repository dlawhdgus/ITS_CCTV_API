package com.example.krtr.controller.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import jakarta.persistence.EntityManager;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
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
		
		String citiyInfo ="";
		
		for (citiesEntity citiesEntity : citiesRepo.findByCityName(city)) {
			citiyInfo += citiesEntity.toString();
		}
		
		String url = this.getRoadInfo(citiyInfo);
		
		if(url.isEmpty() || url == null) {
			return " ";
		} else {			
			return url;
		}
		
	}
	
	public String getRoadInfo(String cityInfo) throws IOException{
		String roadInfo ="";
		
		Pattern roadInfoPattern = Pattern.compile("roadId=([^,$&0-9)]+)");
        Matcher roadInfoMatcher = roadInfoPattern.matcher(cityInfo);
		
		if(roadInfoMatcher.find()) {
			String roadId = roadInfoMatcher.group(1);
			
			for(roadsEntity roadsEntity : roadsRepo.findByRoadName(roadId)) {
				roadInfo += roadsEntity.toString();
			}
			
			Pattern getCoordinatePattern = Pattern.compile("maxx=([\\d.]+), minx=([\\d.]+), maxy=([\\d.]+), miny=([\\d.]+)");
	        Matcher CoordinateMatcher = getCoordinatePattern.matcher(roadInfo);
	        
	        if(CoordinateMatcher.find()) {
	        	String maxx = CoordinateMatcher.group(1);
	        	String minx = CoordinateMatcher.group(2);
	        	String maxy = CoordinateMatcher.group(3);
	        	String miny = CoordinateMatcher.group(4);
	        
	        	String cctvUrl = util.getAPIData(itsKey, maxx, minx, maxy, miny);
	        	
	        	return cctvUrl;
	        } else {	
	        	return "";
	        }			
		} else {			
			return " ";
		}
	}
	
	public String getCityUrl(String city, String apiData) {
		try {
			String url = null;
			Object urlData = null;
			JSONParser parser = new JSONParser();
			
			JSONObject apiJsonData = (JSONObject) parser.parse(apiData);
			
			JSONObject response = (JSONObject) apiJsonData.get("response");
			JSONArray data = (JSONArray) response.get("data");
			
			for(Object cctv : data) {
				Object c = ((JSONObject) cctv).get("cctvname");
				urlData = ((JSONObject) cctv).get("cctvurl");
				
				if(c.toString().replaceAll("\\[.*\\]| ","").equals(city)) {
					urlData = ((JSONObject) cctv).get("cctvurl");
					
					url = urlData.toString();
				
				}
			}
			return url;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		

	
	}

		
	

	public roadsRepository getRoadsRepo() {
		return roadsRepo;
	}

	public getCCTVUrl getUtil() {
		return util;
	}
}
