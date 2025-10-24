package com.example.aop;

import java.util.Optional;

public class DummyService {
	private String value = null;

	public Optional<String> getValue() {
		return Optional.ofNullable(value);
	}

	public void setValue(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException("No acepto argumentos nulos");
		}
		this.value = value;
	}
	
	public void clearValue() {
		value = null;
	}

    public String echo(String input) {
        return input;
    }
    
    public String alwaysNull() {
        return null;
    }

}
