package com.arquitetira.trabalho.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arquitetira.trabalho.app.domain.PlanoEmpresa;
import com.arquitetira.trabalho.app.repository.PlanoEmpresaRepository;
import com.arquitetira.trabalho.app.repository.search.PlanoEmpresaSearchRepository;
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
 * REST controller for managing PlanoEmpresa.
 */
@RestController
@RequestMapping("/api")
public class PlanoEmpresaResource {

    private final Logger log = LoggerFactory.getLogger(PlanoEmpresaResource.class);

    private static final String ENTITY_NAME = "planoEmpresa";

    private PlanoEmpresaRepository planoEmpresaRepository;

    private PlanoEmpresaSearchRepository planoEmpresaSearchRepository;

    public PlanoEmpresaResource(PlanoEmpresaRepository planoEmpresaRepository, PlanoEmpresaSearchRepository planoEmpresaSearchRepository) {
        this.planoEmpresaRepository = planoEmpresaRepository;
        this.planoEmpresaSearchRepository = planoEmpresaSearchRepository;
    }

    /**
     * POST  /plano-empresas : Create a new planoEmpresa.
     *
     * @param planoEmpresa the planoEmpresa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planoEmpresa, or with status 400 (Bad Request) if the planoEmpresa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plano-empresas")
    @Timed
    public ResponseEntity<PlanoEmpresa> createPlanoEmpresa(@RequestBody PlanoEmpresa planoEmpresa) throws URISyntaxException {
        log.debug("REST request to save PlanoEmpresa : {}", planoEmpresa);
        if (planoEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new planoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoEmpresa result = planoEmpresaRepository.save(planoEmpresa);
        planoEmpresaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/plano-empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plano-empresas : Updates an existing planoEmpresa.
     *
     * @param planoEmpresa the planoEmpresa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planoEmpresa,
     * or with status 400 (Bad Request) if the planoEmpresa is not valid,
     * or with status 500 (Internal Server Error) if the planoEmpresa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plano-empresas")
    @Timed
    public ResponseEntity<PlanoEmpresa> updatePlanoEmpresa(@RequestBody PlanoEmpresa planoEmpresa) throws URISyntaxException {
        log.debug("REST request to update PlanoEmpresa : {}", planoEmpresa);
        if (planoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoEmpresa result = planoEmpresaRepository.save(planoEmpresa);
        planoEmpresaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planoEmpresa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plano-empresas : get all the planoEmpresas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planoEmpresas in body
     */
    @GetMapping("/plano-empresas")
    @Timed
    public List<PlanoEmpresa> getAllPlanoEmpresas() {
        log.debug("REST request to get all PlanoEmpresas");
        return planoEmpresaRepository.findAll();
    }

    /**
     * GET  /plano-empresas/:id : get the "id" planoEmpresa.
     *
     * @param id the id of the planoEmpresa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planoEmpresa, or with status 404 (Not Found)
     */
    @GetMapping("/plano-empresas/{id}")
    @Timed
    public ResponseEntity<PlanoEmpresa> getPlanoEmpresa(@PathVariable Long id) {
        log.debug("REST request to get PlanoEmpresa : {}", id);
        Optional<PlanoEmpresa> planoEmpresa = planoEmpresaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(planoEmpresa);
    }

    /**
     * DELETE  /plano-empresas/:id : delete the "id" planoEmpresa.
     *
     * @param id the id of the planoEmpresa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plano-empresas/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanoEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete PlanoEmpresa : {}", id);

        planoEmpresaRepository.deleteById(id);
        planoEmpresaSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/plano-empresas?query=:query : search for the planoEmpresa corresponding
     * to the query.
     *
     * @param query the query of the planoEmpresa search
     * @return the result of the search
     */
    @GetMapping("/_search/plano-empresas")
    @Timed
    public List<PlanoEmpresa> searchPlanoEmpresas(@RequestParam String query) {
        log.debug("REST request to search PlanoEmpresas for query {}", query);
        return StreamSupport
            .stream(planoEmpresaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
