package com.se325a3.smdb.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.dao.MovieSmdbDao;
import com.se325a3.smdb.model.Movie;

@Repository
@Transactional
public class HibernateMovieSmdbDao implements MovieSmdbDao {
	
	private static final String HQL_SELECT_MOVIES_BY_TITLE = "SELECT DISTINCT m FROM Movie m WHERE str(m.title) LIKE :title";

	private static final String HQL_SELECT_MOVIES_BY_ACTOR_NAME = "SELECT DISTINCT m FROM Person p, Role r, Movie m WHERE p.id = r.id "
			+ "AND r.rolePk.movie.moviePk.productionYear = m.moviePk.productionYear AND r.title = m.moviePk.title AND str(p.first_name) LIKE :name";

	private static final String HQL_SELECT_MOVIE_BY_TITLE_AND_YEAR = "SELECT DISTINCT m FROM Movie m WHERE m.moviePk.title = :title AND m.moviePk.productionYear = :production_year";

	private static final String HQL_SELECT_MOVIES_BY_ACTOR_ID = "SELECT DISTINCT m FROM Person p, Role r, Movie m "
			+ "WHERE p.id = r.id AND r.title = m.moviePk.title AND r.rolePk.movie.moviePk.productionYear = m.moviePk.productionYear "
			+ "AND p.id = :id";

	private SessionFactory _sessionFactory;

	@Autowired
	public HibernateMovieSmdbDao(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
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
	public void insertMovie(Movie movie) {
		getCurrentSession().save(movie);
		getCurrentSession().flush();
	}
	
	protected final Session getCurrentSession() {
		return _sessionFactory.getCurrentSession();
	}


}
