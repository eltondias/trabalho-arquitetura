package com.arquitetira.trabalho.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arquitetira.trabalho.app.domain.DescontoGrupo;
import com.arquitetira.trabalho.app.repository.DescontoGrupoRepository;
import com.arquitetira.trabalho.app.repository.search.DescontoGrupoSearchRepository;
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
 * REST controller for managing DescontoGrupo.
 */
@RestController
@RequestMapping("/api")
public class DescontoGrupoResource {

    private final Logger log = LoggerFactory.getLogger(DescontoGrupoResource.class);

    private static final String ENTITY_NAME = "descontoGrupo";

    private DescontoGrupoRepository descontoGrupoRepository;

    private DescontoGrupoSearchRepository descontoGrupoSearchRepository;

    public DescontoGrupoResource(DescontoGrupoRepository descontoGrupoRepository, DescontoGrupoSearchRepository descontoGrupoSearchRepository) {
        this.descontoGrupoRepository = descontoGrupoRepository;
        this.descontoGrupoSearchRepository = descontoGrupoSearchRepository;
    }

    /**
     * POST  /desconto-grupos : Create a new descontoGrupo.
     *
     * @param descontoGrupo the descontoGrupo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new descontoGrupo, or with status 400 (Bad Request) if the descontoGrupo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/desconto-grupos")
    @Timed
    public ResponseEntity<DescontoGrupo> createDescontoGrupo(@RequestBody DescontoGrupo descontoGrupo) throws URISyntaxException {
        log.debug("REST request to save DescontoGrupo : {}", descontoGrupo);
        if (descontoGrupo.getId() != null) {
            throw new BadRequestAlertException("A new descontoGrupo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DescontoGrupo result = descontoGrupoRepository.save(descontoGrupo);
        descontoGrupoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/desconto-grupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /desconto-grupos : Updates an existing descontoGrupo.
     *
     * @param descontoGrupo the descontoGrupo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated descontoGrupo,
     * or with status 400 (Bad Request) if the descontoGrupo is not valid,
     * or with status 500 (Internal Server Error) if the descontoGrupo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/desconto-grupos")
    @Timed
    public ResponseEntity<DescontoGrupo> updateDescontoGrupo(@RequestBody DescontoGrupo descontoGrupo) throws URISyntaxException {
        log.debug("REST request to update DescontoGrupo : {}", descontoGrupo);
        if (descontoGrupo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DescontoGrupo result = descontoGrupoRepository.save(descontoGrupo);
        descontoGrupoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, descontoGrupo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /desconto-grupos : get all the descontoGrupos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of descontoGrupos in body
     */
    @GetMapping("/desconto-grupos")
    @Timed
    public List<DescontoGrupo> getAllDescontoGrupos() {
        log.debug("REST request to get all DescontoGrupos");
        return descontoGrupoRepository.findAll();
    }

    /**
     * GET  /desconto-grupos/:id : get the "id" descontoGrupo.
     *
     * @param id the id of the descontoGrupo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the descontoGrupo, or with status 404 (Not Found)
     */
    @GetMapping("/desconto-grupos/{id}")
    @Timed
    public ResponseEntity<DescontoGrupo> getDescontoGrupo(@PathVariable Long id) {
        log.debug("REST request to get DescontoGrupo : {}", id);
        Optional<DescontoGrupo> descontoGrupo = descontoGrupoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(descontoGrupo);
    }

    /**
     * DELETE  /desconto-grupos/:id : delete the "id" descontoGrupo.
     *
     * @param id the id of the descontoGrupo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/desconto-grupos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDescontoGrupo(@PathVariable Long id) {
        log.debug("REST request to delete DescontoGrupo : {}", id);

        descontoGrupoRepository.deleteById(id);
        descontoGrupoSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/desconto-grupos?query=:query : search for the descontoGrupo corresponding
     * to the query.
     *
     * @param query the query of the descontoGrupo search
     * @return the result of the search
     */
    @GetMapping("/_search/desconto-grupos")
    @Timed
    public List<DescontoGrupo> searchDescontoGrupos(@RequestParam String query) {
        log.debug("REST request to search DescontoGrupos for query {}", query);
        return StreamSupport
            .stream(descontoGrupoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
