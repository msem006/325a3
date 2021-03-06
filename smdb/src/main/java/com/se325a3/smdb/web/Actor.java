package com.se325a3.smdb.web;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

public class Actor {
	private Integer id;
	private String first_name;
	private String last_name;
	private int year_born;
	private String title;
	private int production_year;
	private String description;
	@Min(0)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@NotEmpty
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	@NotEmpty
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	@Min(0)
	public int getYear_born() {
		return year_born;
	}
	public void setYear_born(int year_born) {
		this.year_born = year_born;
	}
	@NotEmpty
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Min(0)
	public int getProduction_year() {
		return production_year;
	}
	public void setProduction_year(int production_year) {
		this.production_year = production_year;
	}
	@NotEmpty
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
