package com.sprintform.stt.enums;

public enum CategoryEnum {

	ENTERTAINMENT("entertainment"),
	FINANCIAL("financial"),
	FOOD("food"),
	HEALTHCARE("healthcare"),
	HOUSING("housing"),
	INSURANCE("insurance"),
	LIFESTYLE("lifestyle"),
	MISCELLANEOUS("miscellaneous"),
	TRAVEL("travel"),
	UTILITIES("utilities");

	private final String name;

	CategoryEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
