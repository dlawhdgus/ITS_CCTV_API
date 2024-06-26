package com.example.krtr.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cities")
@Data
public class citiesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cityIdx;
	private String cityName;
	private String roadId;

	@Override
	public String toString() {
		return "citiesEntity{" +
				"cityIdx=" + cityIdx +
				", cityName='" + cityName + '\'' +
				", roadId='" + roadId + '\'' +
				'}';
	}
}
