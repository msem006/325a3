package com.se325a3.smdb.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;

@Service
public class SmdbServiceImpl implements SmdbService {

	private SmdbDao _smdbDao;
	
	@Autowired
	public SmdbServiceImpl(SmdbDao smdbDao) {
		_smdbDao = smdbDao;
	}
	
	@Override
	public Person getActorById(String id) {
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
	public Collection<Movie> getMoviesByActorID(String id) {
		List<Movie> movies = _smdbDao.getMoviesByActorID(id);
		return movies;
	}

}
