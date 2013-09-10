package com.se325a3.smdb.model;

import java.util.HashSet;
import java.util.Set;

public class Movie {
	private String _title;
	private String _country;
	private String _majorGenre;
	private int _runTime;
	private int _productionYear;
	
	private Set<Person> _actors;

	public Movie() {
		_actors = new HashSet<Person>();
	}
	
	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		this._title = title;
	}

	public String getCountry() {
		return _country;
	}

	public void setCountry(String country) {
		this._country = country;
	}

	public String getMajorGenre() {
		return _majorGenre;
	}

	public void setMajorGenre(String majorGenre) {
		this._majorGenre = majorGenre;
	}

	public int getRunTime() {
		return _runTime;
	}

	public void setRunTime(int runTime) {
		this._runTime = runTime;
	}

	public int getProductionYear() {
		return _productionYear;
	}

	public void setProductionYear(int productionYear) {
		this._productionYear = productionYear;
	}
}
