package com.se325a3.smdb.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

@Repository
@Transactional
public class HibernateSmdbDao implements SmdbDao {
	
	private static final String HQL_SELECT_ACTOR_BY_ID = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND p.id = :id";

	private static final String HQL_SELECT_ACTORS_BY_FIRST_NAME = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(p.first_name) LIKE :name";
	
	private static final String HQL_SELECT_ACTORS_BY_LAST_NAME = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(p.last_name) LIKE :name";
	
	private static final String HQL_SELECT_ACTORS_BY_FIRST_NAME_AND_LAST_NAME = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(p.first_name) LIKE :firstname AND str(p.last_name) LIKE :lastname";
	
	private static final String HQL_SELECT_ACTORS_BY_FIRST_NAME_OR_LAST_NAME = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND  str(p.first_name) LIKE :firstname OR str(p.last_name) LIKE :lastname AND p.id = r.id";

	private static final String HQL_SELECT_ACTORS_BY_MOVIE_TITLE = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(r.title) LIKE :title";

	private static final String HQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id "
			+ "AND r.rolePk.movie.moviePk.productionYear = :production_year AND r.title = :title";

	private static final String HQL_SELECT_MOVIES_BY_TITLE = "SELECT DISTINCT m FROM Movie m WHERE str(m.title) LIKE :title";

	private static final String HQL_SELECT_MOVIES_BY_ACTOR_NAME = "SELECT DISTINCT m FROM Person p, Role r, Movie m WHERE p.id = r.id "
			+ "AND r.rolePk.movie.moviePk.productionYear = m.moviePk.productionYear AND r.title = m.moviePk.title AND str(p.first_name) LIKE :name";

	private static final String HQL_SELECT_MOVIE_BY_TITLE_AND_YEAR = "SELECT DISTINCT m FROM Movie m WHERE m.moviePk.title = :title AND m.moviePk.productionYear = :production_year";

	private static final String HQL_SELECT_MOVIES_BY_ACTOR_ID = "SELECT DISTINCT m FROM Person p, Role r, Movie m "
			+ "WHERE p.id = r.id AND r.title = m.moviePk.title AND r.rolePk.movie.moviePk.productionYear = m.moviePk.productionYear "
			+ "AND p.id = :id";

	private SessionFactory _sessionFactory;

	@Autowired
	public HibernateSmdbDao(SessionFactory sessionFactory) {
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
	public List<Movie> getMoviesByTitle(String title) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_MOVIES_BY_TITLE);
		query.setParameter("title", title + "%");
		return (List<Movie>) query.list();
	}

	@Override
	public List<Movie> getMoviesByActorName(String name) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_MOVIES_BY_ACTOR_NAME);
		query.setParameter("name", name + "%");
		return (List<Movie>) query.list();
	}

	@Override
	public Movie getMovieByTitleAndYear(String title, String year) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_MOVIE_BY_TITLE_AND_YEAR);
		query.setParameter("title", title);
		query.setParameter("production_year", Integer.parseInt(year));
		return (Movie) query.uniqueResult();
	}

	@Override
	public List<Movie> getMoviesByActorID(int id) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_MOVIES_BY_ACTOR_ID);
		query.setParameter("id", id);
		return (List<Movie>) query.list();
	}

	@Override
	public int insertPerson(Person person) {
		Person updatedPerson = (Person) getCurrentSession().merge(person);
		return updatedPerson.getId();
	}

	@Override
	public void insertMovie(Movie movie) {
		getCurrentSession().save(movie);
		getCurrentSession().flush();
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
