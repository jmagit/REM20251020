package com.example.domain.entities.models;

import org.springframework.beans.factory.annotation.Value;

import com.example.domain.entities.Direction;

public interface OwnerPlain {
	interface DirectionSummary {
		String getCity();
	}
	int getId();
	@Value("#{target.firstName + ' ' + target.lastName}")
	String getName();
	DirectionSummary getDirection();
	@Value("#{target.Direction.address}")
	String getCalle();
}
