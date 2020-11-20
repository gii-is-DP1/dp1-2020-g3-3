package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.petclinic.model.BaseEntity;



/**
 * 
 * 
 * */
public interface AzafatoRepository extends Repository<Azafato, Integer>{

	
	/**
	 * Save an <code>Azafato</code> to the data store, either inserting or updating it.
	 * @param azafato the <code>Azafato</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Azafato azafato) throws DataAccessException;
	
	
	
	/**
	 * Retrieve an <code>Azafato</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Azafato</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */	
	@Query("SELECT azafato FROM Azafatos owner left join fetch azafato WHERE azafato.id =:id")
	public Azafato findById(@Param("id") int id);

}
