package com.example.krtr.domain.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="roads")
@Data
public class roadsEntity {
	@Id
	private String road_name;
	private int road_count;
	private Double maxx;
	private Double minx;
	private Double maxy;
	private Double miny;
	
	public Double getMaxX() {
		return maxx;
	}
	public void setMaxX(Double maxX) {
		maxx = maxX;
	}
	public int getRoad_count() {
		return road_count;
	}
	public void setRoad_count(int road_count) {
		this.road_count = road_count;
	}
	public Double getMaxY() {
		return maxy;
	}
	public void setMaxY(Double maxY) {
		maxy = maxY;
	}
	public Double getMinX() {
		return minx;
	}
	public void setMinX(Double minX) {
		minx = minX;
	}
	public Double getMinY() {
		return miny;
	}
	public void setMinY(Double minY) {
		miny = minY;
	}

}
