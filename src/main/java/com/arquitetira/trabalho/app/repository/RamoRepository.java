package com.arquitetira.trabalho.app.repository;

import com.arquitetira.trabalho.app.domain.Ramo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ramo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RamoRepository extends JpaRepository<Ramo, Long> {

}
