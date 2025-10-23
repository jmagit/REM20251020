package com.example.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public class Direction {
	@Column(length=255)
	@Size(max = 255)
	private String address;

	@Column(length=80)
	@Size(max = 80)
	private String city;

	public Direction() {
	}
	
	public Direction(String address, String city) {
		this.address = address;
		this.city = city;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Direction [" + (address != null ? "address=" + address + ", " : "")
				+ (city != null ? "city=" + city : "") + "]";
	}

}
