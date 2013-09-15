package com.se325a3.smdb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MoviePk implements Serializable {
	
	private String title;

	private int production_year;
	
	public MoviePk() {}
	
	public MoviePk(String title, int productionYear) {
		this.title = title;
		this.production_year = productionYear;
	}
	
	@Column(name = "production_year")
	public int getProductionYear() {
		return this.production_year;
	}

	public void setProductionYear(int productionYear) {
		this.production_year = productionYear;
	}
	
	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + production_year;
		result = prime * result
				+ ((title == null) ? 0 : title.hashCode());
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
		MoviePk other = (MoviePk) obj;
		if (production_year != other.production_year)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}