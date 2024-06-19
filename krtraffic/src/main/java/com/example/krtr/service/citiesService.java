package com.example.krtr.service;

import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.krtr.controller.viewController;
import com.example.krtr.domain.entity.citiesEntity;
import com.example.krtr.domain.entity.roadsEntity;
import com.example.krtr.repository.citiesRepository;
import com.example.krtr.repository.roadsRepository;
import com.example.krtr.util.getCCTVUrl;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.regex.*;
import java.io.IOException;

@Service
public class citiesService {

	private final roadsRepository roadsRepo;
	private final citiesRepository citiesRepo;
	private final getCCTVUrl util;

	@Value("${ITS.key}")
	private String itsKey;

	Logger LOGGER = LoggerFactory.getLogger(viewController.class);

	public citiesService(roadsRepository roadsRepo, citiesRepository citiesRepo, getCCTVUrl api) {
		this.roadsRepo = roadsRepo;
		this.util = api;
		this.citiesRepo = citiesRepo;
	}

	// city 의 값으로 road 정보를 가져오는 함수
	public String getApiData(String city) throws IOException, JsonMappingException, JsonProcessingException {

		String citiyInfo = "";

		for (citiesEntity citiesEntity : citiesRepo.findByCityName(city)) {
			citiyInfo += citiesEntity.toString();
		}

		String apiData = this.getRoadInfo(citiyInfo);

		if (apiData.isEmpty() || apiData == null) {
			return "";
		} else {
			return apiData;
		}

	}

	// 받은 apiData에서 URL를 추출
	public String getRoadInfo(String cityInfo) throws IOException {
		String regex = "roadId='([^']+)'";
		String roadInfo = "";

		Pattern roadInfoPattern = Pattern.compile(regex);
		Matcher roadInfoMatcher = roadInfoPattern.matcher(cityInfo.toString());

		if (roadInfoMatcher.find()) {
			String roadId = roadInfoMatcher.group(1);

			for (roadsEntity roadsEntity : roadsRepo.findByRoadName(roadId)) {
				roadInfo += roadsEntity.toString();
			}

			Pattern getCoordinatePattern = Pattern
					.compile("maxx=([\\d.]+), minx=([\\d.]+), maxy=([\\d.]+), miny=([\\d.]+)");

			Matcher CoordinateMatcher = getCoordinatePattern.matcher(roadInfo);

			if (CoordinateMatcher.find()) {
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
			return "";
		}
	}

	public String getCityUrl(String city, String apiData) throws Exception {
		String url = null;
		Object urlData = null;
		JSONParser parser = new JSONParser();

		JSONObject apiJsonData = (JSONObject) parser.parse(apiData);

		JSONObject response = (JSONObject) apiJsonData.get("response");
		JSONArray data = (JSONArray) response.get("data");

		Object cctvName = null;

		for (Object cctv : data) {
			cctvName = ((JSONObject) cctv).get("cctvname").toString();
			urlData = ((JSONObject) cctv).get("cctvurl");

			String[] regexCctvName = null;
			regexCctvName = cctvName.toString().split("\\s");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < regexCctvName.length; i++) {
				if (regexCctvName[0] != regexCctvName[i]) {

					if (i != 0) {
						cctvName = sb.append(regexCctvName[i]);
						if (cctvName.toString().equals(city)) {
							urlData = ((JSONObject) cctv).get("cctvurl");

							url = urlData.toString();
						}
					}
				}
			}

		}
		return url;
	}

	public roadsRepository getRoadsRepo() {
		return roadsRepo;
	}

	public getCCTVUrl getUtil() {
		return util;
	}
}