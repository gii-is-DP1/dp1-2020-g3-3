<<<<<<< HEAD
package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;

public interface PersonalControlRepository extends Repository<PersonalControl, Integer>{
	
	// Guardado de un personal de control
	void save(PersonalControl pOficina) throws DataAccessException;
	
	// Busqueda por ID
	PersonalControl findById(int id) throws DataAccessException;

}
=======
package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;

public interface PersonalControlRepository extends Repository<PersonalControl, Integer>{
	
	// Guardado de un personal de control
	void save(PersonalControl pOficina) throws DataAccessException;
	
	// Busqueda por ID
	PersonalControl findById(int id) throws DataAccessException;

}
>>>>>>> branch 'master' of https://github.com/gii-is-DP1/dp1-2020-g3-3.git
