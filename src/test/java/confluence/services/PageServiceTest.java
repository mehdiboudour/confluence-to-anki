package confluence.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import confluence.model.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.JsonLoader;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class PageServiceTest {

    private static final String PAGE_SAMPLE = "src/test/resources/confluence/services/page.json";
    private PageService serviceTest;

    @Mock
    private GetRequest mockGetRequest;
    @Mock
    private HttpRequest mockHttpRequest;
    @Mock
    private HttpResponse<Page> mockHttpResponse;

    @BeforeEach
    void init() {
        serviceTest = Mockito.spy(new PageService());
        mockGetRequest = Mockito.mock(GetRequest.class);
        mockHttpRequest = Mockito.mock(HttpRequest.class);
        mockHttpResponse = Mockito.mock(HttpResponse.class);
        Mockito.doReturn(mockGetRequest).when((ResourceAbstractService) serviceTest).buildAuthorizedGetRequest();
        Mockito.when(mockGetRequest.routeParam(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString()
        )).thenReturn(mockGetRequest);
        Mockito.when(mockGetRequest.queryString(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString()
        )).thenReturn(mockHttpRequest);
    }

    @Test
    void getPageTest() throws UnirestException, IOException {
        Page expected = JsonLoader.loadObjectFrom(Page.class, PAGE_SAMPLE);

        Mockito.when(mockHttpRequest.asObject(Page.class)).thenReturn(mockHttpResponse);
        Mockito.when(mockHttpResponse.getBody()).thenReturn(expected);
        Page actual = serviceTest.getPage("", "");
        Assertions.assertEquals(expected.getBody().getView().getValue(),
                actual.getBody().getView().getValue());
    }

    @Test
    void getPageExceptionTest() throws UnirestException {
        Mockito.when(mockHttpRequest.asObject(Page.class))
                .thenThrow(new UnirestException("http 400 Bad Request"));
        try {
            serviceTest.getPage("", "");
        } catch (Exception e) {
            Assertions.assertInstanceOf(UnirestException.class, e);
        }
    }

}
