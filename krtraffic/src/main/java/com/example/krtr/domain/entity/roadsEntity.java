package com.example.krtr.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="roads")
@Data
public class roadsEntity {
	@Id
	private String roadName;
	private int roadCount;
	private Double maxx;
	private Double minx;
	private Double maxy;
	private Double miny;

	@Override
	public String toString() {
		return "roadsEntity{" +
				"roadName='" + roadName + '\'' +
				", roadCount=" + roadCount +
				", maxx=" + maxx +
				", minx=" + minx +
				", maxy=" + maxy +
				", miny=" + miny +
				'}';
	}
}