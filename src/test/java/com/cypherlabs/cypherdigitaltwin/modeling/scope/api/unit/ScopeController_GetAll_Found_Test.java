package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.unit;

import com.cypherlabs.cypherdigitaltwin.common.BaseTestTemplate;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.ScopeController;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.GetScopeResponse;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScopeController_GetAll_Found_Test extends BaseTestTemplate {

    private ScopeService mockService;
    private Scope dummyScope;
    private Scope dummyScope1;

    @Override
    protected void prepareData() {
        mockService = mock(ScopeService.class);
        String id = "01K01YBNR1Z1JA2626PABG";
        dummyScope = new Scope(id, "Dummy Scope", new Location(18.0, 72.0),
                Set.of("test" ,"dummy", "fake"), null);
        String id1 = "01K01YBNR1Z1JA2626PABH";
        dummyScope1 = new Scope(id1, "Fake Scope", new Location(19.0, 71.0),
                Set.of(), dummyScope);
        when(mockService.getAll()).thenReturn(List.of(dummyScope, dummyScope1));
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
        assertTrue(response.getBody().size() == 2);
        assertEquals(dummyScope.getId(), response.getBody().getFirst().id());
        assertEquals(dummyScope1.getId(), response.getBody().get(1).id());
        assertEquals(dummyScope.getName(), response.getBody().getFirst().name());
        assertEquals(dummyScope1.getName(), response.getBody().get(1).name());
        assertEquals(dummyScope.getLocation(), response.getBody().getFirst().location());
        assertEquals(dummyScope1.getLocation(), response.getBody().get(1).location());
        assertEquals(dummyScope.getTags(), response.getBody().getFirst().tags());
        assertEquals(dummyScope1.getTags(), response.getBody().get(1).tags());
        assertNull(response.getBody().getFirst().parent());
        assertEquals(dummyScope, response.getBody().get(1).parent());
    }
}
