package com.arquitetira.trabalho.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arquitetira.trabalho.app.domain.PeriodoPagamento;
import com.arquitetira.trabalho.app.repository.PeriodoPagamentoRepository;
import com.arquitetira.trabalho.app.repository.search.PeriodoPagamentoSearchRepository;
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
 * REST controller for managing PeriodoPagamento.
 */
@RestController
@RequestMapping("/api")
public class PeriodoPagamentoResource {

    private final Logger log = LoggerFactory.getLogger(PeriodoPagamentoResource.class);

    private static final String ENTITY_NAME = "periodoPagamento";

    private PeriodoPagamentoRepository periodoPagamentoRepository;

    private PeriodoPagamentoSearchRepository periodoPagamentoSearchRepository;

    public PeriodoPagamentoResource(PeriodoPagamentoRepository periodoPagamentoRepository, PeriodoPagamentoSearchRepository periodoPagamentoSearchRepository) {
        this.periodoPagamentoRepository = periodoPagamentoRepository;
        this.periodoPagamentoSearchRepository = periodoPagamentoSearchRepository;
    }

    /**
     * POST  /periodo-pagamentos : Create a new periodoPagamento.
     *
     * @param periodoPagamento the periodoPagamento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodoPagamento, or with status 400 (Bad Request) if the periodoPagamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periodo-pagamentos")
    @Timed
    public ResponseEntity<PeriodoPagamento> createPeriodoPagamento(@RequestBody PeriodoPagamento periodoPagamento) throws URISyntaxException {
        log.debug("REST request to save PeriodoPagamento : {}", periodoPagamento);
        if (periodoPagamento.getId() != null) {
            throw new BadRequestAlertException("A new periodoPagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodoPagamento result = periodoPagamentoRepository.save(periodoPagamento);
        periodoPagamentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/periodo-pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periodo-pagamentos : Updates an existing periodoPagamento.
     *
     * @param periodoPagamento the periodoPagamento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodoPagamento,
     * or with status 400 (Bad Request) if the periodoPagamento is not valid,
     * or with status 500 (Internal Server Error) if the periodoPagamento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periodo-pagamentos")
    @Timed
    public ResponseEntity<PeriodoPagamento> updatePeriodoPagamento(@RequestBody PeriodoPagamento periodoPagamento) throws URISyntaxException {
        log.debug("REST request to update PeriodoPagamento : {}", periodoPagamento);
        if (periodoPagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodoPagamento result = periodoPagamentoRepository.save(periodoPagamento);
        periodoPagamentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodoPagamento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periodo-pagamentos : get all the periodoPagamentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of periodoPagamentos in body
     */
    @GetMapping("/periodo-pagamentos")
    @Timed
    public List<PeriodoPagamento> getAllPeriodoPagamentos() {
        log.debug("REST request to get all PeriodoPagamentos");
        return periodoPagamentoRepository.findAll();
    }

    /**
     * GET  /periodo-pagamentos/:id : get the "id" periodoPagamento.
     *
     * @param id the id of the periodoPagamento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodoPagamento, or with status 404 (Not Found)
     */
    @GetMapping("/periodo-pagamentos/{id}")
    @Timed
    public ResponseEntity<PeriodoPagamento> getPeriodoPagamento(@PathVariable Long id) {
        log.debug("REST request to get PeriodoPagamento : {}", id);
        Optional<PeriodoPagamento> periodoPagamento = periodoPagamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(periodoPagamento);
    }

    /**
     * DELETE  /periodo-pagamentos/:id : delete the "id" periodoPagamento.
     *
     * @param id the id of the periodoPagamento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periodo-pagamentos/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriodoPagamento(@PathVariable Long id) {
        log.debug("REST request to delete PeriodoPagamento : {}", id);

        periodoPagamentoRepository.deleteById(id);
        periodoPagamentoSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/periodo-pagamentos?query=:query : search for the periodoPagamento corresponding
     * to the query.
     *
     * @param query the query of the periodoPagamento search
     * @return the result of the search
     */
    @GetMapping("/_search/periodo-pagamentos")
    @Timed
    public List<PeriodoPagamento> searchPeriodoPagamentos(@RequestParam String query) {
        log.debug("REST request to search PeriodoPagamentos for query {}", query);
        return StreamSupport
            .stream(periodoPagamentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
