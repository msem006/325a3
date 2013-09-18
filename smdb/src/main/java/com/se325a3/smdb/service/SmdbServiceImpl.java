package com.se325a3.smdb.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se325a3.smdb.dao.MovieSmdbDao;
import com.se325a3.smdb.dao.PersonSmdbDao;
import com.se325a3.smdb.dao.RoleSmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

@Service
public class SmdbServiceImpl implements SmdbService {

	private PersonSmdbDao _personSmdbDao;
	private MovieSmdbDao _movieSmdbDao;
	private RoleSmdbDao _roleSmdbDao;
	
	@Autowired
	public SmdbServiceImpl(PersonSmdbDao personSmdbDao, MovieSmdbDao movieSmdbDao, RoleSmdbDao roleSmdbDao) {
		_personSmdbDao = personSmdbDao;
		_movieSmdbDao = movieSmdbDao;
		_roleSmdbDao = roleSmdbDao;
	}
	
	@Override
	public Person getPersonById(int id) {
		Person person = _personSmdbDao.getPersonById(id);
		return person;
	}
	
	@Override
	public Person getActorById(int id) {
		Person actor = _personSmdbDao.getActorById(id);
		return actor;
	}

	@Override
	public Collection<Person> getActorsByFirstName(String name) {
		List<Person> actors = _personSmdbDao.getActorsByFirstName(name);
		return actors;
	}

	@Override
	public Collection<Person> getActorsByLastName(String name) {
		List<Person> actors = _personSmdbDao.getActorsByLastName(name);
		return actors;
	}

	@Override
	public Collection<Person> getActorsByFirstNameAndLastName(String firstname,
			String lastname) {
		List<Person> actors = _personSmdbDao.getActorsByFirstNameAndLastName(firstname, lastname);
		return actors;
	}

	@Override
	public Collection<Person> getActorsByFirstNameOrLastName(String firstname,
			String lastname) {
		List<Person> actors = _personSmdbDao.getActorsByFirstNameOrLastName(firstname, lastname);
		return actors;
	}
	
	@Override
	public Collection<Person> getActorsByMovieTitle(String title) {
		List<Person> actors = _personSmdbDao.getActorsByMovieTitle(title);
		return actors;
	}
	
	@Override
	public Collection<Person> getActorsByMovieTitleAndYear(String title,
			String year) {
		List<Person> actors = _personSmdbDao.getActorsByMovieTitleAndYear(title, year);
		return actors;
	}

	@Override
	public Collection<Movie> getMoviesByTitle(String title) {
		List<Movie> movies = _movieSmdbDao.getMoviesByTitle(title);
		return movies;
	}

	@Override
	public Collection<Movie> getMoviesByActorName(String name) {
		List<Movie> movies = _movieSmdbDao.getMoviesByActorName(name);
		return movies;
	}

	@Override
	public Movie getMovieByTitleAndYear(String title, String year) {
		Movie movie = _movieSmdbDao.getMovieByTitleAndYear(title, year);
		return movie;
	}

	@Override
	public Collection<Movie> getMoviesByActorID(int id) {
		List<Movie> movies = _movieSmdbDao.getMoviesByActorID(id);
		return movies;
	}

	@Override
	public int insertPerson(Person person) {
		return _personSmdbDao.insertPerson(person);
	}

	@Override
	public void insertMovie(Movie movie) {
		_movieSmdbDao.insertMovie(movie);
	}

	@Override
	public void insertRole(Role role) {
		_roleSmdbDao.insertRole(role);
	}

}
