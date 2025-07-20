package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.unit;

import com.cypherlabs.cypherdigitaltwin.common.BaseUnitTestTemplate;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.ScopeController;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.ScopeNotFoundResponse;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.ScopeResponse;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScopeController_Get_NotFoundTest extends BaseUnitTestTemplate {

    private String id = "01K01YBNR1Z1JA2626PABG";
    private ScopeService mockService;

    @Override
    protected void prepareData() {
        mockService = mock(ScopeService.class);
        when(mockService.get(id)).thenReturn(Optional.empty());
    }

    @Override
    protected Object executeTest() {
        ScopeController controller = new ScopeController(mockService);

        return controller.get(id);
    }

    @Override
    protected void validateResult(Object result) {
        ResponseEntity<ScopeResponse> response = (ResponseEntity<ScopeResponse>) result;
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ScopeNotFoundResponse scopeNotFoundResponse = (ScopeNotFoundResponse) response.getBody();
        assertEquals(id, scopeNotFoundResponse.id());
        assertEquals("Scope not found", scopeNotFoundResponse.message());
    }
}
