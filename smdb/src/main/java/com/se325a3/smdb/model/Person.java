package com.se325a3.smdb.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class Person {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "first_name")
	private String first_name;
	
	@Column(name = "last_name")
	private String last_name;
	
	@Column(name = "year_born")
	private int year_born;
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "rolePk.person", cascade = CascadeType.ALL)
	private Set<Role> _roles;*/

	public Person() {
		//_roles = new HashSet<Role>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	public int getYearBorn() {
		return year_born;
	}

	public void setYearBorn(int yearBorn) {
		this.year_born = yearBorn;
	}

}
