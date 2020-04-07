package com.isoft.trc.web.rest;

import com.isoft.trc.domain.SymptomsSubDetails;
import com.isoft.trc.service.SymptomsSubDetailsService;
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
 * REST controller for managing {@link com.isoft.trc.domain.SymptomsSubDetails}.
 */
@RestController
@RequestMapping("/api")
public class SymptomsSubDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SymptomsSubDetailsResource.class);

    private static final String ENTITY_NAME = "symptomsSubDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SymptomsSubDetailsService symptomsSubDetailsService;

    public SymptomsSubDetailsResource(SymptomsSubDetailsService symptomsSubDetailsService) {
        this.symptomsSubDetailsService = symptomsSubDetailsService;
    }

    /**
     * {@code POST  /symptoms-sub-details} : Create a new symptomsSubDetails.
     *
     * @param symptomsSubDetails the symptomsSubDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new symptomsSubDetails, or with status {@code 400 (Bad Request)} if the symptomsSubDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/symptoms-sub-details")
    public ResponseEntity<SymptomsSubDetails> createSymptomsSubDetails(@Valid @RequestBody SymptomsSubDetails symptomsSubDetails) throws URISyntaxException {
        log.debug("REST request to save SymptomsSubDetails : {}", symptomsSubDetails);
        if (symptomsSubDetails.getId() != null) {
            throw new BadRequestAlertException("A new symptomsSubDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SymptomsSubDetails result = symptomsSubDetailsService.save(symptomsSubDetails);
        return ResponseEntity.created(new URI("/api/symptoms-sub-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /symptoms-sub-details} : Updates an existing symptomsSubDetails.
     *
     * @param symptomsSubDetails the symptomsSubDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated symptomsSubDetails,
     * or with status {@code 400 (Bad Request)} if the symptomsSubDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the symptomsSubDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/symptoms-sub-details")
    public ResponseEntity<SymptomsSubDetails> updateSymptomsSubDetails(@Valid @RequestBody SymptomsSubDetails symptomsSubDetails) throws URISyntaxException {
        log.debug("REST request to update SymptomsSubDetails : {}", symptomsSubDetails);
        if (symptomsSubDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SymptomsSubDetails result = symptomsSubDetailsService.save(symptomsSubDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, symptomsSubDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /symptoms-sub-details} : get all the symptomsSubDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of symptomsSubDetails in body.
     */
    @GetMapping("/symptoms-sub-details")
    public ResponseEntity<List<SymptomsSubDetails>> getAllSymptomsSubDetails(Pageable pageable) {
        log.debug("REST request to get a page of SymptomsSubDetails");
        Page<SymptomsSubDetails> page = symptomsSubDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /symptoms-sub-details/:id} : get the "id" symptomsSubDetails.
     *
     * @param id the id of the symptomsSubDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the symptomsSubDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/symptoms-sub-details/{id}")
    public ResponseEntity<SymptomsSubDetails> getSymptomsSubDetails(@PathVariable Long id) {
        log.debug("REST request to get SymptomsSubDetails : {}", id);
        Optional<SymptomsSubDetails> symptomsSubDetails = symptomsSubDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(symptomsSubDetails);
    }

    /**
     * {@code DELETE  /symptoms-sub-details/:id} : delete the "id" symptomsSubDetails.
     *
     * @param id the id of the symptomsSubDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/symptoms-sub-details/{id}")
    public ResponseEntity<Void> deleteSymptomsSubDetails(@PathVariable Long id) {
        log.debug("REST request to delete SymptomsSubDetails : {}", id);
        symptomsSubDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
