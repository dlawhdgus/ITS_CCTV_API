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
		
		LOGGER.info("{}", city);
		
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
		
		LOGGER.info("{}", cityInfo);
		
		String roadInfo ="";
		
		Pattern roadInfoPattern = Pattern.compile("roadId=([^,$)]+)");
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
		String url ="";
		Gson gson = new Gson();
		// apiData : {"response":{"coordtype":1,"data":[{"roadsectionid":"","coordx":127.086135864257,"coordy":37.4141845703125,"cctvresolution":"","filecreatetime":"","cctvtype":2,"cctvformat":"MP4","cctvname":"[경부선] 금토분기점2","cctvurl":"http://cctvsec.ktict.co.kr/270/uWRT6l6xzGA7NBI7Uz/T6MpgesyOgmyb4Andqzg0PYBcrmLe25rxGlAiwbIeE5/cRoWe7fdcr/JGKJxfG+g59w=="},{"roadsectionid":"","coordx":127.08472,"coordy":37.4153,"cctvresolution":"","filecreatetime":"","cctvtype":2,"cctvformat":"MP4","cctvname":"[경부선] 금토분기점1","cctvurl":"http://cctvsec.ktict.co.kr/320/VhRfKwEXMAqZ3f4QD7U1U0Ub4+i+IQuQHBz42YLhxtL4+qeORl6f37g9YEyoUd7Dmof33sjUoklF5AFB5dYFfg=="},{"roadsectionid":"","coordx":127.084731,"coordy":37.415971,"cctvresolution":"","filecreatetime":"","cctvtype":2,"cctvformat":"MP4","cctvname":"[경부선] 금현동","cctvurl":"http://cctvsec.ktict.co.kr/2431/UVbTGwyhEMPvfAHHngBGtYaSrgZ28xcMXsj5iUDW8pn7U0YbbrmY0sO5b85HWlmSXDMbL5oZRmx9eLR2WlpZlQ=="},{"roadsectionid":"","coordx":127.07639,"coordy":37.42444,"cctvresolution":"","filecreatetime":"","cctvtype":2,"cctvformat":"MP4","cctvname":"[경부선] 달래내1","cctvurl":"http://cctvsec.ktict.co.kr/97/iR/LJi7iJ76umptV9kZ3q2R6urRQOVkdj3DCUe+klZtNZqaRIZ+PBsObivlzG2JvMxrcgAWiteaBHjzo7rZQfA=="},{"roadsectionid":"","coordx":127.069449,"coordy":37.430381,"cctvresolution":"","filecreatetime":"","cctvtype":2,"cctvformat":"MP4","cctvname":"[경부선] 달래내2","cctvurl":"http://cctvsec.ktict.co.kr/501/osjm6iHu67rTC0GnUMReBPaNEVD9zXnN5nfgvVcIzIMBwsD4814rTqjTL3o7j/kssKBopYUXsPMtETHK9P2ZWQ=="},{"roadsectionid":"","coordx":127.060888,"coordy":37.439058,"cctvresolution":"","filecreatetime":"","cctvtype":2,"cctvformat":"MP4","cctvname":"[경부선] 상적교","cctvurl":"http://cctvsec.ktict.co.kr/95/oh9BPV6yOap9I11jkmKDgP5RAiE1vyBBF0jEhsOhtc/gFX3ga0vuqOm7ZTbkhgv3AHCF47IvYTeSl6K/YYK7MA=="},{"roadsectionid":"","coordx":127.060749,"coordy":37.440057,"cctvresolution":"","filecreatetime":"","cctvtype":2,"cctvformat":"MP4","cctvname":"[경부선] 원지동","cctvurl":"http://cctvsec.ktict.co.kr/2430/Z2PIlQoz+mGSyWubCHqbRPFBMbZYtZySPz+vZSyl4QRXILuGHgf+nopvQSxrWsTqBV9C6P3LcVOiGcii/HhE6g=="},{"roadsectionid":"","coordx":127.042004,"coordy":37.461626,"cctvresolution":"","filecreatetime":"","cctvtype":2,"cctvformat":"MP4","cctvname":"[경부선] 양재","cctvurl":"http://cctvsec.ktict.co.kr/100/kE7ozYtzhArVCxyxASPW42gNSmm6i/ia5Vvoh4tv0zhWS2xder4zEBpgAjB8FsI+/yeCq2R3hAftmyPy/RlM1Q=="},{"roadsectionid":"","coordx":127.02583,"coordy":37.48306,"cctvresolution":"","filecreatetime":"","cctvtype":2,"cctvformat":"MP4","cctvname":"[경부선] 서초","cctvurl":"http://cctvsec.ktict.co.kr/99/FTJEu+C4mqPQ5EvPNRRLWSRl97j3offHhBk7M4R6AY9FmE6ydDqFdSgm9PTgqbaPpGpz9A73TE++dWBl5vXa3A=="}],"datacount":9}}
		JsonObject jsonObject = gson.fromJson(apiData, JsonObject.class);
		
		LOGGER.info("jo : {}", jsonObject);
		
		JsonArray dataArray = jsonObject.getAsJsonArray("data");
	        
//        for (JsonElement element : dataArray) {
//            JsonObject dataObject = element.getAsJsonObject();
//            String cctvName = dataObject.get("cctvname").getAsString();
//            if (cctvName.equals(city)) {
//                url = dataObject.get("cctvurl").getAsString();
//                break;
//            }
//        }

	
		
		return url;
	}

		
	

	public roadsRepository getRoadsRepo() {
		return roadsRepo;
	}

	public getCCTVUrl getUtil() {
		return util;
	}
}
