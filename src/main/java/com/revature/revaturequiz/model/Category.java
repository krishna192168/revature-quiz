package com.revature.revaturequiz.model;

import lombok.Data;

@Data
public class Category {
	private Integer id;
	private String categoryName;
	private String iconUrl;
	private String description;
}
