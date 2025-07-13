package com.cypherlabs.cypherdigitaltwin.modeling.scope.api;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.CreateScopeRequest;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.CreateScopeResponse;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.GetScopeResponse;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;
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
    public ResponseEntity<CreateScopeResponse> create(@RequestBody CreateScopeRequest request) {
        Scope created = this.service.create(request.name(),
                request.location(), request.tags(), request.parentId());

        return ResponseEntity.status(HttpStatus.CREATED).body(toPostResponse(created));
    }

    private CreateScopeResponse toPostResponse(Scope scope) {
        return new CreateScopeResponse(scope.getId(), scope.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetScopeResponse> get(@PathVariable String id) {
        return service.get(id)
                .map(found -> ResponseEntity.ok(toGetResponse(found)))
                .orElse(ResponseEntity.notFound().build());
    }

    private GetScopeResponse toGetResponse(Scope scope) {
        return new GetScopeResponse(scope.getId(), scope.getName(), scope.getStatus(),
                scope.getTags(), scope.getParent());
    }

    @GetMapping
    public ResponseEntity<List<GetScopeResponse>> getAll() {
        List<Scope> existingScopes = this.service.getAll();
        if (existingScopes.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(existingScopes.stream().map(this::toGetResponse).toList());
        }
    }
}
