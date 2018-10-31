package com.arquitetira.trabalho.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arquitetira.trabalho.app.domain.Enquadramento;
import com.arquitetira.trabalho.app.repository.EnquadramentoRepository;
import com.arquitetira.trabalho.app.repository.search.EnquadramentoSearchRepository;
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
 * REST controller for managing Enquadramento.
 */
@RestController
@RequestMapping("/api")
public class EnquadramentoResource {

    private final Logger log = LoggerFactory.getLogger(EnquadramentoResource.class);

    private static final String ENTITY_NAME = "enquadramento";

    private EnquadramentoRepository enquadramentoRepository;

    private EnquadramentoSearchRepository enquadramentoSearchRepository;

    public EnquadramentoResource(EnquadramentoRepository enquadramentoRepository, EnquadramentoSearchRepository enquadramentoSearchRepository) {
        this.enquadramentoRepository = enquadramentoRepository;
        this.enquadramentoSearchRepository = enquadramentoSearchRepository;
    }

    /**
     * POST  /enquadramentos : Create a new enquadramento.
     *
     * @param enquadramento the enquadramento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enquadramento, or with status 400 (Bad Request) if the enquadramento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enquadramentos")
    @Timed
    public ResponseEntity<Enquadramento> createEnquadramento(@RequestBody Enquadramento enquadramento) throws URISyntaxException {
        log.debug("REST request to save Enquadramento : {}", enquadramento);
        if (enquadramento.getId() != null) {
            throw new BadRequestAlertException("A new enquadramento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Enquadramento result = enquadramentoRepository.save(enquadramento);
        enquadramentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/enquadramentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enquadramentos : Updates an existing enquadramento.
     *
     * @param enquadramento the enquadramento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enquadramento,
     * or with status 400 (Bad Request) if the enquadramento is not valid,
     * or with status 500 (Internal Server Error) if the enquadramento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enquadramentos")
    @Timed
    public ResponseEntity<Enquadramento> updateEnquadramento(@RequestBody Enquadramento enquadramento) throws URISyntaxException {
        log.debug("REST request to update Enquadramento : {}", enquadramento);
        if (enquadramento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Enquadramento result = enquadramentoRepository.save(enquadramento);
        enquadramentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enquadramento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enquadramentos : get all the enquadramentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of enquadramentos in body
     */
    @GetMapping("/enquadramentos")
    @Timed
    public List<Enquadramento> getAllEnquadramentos() {
        log.debug("REST request to get all Enquadramentos");
        return enquadramentoRepository.findAll();
    }

    /**
     * GET  /enquadramentos/:id : get the "id" enquadramento.
     *
     * @param id the id of the enquadramento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enquadramento, or with status 404 (Not Found)
     */
    @GetMapping("/enquadramentos/{id}")
    @Timed
    public ResponseEntity<Enquadramento> getEnquadramento(@PathVariable Long id) {
        log.debug("REST request to get Enquadramento : {}", id);
        Optional<Enquadramento> enquadramento = enquadramentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(enquadramento);
    }

    /**
     * DELETE  /enquadramentos/:id : delete the "id" enquadramento.
     *
     * @param id the id of the enquadramento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enquadramentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnquadramento(@PathVariable Long id) {
        log.debug("REST request to delete Enquadramento : {}", id);

        enquadramentoRepository.deleteById(id);
        enquadramentoSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/enquadramentos?query=:query : search for the enquadramento corresponding
     * to the query.
     *
     * @param query the query of the enquadramento search
     * @return the result of the search
     */
    @GetMapping("/_search/enquadramentos")
    @Timed
    public List<Enquadramento> searchEnquadramentos(@RequestParam String query) {
        log.debug("REST request to search Enquadramentos for query {}", query);
        return StreamSupport
            .stream(enquadramentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
