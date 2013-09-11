package com.se325a3.smdb.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;

@Service
public class SmdbServiceImpl implements SmdbServce {

	private SmdbDao _smdbDao;
	
	@Autowired
	public SmdbServiceImpl(SmdbDao smdbDao) {
		_smdbDao = smdbDao;
	}
	
	@Override
	public Collection<Person> getActorsByName(String name) {
		List<Person> actors = _smdbDao.getActorsByName(name);
		return actors;
	}

	@Override
	public Collection<Person> getActorsByMovieTitle(String title) {
		List<Person> actors = _smdbDao.getActorsByName(title);
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

}
