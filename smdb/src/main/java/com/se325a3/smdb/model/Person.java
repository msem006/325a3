package com.se325a3.smdb.model;

import java.util.HashSet;
import java.util.Set;

public class Person {
	private String _id;
	private String _firstName;
	private String _lastName;
	private int _yearBorn;
	
	private Set<Movie> _movies;

	public Person() {
		_movies = new HashSet<Movie>();
	}
	
	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String firstName) {
		this._firstName = firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String lastName) {
		this._lastName = lastName;
	}

	public int getYearBorn() {
		return _yearBorn;
	}

	public void setYearBorn(int yearBorn) {
		this._yearBorn = yearBorn;
	}

}
