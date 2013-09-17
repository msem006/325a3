package com.se325a3.smdb.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE")
public class Movie {

	private MoviePk moviePk;

	private String country;

	private String major_genre;

	private int run_time;

	private Set<Role> _actors = new HashSet<Role>();

	public Movie() {
		moviePk = new MoviePk();
	}

	@EmbeddedId
	public MoviePk getMoviePk() {
		return moviePk;
	}

	public void setMoviePk(MoviePk moviePk) {
		this.moviePk = moviePk;
	}

	@Column(name = "title", insertable = false, updatable = false)
	public String getTitle() {
		return moviePk.getTitle();
	}

	public void setTitle(String title) {
		moviePk.setTitle(title);
	}

	@Column(name = "country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "rolePk.movie")
	public Set<Role> getActors() {
		return _actors;
	}

	public void setActors(Set<Role> actors) {
		_actors = actors;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moviePk == null) ? 0 : moviePk.hashCode());
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
		Movie other = (Movie) obj;
		if (moviePk == null) {
			if (other.moviePk != null)
				return false;
		} else if (!moviePk.equals(other.moviePk))
			return false;
		return true;
	}
	
	// Getters and setters for form and controller
	@Column(name = "production_year", insertable = false, updatable = false)
	public int getProduction_year() {
		return moviePk.getProductionYear();
	}

	public void setProduction_year(int productionYear) {
		moviePk.setProductionYear(productionYear);
	}
	
	@Column(name = "major_genre")
	public String getMajor_genre() {
		return major_genre;
	}

	public void setMajor_genre(String majorGenre) {
		this.major_genre = majorGenre;
	}
	
	@Column(name = "run_time")
	public int getRun_time() {
		return run_time;
	}

	public void setRun_time(int runTime) {
		this.run_time = runTime;
	}


}
