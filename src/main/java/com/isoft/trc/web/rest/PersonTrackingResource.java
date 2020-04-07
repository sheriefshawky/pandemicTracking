package com.isoft.trc.web.rest;

import com.isoft.trc.domain.PersonTracking;
import com.isoft.trc.service.PersonTrackingService;
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
 * REST controller for managing {@link com.isoft.trc.domain.PersonTracking}.
 */
@RestController
@RequestMapping("/api")
public class PersonTrackingResource {

    private final Logger log = LoggerFactory.getLogger(PersonTrackingResource.class);

    private static final String ENTITY_NAME = "personTracking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonTrackingService personTrackingService;

    public PersonTrackingResource(PersonTrackingService personTrackingService) {
        this.personTrackingService = personTrackingService;
    }

    /**
     * {@code POST  /person-trackings} : Create a new personTracking.
     *
     * @param personTracking the personTracking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personTracking, or with status {@code 400 (Bad Request)} if the personTracking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-trackings")
    public ResponseEntity<PersonTracking> createPersonTracking(@Valid @RequestBody PersonTracking personTracking) throws URISyntaxException {
        log.debug("REST request to save PersonTracking : {}", personTracking);
        if (personTracking.getId() != null) {
            throw new BadRequestAlertException("A new personTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonTracking result = personTrackingService.save(personTracking);
        return ResponseEntity.created(new URI("/api/person-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-trackings} : Updates an existing personTracking.
     *
     * @param personTracking the personTracking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personTracking,
     * or with status {@code 400 (Bad Request)} if the personTracking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personTracking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-trackings")
    public ResponseEntity<PersonTracking> updatePersonTracking(@Valid @RequestBody PersonTracking personTracking) throws URISyntaxException {
        log.debug("REST request to update PersonTracking : {}", personTracking);
        if (personTracking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonTracking result = personTrackingService.save(personTracking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personTracking.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-trackings} : get all the personTrackings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personTrackings in body.
     */
    @GetMapping("/person-trackings")
    public ResponseEntity<List<PersonTracking>> getAllPersonTrackings(Pageable pageable) {
        log.debug("REST request to get a page of PersonTrackings");
        Page<PersonTracking> page = personTrackingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /person-trackings/:id} : get the "id" personTracking.
     *
     * @param id the id of the personTracking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personTracking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-trackings/{id}")
    public ResponseEntity<PersonTracking> getPersonTracking(@PathVariable Long id) {
        log.debug("REST request to get PersonTracking : {}", id);
        Optional<PersonTracking> personTracking = personTrackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personTracking);
    }

    /**
     * {@code DELETE  /person-trackings/:id} : delete the "id" personTracking.
     *
     * @param id the id of the personTracking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-trackings/{id}")
    public ResponseEntity<Void> deletePersonTracking(@PathVariable Long id) {
        log.debug("REST request to delete PersonTracking : {}", id);
        personTrackingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
