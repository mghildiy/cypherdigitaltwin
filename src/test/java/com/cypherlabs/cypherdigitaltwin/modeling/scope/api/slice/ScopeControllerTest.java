package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.slice;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.CreateScopeRequest;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.exception.ScopeNotFoundException;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.exception.ScopeWithSameNameExistsException;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.ScopeStatus;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ScopeControllerTest {

    @MockitoBean
    private ScopeService service;

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnScope_whenScopeExists() throws Exception {
        Scope mockScope = new Scope("123", "Scope A",
                new Location(18.0, 20.0)
                , Set.of("fake", "dummy"), null);
        when(service.get("123")).thenReturn(Optional.of(mockScope));

        mvc.perform(get("/scope/123")
                        .with(httpBasic("admin", "secret")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Scope A"))
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.tags", containsInAnyOrder("fake", "dummy")));
    }

    @Test
    void shouldReturnNoScope_whenScopeDoesNotExist() throws Exception {
        when(service.get("123")).thenReturn(Optional.empty());

        mvc.perform(get("/scope/123")
                        .with(httpBasic("admin", "secret")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.message").value("Scope not found"));
    }

    @Test
    void shouldReturnAllScopes_whenScopesExist() throws Exception {
        Scope mockScope = new Scope("123", "Scope A",
                new Location(18.0, 20.0)
                , Set.of("fake", "dummy"), null);
        Scope mockScope1 = new Scope("124", "Scope B",
                new Location(19.0, 21.0)
                , Set.of("test", "dummy"), mockScope);
        when(service.getAll()).thenReturn(List.of(mockScope, mockScope1));

        mvc.perform(get("/scope")
                        .with(httpBasic("admin", "secret")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].tags", containsInAnyOrder("dummy", "fake")))
                .andExpect(jsonPath("$[1].id").value("124"))
                .andExpect(jsonPath("$[1].parent.id").value("123"))
                .andExpect(jsonPath("$[1].tags", containsInAnyOrder("test", "dummy")));
    }

    @Test
    public void shouldCreateScope_withoutParentScope() throws Exception {
        CreateScopeRequest request = new CreateScopeRequest("Scope A", new Location(18.0, 20.0),
                Set.of("dummy", "fake"), null);
        Scope created = new Scope("123", "Scope A", new Location(18.0, 20.0),
                Set.of("dummy", "fake"), null
        );

        when(service.create(
                eq("Scope A"),
                eq(new Location(18.0, 20.0)),
                eq(Set.of("dummy", "fake")),
                isNull()
        )).thenReturn(created);

        mvc.perform(post("/scope")
                        .with(httpBasic("admin", "secret"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.status").value(ScopeStatus.ACTIVE.name()));

    }

    @Test
    public void shouldCreateScope_withParentScope() throws Exception {
        String parentId = "122";
        CreateScopeRequest request = new CreateScopeRequest("Scope A", new Location(18.0, 20.0),
                Set.of("dummy", "fake"), parentId);
        Scope parent = new Scope(parentId, "Scope P", new Location(18.0, 20.0),
                Set.of("dummy", "test"), null);
        Scope created = new Scope("123", "Scope A", new Location(18.0, 20.0),
                Set.of("dummy", "fake"), parent
        );

        when(service.create(
                eq("Scope A"),
                eq(new Location(18.0, 20.0)),
                eq(Set.of("dummy", "fake")),
                eq(parentId)
        )).thenReturn(created);

        mvc.perform(post("/scope")
                        .with(httpBasic("admin", "secret"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.status").value(ScopeStatus.ACTIVE.name()));

    }

    @Test
    public void shouldReturnErrorResponse_whenParentScopeNotExists() throws Exception {
        String parentId = "122";
        CreateScopeRequest request = new CreateScopeRequest("Scope A", new Location(18.0, 20.0),
                Set.of("dummy", "fake"), parentId);

        when(service.create(
                eq("Scope A"),
                eq(new Location(18.0, 20.0)),
                eq(Set.of("dummy", "fake")),
                eq(parentId)
        )).thenThrow(new ScopeNotFoundException(String.format("Parent scope '%s' not found", parentId)));

        mvc.perform(post("/scope")
                        .with(httpBasic("admin", "secret"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").value(String.format("Parent scope '%s' not found", parentId)))
                .andExpect(jsonPath("$.errorCode").value("SCOPE_NOT_FOUND"));

    }

    @Test
    public void shouldReturnErrorResponse_whenScopeWithSameNameExists() throws Exception {
        String name = "Scope A";
        CreateScopeRequest request = new CreateScopeRequest(name, new Location(18.0, 20.0),
                Set.of("dummy", "fake"), null);

        when(service.create(
                eq(name),
                eq(new Location(18.0, 20.0)),
                eq(Set.of("dummy", "fake")),
                eq(null)
        )).thenThrow(new ScopeWithSameNameExistsException(String.format("Scope with name '%s' already exists", "Scope A")));

        mvc.perform(post("/scope")
                        .with(httpBasic("admin", "secret"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.messages").value(String.format("Scope with name '%s' already exists", name)))
                .andExpect(jsonPath("$.errorCode").value("SCOPE_WITH_SAME_NAME_EXISTS"));

    }
}
