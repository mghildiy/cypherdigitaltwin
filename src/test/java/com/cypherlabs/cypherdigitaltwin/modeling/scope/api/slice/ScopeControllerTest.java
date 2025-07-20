package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.slice;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ScopeControllerTest {

    @MockitoBean
    private ScopeService service;


    @Autowired
    MockMvc mvc;

    @Test
    void shouldReturnScope_whenScopeExists() throws Exception {
        Scope mockScope = new Scope("123", "Scope A",
                new Location(18.0, 20.0)
                , Set.of("fake", "dummy"), null);
        when(service.get("123")).thenReturn(Optional.of(mockScope));

        mvc.perform(get("/scope/123")
                        .with(httpBasic("admin", "secret")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Scope A"))
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.tags", containsInAnyOrder("fake", "dummy")));
    }
}
