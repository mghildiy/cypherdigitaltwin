package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.unit;

import com.cypherlabs.cypherdigitaltwin.common.BaseTestTemplate;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.ScopeController;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.GetScopeResponse;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.ScopeResponse;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.service.ScopeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScopeController_Get_Found_Test extends BaseTestTemplate {

    private String id = "01K01YBNR1Z1JA2626PABG";
    private ScopeService mockService;
    private Scope dummyScope;

    @Override
    protected void prepareData() {
        String name = "Dummy Scope";
        Location location = new Location(18.0, 72.0);
        Set<String> tags = Set.of("test" ,"dummy", "fake");
        mockService = mock(ScopeService.class);
        dummyScope = new Scope(id, name, location, tags, null);
        when(mockService.get(id)).thenReturn(Optional.of(dummyScope));
    }

    @Override
    protected Object executeTest() {
        ScopeController controller = new ScopeController(mockService);

        return controller.get(id);
    }

    @Override
    protected void validateResult(Object result) {
        ResponseEntity<ScopeResponse> response = (ResponseEntity<ScopeResponse>) result;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GetScopeResponse getScopeResponse = (GetScopeResponse) response.getBody();
        assertEquals(dummyScope.getId(), getScopeResponse.id());
        assertEquals(dummyScope.getName(), getScopeResponse.name());
        assertEquals(dummyScope.getTags(), getScopeResponse.tags());
        assertEquals(dummyScope.getLocation(), getScopeResponse.location());
        assertEquals(dummyScope.getStatus(), getScopeResponse.status());
        assertNull(getScopeResponse.parent());
    }
}
