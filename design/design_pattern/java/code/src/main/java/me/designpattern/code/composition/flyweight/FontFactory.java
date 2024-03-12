package me.designpattern.code.composition.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FontFactory {
	private Map<String, Font> cache = new HashMap<>();

	public Font getFont(String font) {
		return cache.getOrDefault(font, createNewFont(font));
	}

	private Font createNewFont(String font) {
		String[] split = font.split(":");
		return new Font(split[0], Integer.parseInt(split[1]));
	}
}
