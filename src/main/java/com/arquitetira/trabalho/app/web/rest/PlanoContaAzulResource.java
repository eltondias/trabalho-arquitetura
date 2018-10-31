package com.arquitetira.trabalho.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arquitetira.trabalho.app.domain.PlanoContaAzul;
import com.arquitetira.trabalho.app.repository.PlanoContaAzulRepository;
import com.arquitetira.trabalho.app.repository.search.PlanoContaAzulSearchRepository;
import com.arquitetira.trabalho.app.web.rest.errors.BadRequestAlertException;
import com.arquitetira.trabalho.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PlanoContaAzul.
 */
@RestController
@RequestMapping("/api")
public class PlanoContaAzulResource {

    private final Logger log = LoggerFactory.getLogger(PlanoContaAzulResource.class);

    private static final String ENTITY_NAME = "planoContaAzul";

    private PlanoContaAzulRepository planoContaAzulRepository;

    private PlanoContaAzulSearchRepository planoContaAzulSearchRepository;

    public PlanoContaAzulResource(PlanoContaAzulRepository planoContaAzulRepository, PlanoContaAzulSearchRepository planoContaAzulSearchRepository) {
        this.planoContaAzulRepository = planoContaAzulRepository;
        this.planoContaAzulSearchRepository = planoContaAzulSearchRepository;
    }

    /**
     * POST  /plano-conta-azuls : Create a new planoContaAzul.
     *
     * @param planoContaAzul the planoContaAzul to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planoContaAzul, or with status 400 (Bad Request) if the planoContaAzul has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plano-conta-azuls")
    @Timed
    public ResponseEntity<PlanoContaAzul> createPlanoContaAzul(@RequestBody PlanoContaAzul planoContaAzul) throws URISyntaxException {
        log.debug("REST request to save PlanoContaAzul : {}", planoContaAzul);
        if (planoContaAzul.getId() != null) {
            throw new BadRequestAlertException("A new planoContaAzul cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoContaAzul result = planoContaAzulRepository.save(planoContaAzul);
        planoContaAzulSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/plano-conta-azuls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plano-conta-azuls : Updates an existing planoContaAzul.
     *
     * @param planoContaAzul the planoContaAzul to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planoContaAzul,
     * or with status 400 (Bad Request) if the planoContaAzul is not valid,
     * or with status 500 (Internal Server Error) if the planoContaAzul couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plano-conta-azuls")
    @Timed
    public ResponseEntity<PlanoContaAzul> updatePlanoContaAzul(@RequestBody PlanoContaAzul planoContaAzul) throws URISyntaxException {
        log.debug("REST request to update PlanoContaAzul : {}", planoContaAzul);
        if (planoContaAzul.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoContaAzul result = planoContaAzulRepository.save(planoContaAzul);
        planoContaAzulSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planoContaAzul.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plano-conta-azuls : get all the planoContaAzuls.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planoContaAzuls in body
     */
    @GetMapping("/plano-conta-azuls")
    @Timed
    public List<PlanoContaAzul> getAllPlanoContaAzuls() {
        log.debug("REST request to get all PlanoContaAzuls");
        return planoContaAzulRepository.findAll();
    }

    /**
     * GET  /plano-conta-azuls/:id : get the "id" planoContaAzul.
     *
     * @param id the id of the planoContaAzul to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planoContaAzul, or with status 404 (Not Found)
     */
    @GetMapping("/plano-conta-azuls/{id}")
    @Timed
    public ResponseEntity<PlanoContaAzul> getPlanoContaAzul(@PathVariable Long id) {
        log.debug("REST request to get PlanoContaAzul : {}", id);
        Optional<PlanoContaAzul> planoContaAzul = planoContaAzulRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(planoContaAzul);
    }

    /**
     * DELETE  /plano-conta-azuls/:id : delete the "id" planoContaAzul.
     *
     * @param id the id of the planoContaAzul to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plano-conta-azuls/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanoContaAzul(@PathVariable Long id) {
        log.debug("REST request to delete PlanoContaAzul : {}", id);

        planoContaAzulRepository.deleteById(id);
        planoContaAzulSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/plano-conta-azuls?query=:query : search for the planoContaAzul corresponding
     * to the query.
     *
     * @param query the query of the planoContaAzul search
     * @return the result of the search
     */
    @GetMapping("/_search/plano-conta-azuls")
    @Timed
    public List<PlanoContaAzul> searchPlanoContaAzuls(@RequestParam String query) {
        log.debug("REST request to search PlanoContaAzuls for query {}", query);
        return StreamSupport
            .stream(planoContaAzulSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
