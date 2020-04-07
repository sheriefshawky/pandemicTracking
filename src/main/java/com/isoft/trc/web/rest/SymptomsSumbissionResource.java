package com.isoft.trc.web.rest;

import com.isoft.trc.domain.SymptomsSumbission;
import com.isoft.trc.service.SymptomsSumbissionService;
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
 * REST controller for managing {@link com.isoft.trc.domain.SymptomsSumbission}.
 */
@RestController
@RequestMapping("/api")
public class SymptomsSumbissionResource {

    private final Logger log = LoggerFactory.getLogger(SymptomsSumbissionResource.class);

    private static final String ENTITY_NAME = "symptomsSumbission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SymptomsSumbissionService symptomsSumbissionService;

    public SymptomsSumbissionResource(SymptomsSumbissionService symptomsSumbissionService) {
        this.symptomsSumbissionService = symptomsSumbissionService;
    }

    /**
     * {@code POST  /symptoms-sumbissions} : Create a new symptomsSumbission.
     *
     * @param symptomsSumbission the symptomsSumbission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new symptomsSumbission, or with status {@code 400 (Bad Request)} if the symptomsSumbission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/symptoms-sumbissions")
    public ResponseEntity<SymptomsSumbission> createSymptomsSumbission(@Valid @RequestBody SymptomsSumbission symptomsSumbission) throws URISyntaxException {
        log.debug("REST request to save SymptomsSumbission : {}", symptomsSumbission);
        if (symptomsSumbission.getId() != null) {
            throw new BadRequestAlertException("A new symptomsSumbission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SymptomsSumbission result = symptomsSumbissionService.save(symptomsSumbission);
        return ResponseEntity.created(new URI("/api/symptoms-sumbissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /symptoms-sumbissions} : Updates an existing symptomsSumbission.
     *
     * @param symptomsSumbission the symptomsSumbission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated symptomsSumbission,
     * or with status {@code 400 (Bad Request)} if the symptomsSumbission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the symptomsSumbission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/symptoms-sumbissions")
    public ResponseEntity<SymptomsSumbission> updateSymptomsSumbission(@Valid @RequestBody SymptomsSumbission symptomsSumbission) throws URISyntaxException {
        log.debug("REST request to update SymptomsSumbission : {}", symptomsSumbission);
        if (symptomsSumbission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SymptomsSumbission result = symptomsSumbissionService.save(symptomsSumbission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, symptomsSumbission.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /symptoms-sumbissions} : get all the symptomsSumbissions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of symptomsSumbissions in body.
     */
    @GetMapping("/symptoms-sumbissions")
    public ResponseEntity<List<SymptomsSumbission>> getAllSymptomsSumbissions(Pageable pageable) {
        log.debug("REST request to get a page of SymptomsSumbissions");
        Page<SymptomsSumbission> page = symptomsSumbissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /symptoms-sumbissions/:id} : get the "id" symptomsSumbission.
     *
     * @param id the id of the symptomsSumbission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the symptomsSumbission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/symptoms-sumbissions/{id}")
    public ResponseEntity<SymptomsSumbission> getSymptomsSumbission(@PathVariable Long id) {
        log.debug("REST request to get SymptomsSumbission : {}", id);
        Optional<SymptomsSumbission> symptomsSumbission = symptomsSumbissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(symptomsSumbission);
    }

    /**
     * {@code DELETE  /symptoms-sumbissions/:id} : delete the "id" symptomsSumbission.
     *
     * @param id the id of the symptomsSumbission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/symptoms-sumbissions/{id}")
    public ResponseEntity<Void> deleteSymptomsSumbission(@PathVariable Long id) {
        log.debug("REST request to delete SymptomsSumbission : {}", id);
        symptomsSumbissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
