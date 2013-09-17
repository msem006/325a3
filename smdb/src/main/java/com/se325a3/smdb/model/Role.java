package com.se325a3.smdb.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ROLE")
@AssociationOverrides({
		@AssociationOverride(name = "rolePk.person", joinColumns = @JoinColumn(name = "id")),
		@AssociationOverride(name = "rolePk.movie", joinColumns = {
				@JoinColumn(name = "title", referencedColumnName = "title"),
				@JoinColumn(name = "production_year", referencedColumnName = "production_year") }) })
public class Role {

	private RolePk rolePk;

	private Integer id;

	private String credits;

	public Role() {
		rolePk = new RolePk();
	}

	@EmbeddedId
	public RolePk getRolePk() {
		return rolePk;
	}
	
	public void setRolePk(RolePk rolePk) {
		this.rolePk = rolePk;
	}
	
	@Transient
	public Person getPerson() {
		return rolePk.getPerson();
	}

	public void setPerson(Person person) {
		rolePk.setPerson(person);
	}

	@Transient
	public Movie getMovie() {
		return rolePk.getMovie();
	}

	public void setMovie(Movie movie) {
		rolePk.setMovie(movie);
	}

	@Column(name = "title", insertable = false, updatable = false)
	public String getTitle() {
		return rolePk.getMovie().getTitle();
	}

	public void setTitle(String title) {
		rolePk.getMovie().setTitle(title);
	}

	@Column(name = "production_year", insertable = false, updatable = false)
	public int getProduction_year() {
		return rolePk.getMovie().getProduction_year();
	}

	public void setProduction_year(int productionYear) {
		rolePk.getMovie().setProduction_year(productionYear);
	}

	@Column(name = "description", insertable = false, updatable = false)
	public String getDescription() {
		return rolePk.getDescription();
	}

	public void setDescription(String description) {
		rolePk.setDescription(description);
	}

	@Column(name = "id", insertable = false, updatable = false)
	public Integer getId() {
		return id;
	}

	@Column(name = "credits")
	public String getCredits() {
		return credits;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rolePk == null) ? 0 : rolePk.hashCode());
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
		Role other = (Role) obj;
		if (rolePk == null) {
			if (other.rolePk != null)
				return false;
		} else if (!rolePk.equals(other.rolePk))
			return false;
		return true;
	}

}
