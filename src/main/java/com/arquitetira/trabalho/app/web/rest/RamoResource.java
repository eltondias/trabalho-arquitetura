package com.arquitetira.trabalho.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arquitetira.trabalho.app.domain.Ramo;
import com.arquitetira.trabalho.app.repository.RamoRepository;
import com.arquitetira.trabalho.app.repository.search.RamoSearchRepository;
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
 * REST controller for managing Ramo.
 */
@RestController
@RequestMapping("/api")
public class RamoResource {

    private final Logger log = LoggerFactory.getLogger(RamoResource.class);

    private static final String ENTITY_NAME = "ramo";

    private RamoRepository ramoRepository;

    private RamoSearchRepository ramoSearchRepository;

    public RamoResource(RamoRepository ramoRepository, RamoSearchRepository ramoSearchRepository) {
        this.ramoRepository = ramoRepository;
        this.ramoSearchRepository = ramoSearchRepository;
    }

    /**
     * POST  /ramos : Create a new ramo.
     *
     * @param ramo the ramo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ramo, or with status 400 (Bad Request) if the ramo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ramos")
    @Timed
    public ResponseEntity<Ramo> createRamo(@RequestBody Ramo ramo) throws URISyntaxException {
        log.debug("REST request to save Ramo : {}", ramo);
        if (ramo.getId() != null) {
            throw new BadRequestAlertException("A new ramo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ramo result = ramoRepository.save(ramo);
        ramoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ramos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ramos : Updates an existing ramo.
     *
     * @param ramo the ramo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ramo,
     * or with status 400 (Bad Request) if the ramo is not valid,
     * or with status 500 (Internal Server Error) if the ramo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ramos")
    @Timed
    public ResponseEntity<Ramo> updateRamo(@RequestBody Ramo ramo) throws URISyntaxException {
        log.debug("REST request to update Ramo : {}", ramo);
        if (ramo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ramo result = ramoRepository.save(ramo);
        ramoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ramo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ramos : get all the ramos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ramos in body
     */
    @GetMapping("/ramos")
    @Timed
    public List<Ramo> getAllRamos() {
        log.debug("REST request to get all Ramos");
        return ramoRepository.findAll();
    }

    /**
     * GET  /ramos/:id : get the "id" ramo.
     *
     * @param id the id of the ramo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ramo, or with status 404 (Not Found)
     */
    @GetMapping("/ramos/{id}")
    @Timed
    public ResponseEntity<Ramo> getRamo(@PathVariable Long id) {
        log.debug("REST request to get Ramo : {}", id);
        Optional<Ramo> ramo = ramoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ramo);
    }

    /**
     * DELETE  /ramos/:id : delete the "id" ramo.
     *
     * @param id the id of the ramo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ramos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRamo(@PathVariable Long id) {
        log.debug("REST request to delete Ramo : {}", id);

        ramoRepository.deleteById(id);
        ramoSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ramos?query=:query : search for the ramo corresponding
     * to the query.
     *
     * @param query the query of the ramo search
     * @return the result of the search
     */
    @GetMapping("/_search/ramos")
    @Timed
    public List<Ramo> searchRamos(@RequestParam String query) {
        log.debug("REST request to search Ramos for query {}", query);
        return StreamSupport
            .stream(ramoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
