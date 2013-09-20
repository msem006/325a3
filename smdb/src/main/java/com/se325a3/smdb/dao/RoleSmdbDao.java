package com.se325a3.smdb.dao;

import com.se325a3.smdb.model.Role;

/**
 * Queries that insert roles into the Database
 *
 */
public interface RoleSmdbDao {

	/**
	 * Insert a Role into the database. The Person and Movie that it references
	 * must already exist in the database.
	 * 
	 * @param role
	 *            Role object to be saved in database
	 * @return A map containing the title, producton year and description fields
	 *         of the inserted Role
	 */
	void insertRole(Role role);
	
}
