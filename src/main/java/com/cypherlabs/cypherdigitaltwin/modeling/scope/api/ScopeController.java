package com.cypherlabs.cypherdigitaltwin.modeling.scope.api;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.*;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scope")
public class ScopeController {

    private ScopeService service;

    @Autowired
    public ScopeController(ScopeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreateScopeResponse> create(@Valid @RequestBody CreateScopeRequest request) {
        Scope created = this.service.create(request.name(),
                request.location(), request.tags(), request.parentId());

        return ResponseEntity.status(HttpStatus.CREATED).body(toPostResponse(created));
    }

    private CreateScopeResponse toPostResponse(Scope scope) {
        return new CreateScopeResponse(scope.getId(), scope.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScopeResponse> get(@PathVariable String id) {
        return service.get(id)
                .<ResponseEntity<ScopeResponse>>map(found ->
                        ResponseEntity.ok(toGetResponse(found)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ScopeNotFoundResponse("Scope not found", id)));
    }

    private GetScopeResponse toGetResponse(Scope scope) {
        return new GetScopeResponse(scope.getId(), scope.getName(), scope.getStatus(), scope.getLocation(),
                scope.getTags(), scope.getParent());
    }

    @GetMapping
    public ResponseEntity<List<GetScopeResponse>> getAll() {
        List<GetScopeResponse> responses = service.getAll()
                .stream()
                .map(this::toGetResponse)
                .toList();
        return ResponseEntity.ok(responses); // returns [] if empty
    }
}
