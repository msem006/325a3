package com.se325a3.smdb.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.dao.RoleSmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

@Repository
@Transactional
public class HibernateRoleSmdbDao implements RoleSmdbDao {

	private static final String HQL_SELECT_MOVIE_BY_TITLE_AND_YEAR = "SELECT DISTINCT m FROM Movie m WHERE m.moviePk.title = :title AND m.moviePk.productionYear = :production_year";

	private SessionFactory _sessionFactory;
	
	@Autowired
	public HibernateRoleSmdbDao(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}
	
	@Override
	public void insertRole(Role role) {
		Person person = (Person) getCurrentSession().get(Person.class, role.getId());
		
		Query mQuery = getCurrentSession().createQuery(
				HQL_SELECT_MOVIE_BY_TITLE_AND_YEAR);
		mQuery.setParameter("title", role.getTitle());
		mQuery.setParameter("production_year", role.getProduction_year());
		Movie movie = (Movie) mQuery.uniqueResult();
		
		role.setPerson(person);
		role.setMovie(movie);
		getCurrentSession().save(role);
		getCurrentSession().flush();
	}
	
	protected final Session getCurrentSession() {
		return _sessionFactory.getCurrentSession();
	}

}
