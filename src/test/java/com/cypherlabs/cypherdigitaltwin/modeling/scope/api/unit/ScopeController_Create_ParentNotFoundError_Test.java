package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.unit;

import com.cypherlabs.cypherdigitaltwin.common.BaseTestTemplate;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.ScopeController;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.CreateScopeRequest;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.exception.ScopeNotFoundException;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScopeController_Create_ParentNotFoundError_Test extends BaseTestTemplate {

    private CreateScopeRequest request;
    private Object response;
    private ScopeService mockService;
    private String parentId = "01K01YBNR1Z1JA2626PABG";

    @Override
    protected void prepareData() {
        String name = "Dummy Scope";
        Location location = new Location(18.0, 72.0);
        Set<String> tags = Set.of("test" ,"dummy", "fake");
        request = new CreateScopeRequest(name, location, tags, parentId);
        mockService = mock(ScopeService.class);
        when(mockService.create(name, location, tags, parentId))
                .thenThrow(new ScopeNotFoundException(String.format("Parent scope '%s' not found", parentId)));
    }

    @Override
    protected Object executeTest() {
        ScopeController controller = new ScopeController(mockService);
        try {
            controller.create(request);
        } catch(Exception e) {
            response = e;
        }

        return response;
    }

    @Override
    protected void validateResult(Object result) {
        ScopeNotFoundException response = (ScopeNotFoundException) result;
        String expectedMessage = String.format("Parent scope '%s' not found", parentId);
        assertEquals(expectedMessage, response.getMessage());
    }
}
