package com.isoft.trc.web.rest;

import com.isoft.trc.domain.PersonInteracitonTracking;
import com.isoft.trc.service.PersonInteracitonTrackingService;
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
 * REST controller for managing {@link com.isoft.trc.domain.PersonInteracitonTracking}.
 */
@RestController
@RequestMapping("/api")
public class PersonInteracitonTrackingResource {

    private final Logger log = LoggerFactory.getLogger(PersonInteracitonTrackingResource.class);

    private static final String ENTITY_NAME = "personInteracitonTracking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonInteracitonTrackingService personInteracitonTrackingService;

    public PersonInteracitonTrackingResource(PersonInteracitonTrackingService personInteracitonTrackingService) {
        this.personInteracitonTrackingService = personInteracitonTrackingService;
    }

    /**
     * {@code POST  /person-interaciton-trackings} : Create a new personInteracitonTracking.
     *
     * @param personInteracitonTracking the personInteracitonTracking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personInteracitonTracking, or with status {@code 400 (Bad Request)} if the personInteracitonTracking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-interaciton-trackings")
    public ResponseEntity<PersonInteracitonTracking> createPersonInteracitonTracking(@Valid @RequestBody PersonInteracitonTracking personInteracitonTracking) throws URISyntaxException {
        log.debug("REST request to save PersonInteracitonTracking : {}", personInteracitonTracking);
        if (personInteracitonTracking.getId() != null) {
            throw new BadRequestAlertException("A new personInteracitonTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonInteracitonTracking result = personInteracitonTrackingService.save(personInteracitonTracking);
        return ResponseEntity.created(new URI("/api/person-interaciton-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-interaciton-trackings} : Updates an existing personInteracitonTracking.
     *
     * @param personInteracitonTracking the personInteracitonTracking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personInteracitonTracking,
     * or with status {@code 400 (Bad Request)} if the personInteracitonTracking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personInteracitonTracking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-interaciton-trackings")
    public ResponseEntity<PersonInteracitonTracking> updatePersonInteracitonTracking(@Valid @RequestBody PersonInteracitonTracking personInteracitonTracking) throws URISyntaxException {
        log.debug("REST request to update PersonInteracitonTracking : {}", personInteracitonTracking);
        if (personInteracitonTracking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonInteracitonTracking result = personInteracitonTrackingService.save(personInteracitonTracking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personInteracitonTracking.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-interaciton-trackings} : get all the personInteracitonTrackings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personInteracitonTrackings in body.
     */
    @GetMapping("/person-interaciton-trackings")
    public ResponseEntity<List<PersonInteracitonTracking>> getAllPersonInteracitonTrackings(Pageable pageable) {
        log.debug("REST request to get a page of PersonInteracitonTrackings");
        Page<PersonInteracitonTracking> page = personInteracitonTrackingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /person-interaciton-trackings/:id} : get the "id" personInteracitonTracking.
     *
     * @param id the id of the personInteracitonTracking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personInteracitonTracking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-interaciton-trackings/{id}")
    public ResponseEntity<PersonInteracitonTracking> getPersonInteracitonTracking(@PathVariable Long id) {
        log.debug("REST request to get PersonInteracitonTracking : {}", id);
        Optional<PersonInteracitonTracking> personInteracitonTracking = personInteracitonTrackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personInteracitonTracking);
    }

    /**
     * {@code DELETE  /person-interaciton-trackings/:id} : delete the "id" personInteracitonTracking.
     *
     * @param id the id of the personInteracitonTracking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-interaciton-trackings/{id}")
    public ResponseEntity<Void> deletePersonInteracitonTracking(@PathVariable Long id) {
        log.debug("REST request to delete PersonInteracitonTracking : {}", id);
        personInteracitonTrackingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
