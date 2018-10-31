package com.arquitetira.trabalho.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arquitetira.trabalho.app.domain.PlanoContabil;
import com.arquitetira.trabalho.app.repository.PlanoContabilRepository;
import com.arquitetira.trabalho.app.repository.search.PlanoContabilSearchRepository;
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
 * REST controller for managing PlanoContabil.
 */
@RestController
@RequestMapping("/api")
public class PlanoContabilResource {

    private final Logger log = LoggerFactory.getLogger(PlanoContabilResource.class);

    private static final String ENTITY_NAME = "planoContabil";

    private PlanoContabilRepository planoContabilRepository;

    private PlanoContabilSearchRepository planoContabilSearchRepository;

    public PlanoContabilResource(PlanoContabilRepository planoContabilRepository, PlanoContabilSearchRepository planoContabilSearchRepository) {
        this.planoContabilRepository = planoContabilRepository;
        this.planoContabilSearchRepository = planoContabilSearchRepository;
    }

    /**
     * POST  /plano-contabils : Create a new planoContabil.
     *
     * @param planoContabil the planoContabil to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planoContabil, or with status 400 (Bad Request) if the planoContabil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plano-contabils")
    @Timed
    public ResponseEntity<PlanoContabil> createPlanoContabil(@RequestBody PlanoContabil planoContabil) throws URISyntaxException {
        log.debug("REST request to save PlanoContabil : {}", planoContabil);
        if (planoContabil.getId() != null) {
            throw new BadRequestAlertException("A new planoContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoContabil result = planoContabilRepository.save(planoContabil);
        planoContabilSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/plano-contabils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plano-contabils : Updates an existing planoContabil.
     *
     * @param planoContabil the planoContabil to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planoContabil,
     * or with status 400 (Bad Request) if the planoContabil is not valid,
     * or with status 500 (Internal Server Error) if the planoContabil couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plano-contabils")
    @Timed
    public ResponseEntity<PlanoContabil> updatePlanoContabil(@RequestBody PlanoContabil planoContabil) throws URISyntaxException {
        log.debug("REST request to update PlanoContabil : {}", planoContabil);
        if (planoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoContabil result = planoContabilRepository.save(planoContabil);
        planoContabilSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planoContabil.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plano-contabils : get all the planoContabils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planoContabils in body
     */
    @GetMapping("/plano-contabils")
    @Timed
    public List<PlanoContabil> getAllPlanoContabils() {
        log.debug("REST request to get all PlanoContabils");
        return planoContabilRepository.findAll();
    }

    /**
     * GET  /plano-contabils/:id : get the "id" planoContabil.
     *
     * @param id the id of the planoContabil to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planoContabil, or with status 404 (Not Found)
     */
    @GetMapping("/plano-contabils/{id}")
    @Timed
    public ResponseEntity<PlanoContabil> getPlanoContabil(@PathVariable Long id) {
        log.debug("REST request to get PlanoContabil : {}", id);
        Optional<PlanoContabil> planoContabil = planoContabilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(planoContabil);
    }

    /**
     * DELETE  /plano-contabils/:id : delete the "id" planoContabil.
     *
     * @param id the id of the planoContabil to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plano-contabils/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanoContabil(@PathVariable Long id) {
        log.debug("REST request to delete PlanoContabil : {}", id);

        planoContabilRepository.deleteById(id);
        planoContabilSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/plano-contabils?query=:query : search for the planoContabil corresponding
     * to the query.
     *
     * @param query the query of the planoContabil search
     * @return the result of the search
     */
    @GetMapping("/_search/plano-contabils")
    @Timed
    public List<PlanoContabil> searchPlanoContabils(@RequestParam String query) {
        log.debug("REST request to search PlanoContabils for query {}", query);
        return StreamSupport
            .stream(planoContabilSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
