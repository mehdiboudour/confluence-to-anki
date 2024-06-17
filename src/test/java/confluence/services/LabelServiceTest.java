package confluence.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import confluence.model.Labels;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.JsonLoader;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {

    private static final String LABELL_SAMPLE = "src/test/resources/confluence/services/labels.json";
    private LabelService serviceTest;

    @Mock
    private GetRequest mockGetRequest;
    @Mock
    private HttpRequest mockHttpRequest;
    @Mock
    private HttpResponse<Labels> mockHttpResponse;

    @BeforeEach
    void inti() {
        serviceTest = Mockito.spy(new LabelService());
        mockGetRequest = Mockito.mock(GetRequest.class);
        mockHttpRequest = Mockito.mock(HttpRequest.class);
        mockHttpResponse = Mockito.mock(HttpResponse.class);
        Mockito.doReturn(mockGetRequest).when((ResourceAbstractService) serviceTest).buildAuthorizedGetRequest();
        Mockito.when(mockGetRequest.routeParam(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString()
        )).thenReturn(mockGetRequest);
    }

    @Test
    void getLabelsTest() throws IOException, UnirestException {
        Labels toBeReturner = JsonLoader.loadObjectFrom(Labels.class, LABELL_SAMPLE);
        Mockito.when(mockGetRequest.asObject(Labels.class)).thenReturn(mockHttpResponse);
        Mockito.when(mockHttpResponse.getBody()).thenReturn(toBeReturner);
        String expected = "Test OK.";
        Labels actual = serviceTest.getLabels("");
        actual.getResults().forEach(
                label -> Assertions.assertEquals(expected, label.getName())
        );
    }

    @Test
    void getLabelsExceptionTest() throws UnirestException {
        Mockito.when(mockGetRequest.asObject(Labels.class))
                .thenThrow(new UnirestException("400 Bad Request."));
        try {
            serviceTest.getLabels("");
        } catch (Exception exception) {
            Assertions.assertInstanceOf(UnirestException.class, exception);
        }
    }
}
