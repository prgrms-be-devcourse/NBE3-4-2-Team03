package com.programmers.pcquotation.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
	Customer("customer"),
	Seller("seller"),
	Admin("admin"),
	Nothing("nothing");
	private final String value;
	public static UserType fromString(String value) {
		if (value == null) {
			return UserType.Nothing;
		}
		switch (value){
			case "ROLE_CUSTOMER": return UserType.Customer;
			case "ROLE_SELLER": return UserType.Seller;
		}
		try {
			return UserType.valueOf(value);
		} catch (IllegalArgumentException e) {
			return UserType.Nothing; 
		}
	}

}
