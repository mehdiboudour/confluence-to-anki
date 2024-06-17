package confluence.services;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


class ResourceAbstractServiceTest {

    private ResourceAbstractService serviceTest;

    @Mock
    private GetRequest mockGetRequest;

    @BeforeEach
    void init() {
        serviceTest = Mockito.mock(
                ResourceAbstractService.class,
                Mockito.CALLS_REAL_METHODS
        );
        try(MockedStatic<Unirest> mockUnirest = Mockito.mockStatic(Unirest.class)) {
            mockUnirest.when(() -> Unirest.get(ArgumentMatchers.anyString()))
                    .thenReturn(mockGetRequest);
        }
    }

    @Test
    void buildAuthorizedGetRequestTest() {
        GetRequest getRequest = serviceTest.buildAuthorizedGetRequest();
        Assertions.assertNotNull(getRequest);
    }
}
