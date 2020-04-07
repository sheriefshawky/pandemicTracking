package com.isoft.trc.web.rest;

import com.isoft.trc.domain.SymptomsSpecs;
import com.isoft.trc.service.SymptomsSpecsService;
import com.isoft.trc.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.isoft.trc.domain.SymptomsSpecs}.
 */
@RestController
@RequestMapping("/api")
public class SymptomsSpecsResource {

    private final Logger log = LoggerFactory.getLogger(SymptomsSpecsResource.class);

    private static final String ENTITY_NAME = "symptomsSpecs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SymptomsSpecsService symptomsSpecsService;

    public SymptomsSpecsResource(SymptomsSpecsService symptomsSpecsService) {
        this.symptomsSpecsService = symptomsSpecsService;
    }

    /**
     * {@code POST  /symptoms-specs} : Create a new symptomsSpecs.
     *
     * @param symptomsSpecs the symptomsSpecs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new symptomsSpecs, or with status {@code 400 (Bad Request)} if the symptomsSpecs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/symptoms-specs")
    public ResponseEntity<SymptomsSpecs> createSymptomsSpecs(@Valid @RequestBody SymptomsSpecs symptomsSpecs) throws URISyntaxException {
        log.debug("REST request to save SymptomsSpecs : {}", symptomsSpecs);
        if (symptomsSpecs.getId() != null) {
            throw new BadRequestAlertException("A new symptomsSpecs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SymptomsSpecs result = symptomsSpecsService.save(symptomsSpecs);
        return ResponseEntity.created(new URI("/api/symptoms-specs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /symptoms-specs} : Updates an existing symptomsSpecs.
     *
     * @param symptomsSpecs the symptomsSpecs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated symptomsSpecs,
     * or with status {@code 400 (Bad Request)} if the symptomsSpecs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the symptomsSpecs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/symptoms-specs")
    public ResponseEntity<SymptomsSpecs> updateSymptomsSpecs(@Valid @RequestBody SymptomsSpecs symptomsSpecs) throws URISyntaxException {
        log.debug("REST request to update SymptomsSpecs : {}", symptomsSpecs);
        if (symptomsSpecs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SymptomsSpecs result = symptomsSpecsService.save(symptomsSpecs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, symptomsSpecs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /symptoms-specs} : get all the symptomsSpecs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of symptomsSpecs in body.
     */
    @GetMapping("/symptoms-specs")
    public ResponseEntity<List<SymptomsSpecs>> getAllSymptomsSpecs(Pageable pageable) {
        log.debug("REST request to get a page of SymptomsSpecs");
        Page<SymptomsSpecs> page = symptomsSpecsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /symptoms-specs/:id} : get the "id" symptomsSpecs.
     *
     * @param id the id of the symptomsSpecs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the symptomsSpecs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/symptoms-specs/{id}")
    public ResponseEntity<SymptomsSpecs> getSymptomsSpecs(@PathVariable Long id) {
        log.debug("REST request to get SymptomsSpecs : {}", id);
        Optional<SymptomsSpecs> symptomsSpecs = symptomsSpecsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(symptomsSpecs);
    }

    /**
     * {@code DELETE  /symptoms-specs/:id} : delete the "id" symptomsSpecs.
     *
     * @param id the id of the symptomsSpecs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/symptoms-specs/{id}")
    public ResponseEntity<Void> deleteSymptomsSpecs(@PathVariable Long id) {
        log.debug("REST request to delete SymptomsSpecs : {}", id);
        symptomsSpecsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
