package com.se325a3.smdb.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;

@Repository
public class HibernateSmdbDao implements SmdbDao {

	private static final String HQL_SELECT_ACTORS_BY_ID = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND p.id = :id";

	private static final String HQL_SELECT_ACTORS_BY_NAME = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(p.first_name) LIKE :name";

	private static final String HQL_SELECT_ACTORS_BY_MOVIE_TITLE = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id AND str(r.title) LIKE :title";

	private static final String HQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR = "SELECT DISTINCT p FROM Person p, Role r WHERE p.id = r.id "
			+ "AND r.rolePk.movie.moviePk.productionYear = :production_year AND r.title = :title";

	private static final String HQL_SELECT_MOVIES_BY_TITLE = "SELECT DISTINCT m FROM Movie m WHERE str(m.title) LIKE :title";

	private static final String HQL_SELECT_MOVIES_BY_ACTOR_NAME = "SELECT DISTINCT m FROM Person p, Role r, Movie m WHERE p.id = r.id "
			+ "AND r.rolePk.movie.moviePk.productionYear = m.moviePk.productionYear AND r.title = m.moviePk.title AND str(p.first_name) LIKE :name";

	private static final String HQL_SELECT_MOVIES_BY_TITLE_AND_YEAR = "SELECT DISTINCT m FROM Movie m WHERE m.moviePk.title = :title AND m.moviePk.productionYear = :production_year";

	private static final String HQL_SELECT_MOVIES_BY_ACTOR_ID = "SELECT DISTINCT m FROM Person p, Role r, Movie m "
			+ "WHERE p.id = r.id AND r.title = m.moviePk.title AND r.rolePk.movie.moviePk.productionYear = m.moviePk.productionYear "
			+ "AND p.id = :id";

	private SessionFactory _sessionFactory;

	@Autowired
	public HibernateSmdbDao(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public Person getActorById(String id) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_ACTORS_BY_ID);
		query.setParameter("id", id);
		return (Person) query.uniqueResult();
	}

	@Override
	@Transactional
	public List<Person> getActorsByName(String name) {
		Query query = getCurrentSession()
				.createQuery(HQL_SELECT_ACTORS_BY_NAME);
		query.setParameter("name", name + "%");
		return (List<Person>) query.list();
	}

	@Override
	@Transactional
	public List<Person> getActorsByMovieTitle(String title) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_ACTORS_BY_MOVIE_TITLE);
		query.setParameter("title", title + "%");
		return (List<Person>) query.list();
	}

	@Override
	@Transactional
	public List<Person> getActorsByMovieTitleAndYear(String title, String year) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR);
		query.setParameter("title", title);
		query.setParameter("production_year", Integer.parseInt(year));
		return (List<Person>) query.list();
	}

	@Override
	@Transactional
	public List<Movie> getMoviesByTitle(String title) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_MOVIES_BY_TITLE);
		query.setParameter("title", title + "%");
		return (List<Movie>) query.list();
	}

	@Override
	@Transactional
	public List<Movie> getMoviesByActorName(String name) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_MOVIES_BY_ACTOR_NAME);
		query.setParameter("name", name + "%");
		return (List<Movie>) query.list();
	}

	@Override
	@Transactional
	public Movie getMovieByTitleAndYear(String title, String year) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_MOVIES_BY_TITLE_AND_YEAR);
		query.setParameter("title", title);
		query.setParameter("production_year", Integer.parseInt(year));
		return (Movie) query.uniqueResult();
	}

	@Override
	@Transactional
	public List<Movie> getMoviesByActorID(String id) {
		Query query = getCurrentSession().createQuery(
				HQL_SELECT_MOVIES_BY_ACTOR_ID);
		query.setParameter("id", id);
		return (List<Movie>) query.list();
	}

	protected final Session getCurrentSession() {
		return _sessionFactory.getCurrentSession();
	}

}
