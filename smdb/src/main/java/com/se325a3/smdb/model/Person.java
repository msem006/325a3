package com.se325a3.smdb.model;

import java.util.HashSet;
import java.util.Set;

import javax.management.relation.RoleStatus;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rolePk.person", cascade = CascadeType.ALL)
	private Set<Role> _roles;

	public Person() {
		_roles = new HashSet<Role>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getYear_born() {
		return year_born;
	}

	public void setYear_born(int year_born) {
		this.year_born = year_born;
	}

	public Set<Role> getRoles() {
		return _roles;
	}
	
	public void setRoles(Set<Role> roles) {
		_roles = roles;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
