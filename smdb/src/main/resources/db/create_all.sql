DROP TABLE IF EXISTS MOVIE;
DROP TABLE IF EXISTS PERSON;
DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS DIRECTOR;
DROP TABLE IF EXISTS WRITER;
DROP TABLE IF EXISTS CREW;

SET IGNORECASE=TRUE;

CREATE TABLE MOVIE
(
	title varchar(40) NOT NULL,
	production_year	integer NOT NULL,
	country varchar(20) NOT NULL,
	run_time integer NOT NULL,
	major_genre varchar(15),
	CONSTRAINT pk_movie PRIMARY KEY(title,production_year)
);

CREATE TABLE PERSON
(
	id varchar(8) NOT NULL,
	first_name varchar(15) NOT NULL,
	last_name varchar(30) NOT NULL,
	year_born integer,
	CONSTRAINT pk_person PRIMARY KEY(id)
);

CREATE TABLE ROLE
(
	id varchar(8) NOT NULL,
	title varchar(40) NOT NULL,
	production_year	integer NOT NULL,
	description varchar(100) NOT NULL,
	credits varchar(40),
	CONSTRAINT pk_role PRIMARY KEY(title,production_year,description),
	CONSTRAINT fk_role_1 FOREIGN KEY(title,production_year) REFERENCES MOVIE(title,production_year),
	CONSTRAINT fk_role_2 FOREIGN KEY(id) REFERENCES PERSON(id)
);

CREATE TABLE DIRECTOR
(
	id varchar(8) NOT NULL,
	title varchar(40) NOT NULL,
	production_year	integer	NOT NULL,
	CONSTRAINT pk_director PRIMARY KEY(title,production_year),
	CONSTRAINT fk_director_1 FOREIGN KEY(title,production_year) REFERENCES MOVIE(title,production_year),
	CONSTRAINT fk_director_2 FOREIGN KEY(id) REFERENCES PERSON(id)
);

CREATE TABLE WRITER
(
	id varchar(8) NOT NULL,
	title varchar(40) NOT NULL,
	production_year	integer NOT NULL,
	credits	varchar(40) ,
	CONSTRAINT pk_writer PRIMARY KEY(id,title,production_year),
	CONSTRAINT fk_writer_1 FOREIGN KEY(title,production_year) REFERENCES MOVIE(title,production_year),
	CONSTRAINT fk_writer_2 FOREIGN KEY(id) REFERENCES PERSON(id)
);

CREATE TABLE CREW
(
	id varchar(8) NOT NULL,
	title varchar(40) NOT NULL,
	production_year	integer NOT NULL,
	contribution varchar(30),
	CONSTRAINT pk_crew PRIMARY KEY(id,title,production_year),
	CONSTRAINT fk_crew_1 FOREIGN KEY(title,production_year) REFERENCES MOVIE(title,production_year),
	CONSTRAINT fk_crew_2 FOREIGN KEY(id) REFERENCES PERSON(id)
);