package com.se325a3.smdb.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

@Service
public class SmdbServiceImpl implements SmdbService {

	private SmdbDao _smdbDao;
	
	@Autowired
	public SmdbServiceImpl(SmdbDao smdbDao) {
		_smdbDao = smdbDao;
	}
	
	@Override
	public Person getPersonById(int id) {
		Person person = _smdbDao.getPersonById(id);
		return person;
	}
	
	@Override
	public Person getActorById(int id) {
		Person actor = _smdbDao.getActorById(id);
		return actor;
	}

	@Override
	public Collection<Person> getActorsByName(String name) {
		List<Person> actors = _smdbDao.getActorsByName(name);
		return actors;
	}

	@Override
	public Collection<Person> getActorsByMovieTitle(String title) {
		List<Person> actors = _smdbDao.getActorsByMovieTitle(title);
		return actors;
	}
	
	@Override
	public Collection<Person> getActorsByMovieTitleAndYear(String title,
			String year) {
		List<Person> actors = _smdbDao.getActorsByMovieTitleAndYear(title, year);
		return actors;
	}

	@Override
	public Collection<Movie> getMoviesByTitle(String title) {
		List<Movie> movies = _smdbDao.getMoviesByTitle(title);
		return movies;
	}

	@Override
	public Collection<Movie> getMoviesByActorName(String name) {
		List<Movie> movies = _smdbDao.getMoviesByActorName(name);
		return movies;
	}

	@Override
	public Movie getMovieByTitleAndYear(String title, String year) {
		Movie movie = _smdbDao.getMovieByTitleAndYear(title, year);
		return movie;
	}

	@Override
	public Collection<Movie> getMoviesByActorID(int id) {
		List<Movie> movies = _smdbDao.getMoviesByActorID(id);
		return movies;
	}

	@Override
	public int insertPerson(Person person) {
		return _smdbDao.insertPerson(person);
	}

	@Override
	public Map<String, Object> insertMovie(Movie movie) {
		return _smdbDao.insertMovie(movie);
	}

	@Override
	public Map<String, Object> insertRole(Role role) {
		// TODO Auto-generated method stub
		return _smdbDao.insertRole(role);
	}

}
