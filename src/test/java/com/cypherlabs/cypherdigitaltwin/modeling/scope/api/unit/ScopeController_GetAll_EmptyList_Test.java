package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.unit;

import com.cypherlabs.cypherdigitaltwin.common.BaseTestTemplate;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.ScopeController;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.GetScopeResponse;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScopeController_GetAll_EmptyList_Test extends BaseTestTemplate {

    private ScopeService mockService;

    @Override
    protected void prepareData() {
        mockService = mock(ScopeService.class);
        when(mockService.getAll()).thenReturn(List.of());
    }

    @Override
    protected Object executeTest() {
        ScopeController controller = new ScopeController(mockService);

        return controller.getAll();
    }

    @Override
    protected void validateResult(Object result) {
        ResponseEntity<List<GetScopeResponse>> response = (ResponseEntity<List<GetScopeResponse>>) result;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() == 0);
    }
}
