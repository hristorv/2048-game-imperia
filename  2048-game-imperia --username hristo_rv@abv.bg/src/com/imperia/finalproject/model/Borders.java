package com.imperia.finalproject.model;

import java.util.HashMap;

import com.example.finalproject.R;

public class Borders {
	
	// Borders for every different number
	public static HashMap<Integer, Integer> borders = new HashMap<Integer, Integer>();

	// Initialize the borders with the number of the square
	static {
		borders.put(1, R.layout.border_default);
		borders.put(2, R.layout.border_2);
		borders.put(4, R.layout.border_4);
		borders.put(8, R.layout.border_8);
		borders.put(16, R.layout.border_16);
		borders.put(32, R.layout.border_32);
		borders.put(64, R.layout.border_64);
		borders.put(128, R.layout.border_128);
		borders.put(256, R.layout.border_256);
		borders.put(512, R.layout.border_512);
		borders.put(1024, R.layout.border_1024);
		borders.put(2048, R.layout.border_2048);
	}
}
