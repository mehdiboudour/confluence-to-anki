import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled
class LoggingTest {
    //https://www.baeldung.com/java-logging-intro

    private static final Logger LOG = LogManager.getLogger(LoggingTest.class);
    @Test
    void loggingTest() {
        LOG.info("Hello Info!");
        LOG.debug("Hello Debug!");
        LOG.error("Hello Error!");
        Assertions.assertTrue(Boolean.TRUE, "test ok.");
    }

}
