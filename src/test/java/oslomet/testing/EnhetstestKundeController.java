package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestKundeController {
    @InjectMocks
    // denne skal testes
    private AdminKundeController kundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_loggetInn() {
        // arrange
        List<Kunde> kunder = new ArrayList<> ();
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        kunder.add(enKunde);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentAlleKunder()).thenReturn(kunder);

        // act
        List<Kunde> resultat = kundeController.hentAlle();

        // assert
        assertEquals(kunder, resultat);
    }

    @Test
    public void hentAlle_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Kunde> resultat = kundeController.hentAlle();

        // assert
        assertEquals(null, resultat);
    }

    @Test
    public void lagreKunde_loggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKunde(enKunde)).thenReturn("OK");

        // act
        String resultat = kundeController.lagreKunde(enKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void lagreKunde_ikkeLoggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = kundeController.lagreKunde(enKunde);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void endre_loggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(enKunde)).thenReturn("OK");

        // act
        String resultat = kundeController.endre(enKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endre_ikkeLoggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = kundeController.endre(enKunde);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slett_loggetInn() {
        // arrange
        String personnr = "01010110523";

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKunde(personnr)).thenReturn("OK");

        // act
        String resultat = kundeController.slett(personnr);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slett_ikkeLoggetInn() {
        // arrange
        String personnr = "01010110523";

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = kundeController.slett(personnr);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }
}
