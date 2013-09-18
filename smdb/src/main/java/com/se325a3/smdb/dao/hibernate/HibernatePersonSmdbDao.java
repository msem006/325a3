package com.se325a3.smdb.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.dao.PersonSmdbDao;
import com.se325a3.smdb.model.Person;

@Repository
@Transactional
public class HibernatePersonSmdbDao implements PersonSmdbDao {

	private static final String HQL_SELECT_ACTOR_BY_ID = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND p.id = :id";

	private static final String HQL_SELECT_ACTORS_BY_FIRST_NAME = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(p.first_name) LIKE :name";
	
	private static final String HQL_SELECT_ACTORS_BY_LAST_NAME = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(p.last_name) LIKE :name";
	
	private static final String HQL_SELECT_ACTORS_BY_FIRST_NAME_AND_LAST_NAME = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(p.first_name) LIKE :firstname AND str(p.last_name) LIKE :lastname";
	
	private static final String HQL_SELECT_ACTORS_BY_FIRST_NAME_OR_LAST_NAME = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND  str(p.first_name) LIKE :firstname OR str(p.last_name) LIKE :lastname AND p.id = r.id";

	private static final String HQL_SELECT_ACTORS_BY_MOVIE_TITLE = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(r.title) LIKE :title";

	private static final String HQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id "
			+ "AND r.rolePk.movie.moviePk.productionYear = :production_year AND r.title = :title";

	private SessionFactory _sessionFactory;

	@Autowired
	public HibernatePersonSmdbDao(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}
	
	@Override
	public Person getPersonById(int id) {
		return (Person) getCurrentSession().get(Person.class, id);
	}
	
	@Override
	public Person getActorById(int id) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_ACTOR_BY_ID);
		query.setParameter("id", id);
		return (Person) query.uniqueResult();
	}

	@Override
	public List<Person> getActorsByFirstName(String name) {
		Query query = getCurrentSession()
				.createQuery(HQL_SELECT_ACTORS_BY_FIRST_NAME);
		query.setParameter("name", name + "%");
		return (List<Person>) query.list();
	}

	@Override
	public List<Person> getActorsByLastName(String name) {
		Query query = getCurrentSession()
				.createQuery(HQL_SELECT_ACTORS_BY_LAST_NAME);
		query.setParameter("name", name + "%");
		return (List<Person>) query.list();
	}

	@Override
	public List<Person> getActorsByFirstNameAndLastName(String firstname,
			String lastname) {
		Query query = getCurrentSession()
				.createQuery(HQL_SELECT_ACTORS_BY_FIRST_NAME_AND_LAST_NAME);
		query.setParameter("firstname", firstname + "%");
		query.setParameter("lastname", lastname + "%");
		return (List<Person>) query.list();
	}
	
	@Override
	public List<Person> getActorsByFirstNameOrLastName(String firstname,
			String lastname) {
		Query query = getCurrentSession()
				.createQuery(HQL_SELECT_ACTORS_BY_FIRST_NAME_OR_LAST_NAME);
		query.setParameter("firstname", firstname + "%");
		query.setParameter("lastname", lastname + "%");
		return (List<Person>) query.list();
	}
	
	@Override
	public List<Person> getActorsByMovieTitle(String title) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_ACTORS_BY_MOVIE_TITLE);
		query.setParameter("title", title + "%");
		return (List<Person>) query.list();
	}

	@Override
	public List<Person> getActorsByMovieTitleAndYear(String title, String year) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR);
		query.setParameter("title", title);
		query.setParameter("production_year", Integer.parseInt(year));
		return (List<Person>) query.list();
	}
	
	@Override
	public int insertPerson(Person person) {
		Person updatedPerson = (Person) getCurrentSession().merge(person);
		return updatedPerson.getId();
	}
	
	protected final Session getCurrentSession() {
		return _sessionFactory.getCurrentSession();
	}


}
