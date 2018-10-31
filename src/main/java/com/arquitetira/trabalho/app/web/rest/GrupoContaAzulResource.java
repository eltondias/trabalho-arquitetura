package com.arquitetira.trabalho.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arquitetira.trabalho.app.domain.GrupoContaAzul;
import com.arquitetira.trabalho.app.repository.GrupoContaAzulRepository;
import com.arquitetira.trabalho.app.repository.search.GrupoContaAzulSearchRepository;
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
 * REST controller for managing GrupoContaAzul.
 */
@RestController
@RequestMapping("/api")
public class GrupoContaAzulResource {

    private final Logger log = LoggerFactory.getLogger(GrupoContaAzulResource.class);

    private static final String ENTITY_NAME = "grupoContaAzul";

    private GrupoContaAzulRepository grupoContaAzulRepository;

    private GrupoContaAzulSearchRepository grupoContaAzulSearchRepository;

    public GrupoContaAzulResource(GrupoContaAzulRepository grupoContaAzulRepository, GrupoContaAzulSearchRepository grupoContaAzulSearchRepository) {
        this.grupoContaAzulRepository = grupoContaAzulRepository;
        this.grupoContaAzulSearchRepository = grupoContaAzulSearchRepository;
    }

    /**
     * POST  /grupo-conta-azuls : Create a new grupoContaAzul.
     *
     * @param grupoContaAzul the grupoContaAzul to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grupoContaAzul, or with status 400 (Bad Request) if the grupoContaAzul has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grupo-conta-azuls")
    @Timed
    public ResponseEntity<GrupoContaAzul> createGrupoContaAzul(@RequestBody GrupoContaAzul grupoContaAzul) throws URISyntaxException {
        log.debug("REST request to save GrupoContaAzul : {}", grupoContaAzul);
        if (grupoContaAzul.getId() != null) {
            throw new BadRequestAlertException("A new grupoContaAzul cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoContaAzul result = grupoContaAzulRepository.save(grupoContaAzul);
        grupoContaAzulSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/grupo-conta-azuls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grupo-conta-azuls : Updates an existing grupoContaAzul.
     *
     * @param grupoContaAzul the grupoContaAzul to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grupoContaAzul,
     * or with status 400 (Bad Request) if the grupoContaAzul is not valid,
     * or with status 500 (Internal Server Error) if the grupoContaAzul couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grupo-conta-azuls")
    @Timed
    public ResponseEntity<GrupoContaAzul> updateGrupoContaAzul(@RequestBody GrupoContaAzul grupoContaAzul) throws URISyntaxException {
        log.debug("REST request to update GrupoContaAzul : {}", grupoContaAzul);
        if (grupoContaAzul.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoContaAzul result = grupoContaAzulRepository.save(grupoContaAzul);
        grupoContaAzulSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grupoContaAzul.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grupo-conta-azuls : get all the grupoContaAzuls.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of grupoContaAzuls in body
     */
    @GetMapping("/grupo-conta-azuls")
    @Timed
    public List<GrupoContaAzul> getAllGrupoContaAzuls() {
        log.debug("REST request to get all GrupoContaAzuls");
        return grupoContaAzulRepository.findAll();
    }

    /**
     * GET  /grupo-conta-azuls/:id : get the "id" grupoContaAzul.
     *
     * @param id the id of the grupoContaAzul to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grupoContaAzul, or with status 404 (Not Found)
     */
    @GetMapping("/grupo-conta-azuls/{id}")
    @Timed
    public ResponseEntity<GrupoContaAzul> getGrupoContaAzul(@PathVariable Long id) {
        log.debug("REST request to get GrupoContaAzul : {}", id);
        Optional<GrupoContaAzul> grupoContaAzul = grupoContaAzulRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grupoContaAzul);
    }

    /**
     * DELETE  /grupo-conta-azuls/:id : delete the "id" grupoContaAzul.
     *
     * @param id the id of the grupoContaAzul to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grupo-conta-azuls/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrupoContaAzul(@PathVariable Long id) {
        log.debug("REST request to delete GrupoContaAzul : {}", id);

        grupoContaAzulRepository.deleteById(id);
        grupoContaAzulSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/grupo-conta-azuls?query=:query : search for the grupoContaAzul corresponding
     * to the query.
     *
     * @param query the query of the grupoContaAzul search
     * @return the result of the search
     */
    @GetMapping("/_search/grupo-conta-azuls")
    @Timed
    public List<GrupoContaAzul> searchGrupoContaAzuls(@RequestParam String query) {
        log.debug("REST request to search GrupoContaAzuls for query {}", query);
        return StreamSupport
            .stream(grupoContaAzulSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
