package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.unit;

import com.cypherlabs.cypherdigitaltwin.common.BaseUnitTestTemplate;
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

public class ScopeController_Create_WithParent_Test extends BaseUnitTestTemplate {

    private CreateScopeRequest request;
    private ScopeService mockService;
    private Scope dummyScope;
    private Scope dummyParentScope;

    @Override
    protected void prepareData() {
        String name = "Dummy Scope";
        Location location = new Location(18.0, 72.0);
        Set<String> tags = Set.of("test" ,"dummy", "fake");
        dummyParentScope = new Scope("01K01YBNR1Z1JA2626PABF", null, null , null, null);
        request = new CreateScopeRequest(name, location, tags, dummyParentScope.getId());
        mockService = mock(ScopeService.class);
        dummyScope = new Scope("01K01YBNR1Z1JA2626PABG", name, location, tags, dummyParentScope);
        when(mockService.create(name, location, tags, dummyParentScope.getId())).thenReturn(dummyScope);
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
