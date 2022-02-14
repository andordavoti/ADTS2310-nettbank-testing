


package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestKontoController {
    @InjectMocks
    // denne skal testes
    private AdminKontoController kontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_loggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();

        konti.add(new Konto());

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentAlleKonti()).thenReturn(konti);

        // act
        List<Konto> resultat = kontoController.hentAlleKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentAlleKonti_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = kontoController.hentAlleKonti();

        // assert
        assertEquals(null, resultat);
    }

    @Test
    public void registrerKonto_loggetInn() {
        // arrange
        Konto konto = new Konto();

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKonto(konto)).thenReturn("OK");

        // act
        String resultat = kontoController.registrerKonto(konto);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void lagreKunde_ikkeLoggetInn() {
        // arrange
        Konto konto = new Konto();

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = kontoController.registrerKonto(konto);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void endreKonto_loggetInn() {
        // arrange
        Konto konto = new Konto();

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKonto(konto)).thenReturn("OK");

        // act
        String resultat = kontoController.endreKonto(konto);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKonto_ikkeLoggetInn() {
        // arrange
        Konto konto = new Konto();

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = kontoController.endreKonto(konto);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slettKonto_loggetInn() {
        // arrange
        String kontonummer = "12345678912";

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKonto(kontonummer)).thenReturn("OK");

        // act
        String resultat = kontoController.slettKonto(kontonummer);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKonto_ikkeLoggetInn() {
        // arrange
        String kontonummer = "12345678912";

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = kontoController.slettKonto(kontonummer);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }
}
