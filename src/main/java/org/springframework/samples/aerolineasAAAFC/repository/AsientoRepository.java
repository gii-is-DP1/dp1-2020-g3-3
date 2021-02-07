package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.stereotype.Repository;

public interface AsientoRepository extends CrudRepository<Asiento,Integer> {

}
