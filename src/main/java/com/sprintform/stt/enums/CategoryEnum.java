package com.sprintform.stt.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CategoryEnum {

	CLOTHING("CLOTHING"),
	ENTERTAINMENT("ENTERTAINMENT"),
	FINANCIAL("FINANCIAL"),
	FOOD("FOOD"),
	HEALTHCARE("HEALTHCARE"),
	HOUSING("HOUSING"),
	INSURANCE("INSURANCE"),
	LIFESTYLE("LIFESTYLE"),
	MISCELLANEOUS("MISCELLANEOUS"),
	TRAVEL("TRAVEL"),
	UTILITIES("UTILITIES");

	private final String name;

	CategoryEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@JsonCreator
	public static CategoryEnum fromName(String name) {
		for (CategoryEnum r : CategoryEnum.values()) {
			if (r.getName().equals(name)) {
				return r;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return name.toLowerCase();
	}
}
