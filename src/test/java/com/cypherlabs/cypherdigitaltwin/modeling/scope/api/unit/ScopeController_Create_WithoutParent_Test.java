package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.unit;

import com.cypherlabs.cypherdigitaltwin.common.BaseTestTemplate;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.ScopeController;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.CreateScopeRequest;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.CreateScopeResponse;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScopeController_Create_WithoutParent_Test extends BaseTestTemplate {

    private CreateScopeRequest request;
    private ScopeService mockService;
    private Scope dummyScope;

    @Override
    protected void prepareData() {
        String name = "Dummy Scope";
        Location location = new Location(18.0, 72.0);
        Set<String> tags = Set.of("test" ,"dummy", "fake");
        request = new CreateScopeRequest(name, location, tags, null);
        mockService = mock(ScopeService.class);
        dummyScope = new Scope("01K01YBNR1Z1JA2626PABG", name, location, tags, null);
        when(mockService.create(name, location, tags, null)).thenReturn(dummyScope);
    }

    @Override
    protected Object executeTest() {
        ScopeController controller = new ScopeController(mockService);

        return controller.create(request);
    }

    @Override
    protected void validateResult(Object result) {
        ResponseEntity<CreateScopeResponse> response = (ResponseEntity<CreateScopeResponse>) result;
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dummyScope.getId(), response.getBody().id());
        assertEquals(dummyScope.getStatus(), response.getBody().status());
    }
}
