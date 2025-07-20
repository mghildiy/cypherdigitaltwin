package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.unit;

import com.cypherlabs.cypherdigitaltwin.common.BaseUnitTestTemplate;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.ScopeController;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.CreateScopeRequest;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.exception.ScopeWithSameNameExistsException;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScopeController_Create_DuplicateNameError_Test extends BaseUnitTestTemplate {

    private CreateScopeRequest request;
    private Object response;
    private ScopeService mockService;
    private String name = "Dummy Scope";

    @Override
    protected void prepareData() {
        Location location = new Location(18.0, 72.0);
        Set<String> tags = Set.of("test" ,"dummy", "fake");
        request = new CreateScopeRequest(name, location, tags, null);
        mockService = mock(ScopeService.class);
        when(mockService.create(name, location, tags, null))
                .thenThrow(new ScopeWithSameNameExistsException(String.format("Scope with name '%s' already exists", name)));
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
        ScopeWithSameNameExistsException response = (ScopeWithSameNameExistsException) result;
        String expectedMessage = String.format("Scope with name '%s' already exists", name);
        assertEquals(expectedMessage, response.getMessage());
    }
}
