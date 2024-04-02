package com.example.krtr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class getCCTVUrl {
	public String getAPIData(String key, String maxx, String minx, String maxy, String miny) throws IOException{
		StringBuilder urlBuilder = new StringBuilder("https://openapi.its.go.kr:9443/cctvInfo") /*URL*/
		  .append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8")) /*공개키*/
		  .append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("ex", "UTF-8")) /*도로유형*/
		  .append("&" + URLEncoder.encode("cctvType","UTF-8") + "=" + URLEncoder.encode("2", "UTF-8")) /*CCTV유형*/
		  .append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode(minx, "UTF-8")) /*최소경도영역*/
		  .append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode(maxx, "UTF-8")) /*최대경도영역*/
		  .append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode(miny, "UTF-8")) /*최소위도영역*/
		  .append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode(maxy, "UTF-8")) /*최대위도영역*/
		  .append("&" + URLEncoder.encode("getType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*출력타입*/
		
		URL url = new URL(urlBuilder.toString());
		  
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "text/xml;charset=UTF-8");
		  
		BufferedReader rd;
		  
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		  
		StringBuilder sb = new StringBuilder();
		String line;
		  
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		  
		rd.close();
		conn.disconnect();
		  
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(sb.toString());

		return rootNode.toString();
	}
}
