package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {
    @InjectMocks
    // denne skal testes
    private Sikkerhet sikkerhet;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private MockHttpSession session;

    @Test
    public void sjekkLoggInn_OK() {
        // arrange
        String personnr = "01010110523";
        String passord = "123456";

        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnr, passord);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void sjekkLoggInn_FEIL() {
        // arrange
        String personnr = "01010110523";
        String passord = "123456";

        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnr, passord);

        // assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    @Test
    public void sjekkLoggInn_personnrValideringsFeil() {
        // arrange
        String personnr = "010101105231";
        String passord = "123456";

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnr, passord);

        // assert
        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void sjekkLoggInn_passordValideringsFeil() {
        // arrange
        String personnr = "01010110523";
        String passord = "123";

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnr, passord);

        // assert
        assertEquals("Feil i passord", resultat);
    }

    @Test
    public void loggInnAdmin_OK() {
        // arrange
        String bruker = "Admin";
        String passord = "Admin";

        // act
        String resultat = sikkerhet.loggInnAdmin(bruker, passord);

        // assert
        assertEquals("Logget inn", resultat);
    }

    @Test
    public void loggInnAdmin_FEIL() {
        // arrange
        String bruker = "admin";
        String passord = "admin";

        // act
        String resultat = sikkerhet.loggInnAdmin(bruker, passord);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void loggetInn() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), anyString());

        // arrange
        session.setAttribute("Innlogget", "12345678912");

        // act
        String resultat = sikkerhet.loggetInn();

        // assert
        assertEquals("12345678912", resultat);
    }

    @Test
    public void loggetInn_ikkeLoggetInn() {
        // arrange
        session.setAttribute("Innlogget", null);

        // act
        String resultat = sikkerhet.loggetInn();

        // assert
        assertEquals(null, resultat);
    }
}
