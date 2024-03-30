package com.example.krtr.util;

import java.io.IOException;
import java.net.URLEncoder;

public class getCCTVUrl {
	public String getAPIData(String key, int MaxX, int MinX, int MaxY, int MinY) throws IOException{
		StringBuilder urlBuilder = new StringBuilder("https://openapi.its.go.kr:9443/cctvInfo") /*URL*/
		  .append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8")) /*공개키*/
		  .append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("ex", "UTF-8")) /*도로유형*/
		  .append("&" + URLEncoder.encode("cctvType","UTF-8") + "=" + URLEncoder.encode("2", "UTF-8")) /*CCTV유형*/
		  .append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode("MinX", "UTF-8")) /*최소경도영역*/
		  .append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode("MaxX", "UTF-8")) /*최대경도영역*/
		  .append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode("MinY", "UTF-8")) /*최소위도영역*/
		  .append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode("MaxY", "UTF-8")) /*최대위도영역*/
		  .append("&" + URLEncoder.encode("getType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*출력타입*/
		
		return urlBuilder.toString();
	}
}
