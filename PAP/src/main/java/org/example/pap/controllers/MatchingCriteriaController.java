package org.example.pap.controllers;

import org.example.pap.models.MatchingCriteria;
import org.example.pap.services.MatchingCriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/matching-criteria")
public class MatchingCriteriaController {

    @Autowired
    private MatchingCriteriaService matchingCriteriaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MatchingCriteria> getAllMatchingCriteria() {
        return Flux.fromIterable(matchingCriteriaService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MatchingCriteria> getMatchingCriteriaById(@PathVariable Long id) {
        return Mono.justOrEmpty(matchingCriteriaService.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MatchingCriteria> createMatchingCriteria(@RequestBody MatchingCriteria matchingCriteria) {
        return Mono.just(matchingCriteriaService.save(matchingCriteria));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MatchingCriteria> updateMatchingCriteria(@PathVariable Long id, @RequestBody MatchingCriteria matchingCriteria) {
        matchingCriteria.setId(id);
        return Mono.just(matchingCriteriaService.save(matchingCriteria));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMatchingCriteria(@PathVariable Long id) {
        matchingCriteriaService.deleteById(id);
        return Mono.empty();
    }
}
