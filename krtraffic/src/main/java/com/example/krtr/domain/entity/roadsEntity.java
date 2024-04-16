package com.example.krtr.domain.entity;

import java.sql.Array;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collation = "roads")
public class roadsEntity {
	@Id
	private String id;
	private Array data;
	
	public Array getData() {
		return data;
	}
	public void setData(Array data) {
		this.data = data;
	}
	
	
}
