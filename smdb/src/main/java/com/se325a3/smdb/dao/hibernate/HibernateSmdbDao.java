package com.se325a3.smdb.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;

@Repository
public class HibernateSmdbDao implements SmdbDao {

	SessionFactory _sessionFactory;
	
	@Autowired
	public HibernateSmdbDao(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Person> getActorsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> getActorsByMovieTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> getMoviesByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> getMoviesByActorName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
