package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_ikkeloggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_loggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner("22351010110","2000-01-01","2000-01-04");

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_loggetInn() {
        // arrange
        String kontonummer = "22351010110";
        String fraDato = "2000-01-01";
        String tilDato = "2000-01-04";
        Konto konto = new Konto();
        when(sjekk.loggetInn()).thenReturn("12345678912");
        when(repository.hentTransaksjoner(kontonummer, fraDato, tilDato)).thenReturn(konto);

        // act
        Konto resultat = bankController.hentTransaksjoner(kontonummer, fraDato, tilDato);

        // assert
        assertEquals(konto, resultat);
    }

    @Test
    public void hentSaldi_loggetInn() {
        // arrange
        List<Konto> saldo = new ArrayList<>();
        Konto konto3 = new Konto("105010123451", "01010110529",
                666, "Lønnskonto", "NOK", null);
        saldo.add(konto3);

        when(sjekk.loggetInn()).thenReturn("105010123451");
        when(repository.hentSaldi(anyString())).thenReturn(saldo);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(saldo, resultat);
    }


    @Test
    public void hentSaldi_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registerBetaling_loggetInn() {
        // arrange
        List<Transaksjon> transaksjon = new ArrayList<>();
        Transaksjon enTransaksjon = new Transaksjon(4, "201020123472",
                5000.5, "2015-03-30", "Husleie", "105010123456", "1");
        transaksjon.add(enTransaksjon);
        Konto konto3 = new Konto("105010123451", "01010110529",
                666, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(konto3.getPersonnummer());
        when(repository.registrerBetaling(enTransaksjon)).thenReturn("OK");

        // act
        String resultat = bankController.registrerBetaling(enTransaksjon);

        // assert
        assertEquals("OK", resultat);
    }


    @Test
    public void registerBetaling_ikkeLoggetInn() {
        // arrange
        Transaksjon enTransaksjon = new Transaksjon(4, "201020123472",
                5000.5, "2015-03-30", "Husleie", "105010123456", "1");
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String transaksjon = bankController.registrerBetaling(enTransaksjon);

        // assert
        assertNull(transaksjon);
    }

    @Test
    public void hentBetaling_loggetInn() {
        // arrange
        List<Transaksjon> transaksjon = new ArrayList<>();
        Transaksjon enTransaksjon = new Transaksjon(4, "201020123472",
                5000.5, "2015-03-30", "Husleie", "105010123456", "1");
        transaksjon.add(enTransaksjon);


        Konto konto3 = new Konto("105010123451", "01010110529",
                666, "Lønnskonto", "NOK", null);



        when(sjekk.loggetInn()).thenReturn("105010123451");
        when(repository.hentBetalinger(konto3.getPersonnummer())).thenReturn(transaksjon);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjon, resultat);
    }


    @Test
    public void hentBetaling_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List <Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_loggetInn() {
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon = new Transaksjon(4, "201020123472",
                5000.5, "2015-03-30", "Husleie", "105010123456", "1");
        transaksjoner.add(transaksjon);

        Konto konto3 = new Konto("105010123451", "01010110529",
                666, "Lønnskonto", "NOK", null);
        konto3.setTransaksjoner(transaksjoner);

        when(sjekk.loggetInn()).thenReturn("105010123451");
        when(repository.utforBetaling(transaksjon.getTxID())).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(transaksjon.getTxID());

        // assert
        assertEquals(transaksjoner, resultat);
    }


    @Test
    public void utforBetaling_ikkeLoggetInn() {
        // arrange
        List<Transaksjon> transaksjon = new ArrayList<>();
        Transaksjon enTransaksjon = new Transaksjon(4, "201020123472",
                5000.5, "2015-03-30", "Husleie", "105010123456", "1");
        transaksjon.add(enTransaksjon);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List <Transaksjon> resultat = bankController.utforBetaling(enTransaksjon.getTxID());

        // assert
        assertNull(resultat);
    }

    @Test
    public void endre_loggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn(enKunde.getPersonnummer());

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals(enKunde.getPersonnummer(), resultat);
    }

    @Test
    public void endre_ikkeLoggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.endre(enKunde);

        // assert
        assertNull(resultat);
    }
}

