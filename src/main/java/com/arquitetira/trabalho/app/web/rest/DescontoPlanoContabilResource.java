package com.arquitetira.trabalho.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arquitetira.trabalho.app.domain.DescontoPlanoContabil;
import com.arquitetira.trabalho.app.repository.DescontoPlanoContabilRepository;
import com.arquitetira.trabalho.app.repository.search.DescontoPlanoContabilSearchRepository;
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
 * REST controller for managing DescontoPlanoContabil.
 */
@RestController
@RequestMapping("/api")
public class DescontoPlanoContabilResource {

    private final Logger log = LoggerFactory.getLogger(DescontoPlanoContabilResource.class);

    private static final String ENTITY_NAME = "descontoPlanoContabil";

    private DescontoPlanoContabilRepository descontoPlanoContabilRepository;

    private DescontoPlanoContabilSearchRepository descontoPlanoContabilSearchRepository;

    public DescontoPlanoContabilResource(DescontoPlanoContabilRepository descontoPlanoContabilRepository, DescontoPlanoContabilSearchRepository descontoPlanoContabilSearchRepository) {
        this.descontoPlanoContabilRepository = descontoPlanoContabilRepository;
        this.descontoPlanoContabilSearchRepository = descontoPlanoContabilSearchRepository;
    }

    /**
     * POST  /desconto-plano-contabils : Create a new descontoPlanoContabil.
     *
     * @param descontoPlanoContabil the descontoPlanoContabil to create
     * @return the ResponseEntity with status 201 (Created) and with body the new descontoPlanoContabil, or with status 400 (Bad Request) if the descontoPlanoContabil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/desconto-plano-contabils")
    @Timed
    public ResponseEntity<DescontoPlanoContabil> createDescontoPlanoContabil(@RequestBody DescontoPlanoContabil descontoPlanoContabil) throws URISyntaxException {
        log.debug("REST request to save DescontoPlanoContabil : {}", descontoPlanoContabil);
        if (descontoPlanoContabil.getId() != null) {
            throw new BadRequestAlertException("A new descontoPlanoContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DescontoPlanoContabil result = descontoPlanoContabilRepository.save(descontoPlanoContabil);
        descontoPlanoContabilSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/desconto-plano-contabils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /desconto-plano-contabils : Updates an existing descontoPlanoContabil.
     *
     * @param descontoPlanoContabil the descontoPlanoContabil to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated descontoPlanoContabil,
     * or with status 400 (Bad Request) if the descontoPlanoContabil is not valid,
     * or with status 500 (Internal Server Error) if the descontoPlanoContabil couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/desconto-plano-contabils")
    @Timed
    public ResponseEntity<DescontoPlanoContabil> updateDescontoPlanoContabil(@RequestBody DescontoPlanoContabil descontoPlanoContabil) throws URISyntaxException {
        log.debug("REST request to update DescontoPlanoContabil : {}", descontoPlanoContabil);
        if (descontoPlanoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DescontoPlanoContabil result = descontoPlanoContabilRepository.save(descontoPlanoContabil);
        descontoPlanoContabilSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, descontoPlanoContabil.getId().toString()))
            .body(result);
    }

    /**
     * GET  /desconto-plano-contabils : get all the descontoPlanoContabils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of descontoPlanoContabils in body
     */
    @GetMapping("/desconto-plano-contabils")
    @Timed
    public List<DescontoPlanoContabil> getAllDescontoPlanoContabils() {
        log.debug("REST request to get all DescontoPlanoContabils");
        return descontoPlanoContabilRepository.findAll();
    }

    /**
     * GET  /desconto-plano-contabils/:id : get the "id" descontoPlanoContabil.
     *
     * @param id the id of the descontoPlanoContabil to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the descontoPlanoContabil, or with status 404 (Not Found)
     */
    @GetMapping("/desconto-plano-contabils/{id}")
    @Timed
    public ResponseEntity<DescontoPlanoContabil> getDescontoPlanoContabil(@PathVariable Long id) {
        log.debug("REST request to get DescontoPlanoContabil : {}", id);
        Optional<DescontoPlanoContabil> descontoPlanoContabil = descontoPlanoContabilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(descontoPlanoContabil);
    }

    /**
     * DELETE  /desconto-plano-contabils/:id : delete the "id" descontoPlanoContabil.
     *
     * @param id the id of the descontoPlanoContabil to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/desconto-plano-contabils/{id}")
    @Timed
    public ResponseEntity<Void> deleteDescontoPlanoContabil(@PathVariable Long id) {
        log.debug("REST request to delete DescontoPlanoContabil : {}", id);

        descontoPlanoContabilRepository.deleteById(id);
        descontoPlanoContabilSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/desconto-plano-contabils?query=:query : search for the descontoPlanoContabil corresponding
     * to the query.
     *
     * @param query the query of the descontoPlanoContabil search
     * @return the result of the search
     */
    @GetMapping("/_search/desconto-plano-contabils")
    @Timed
    public List<DescontoPlanoContabil> searchDescontoPlanoContabils(@RequestParam String query) {
        log.debug("REST request to search DescontoPlanoContabils for query {}", query);
        return StreamSupport
            .stream(descontoPlanoContabilSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
