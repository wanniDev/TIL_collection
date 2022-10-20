package me.designpattern.code.composition.flyweight;

public class Character {
	private char value;
	private String color;
	private String fontFamily;
	private int fontSize;

	public Character(char value, String color, String fontFamily, int fontSize) {
		this.value = value;
		this.color = color;
		this.fontFamily = fontFamily;
		this.fontSize = fontSize;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
}
