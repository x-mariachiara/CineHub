package com.unisa.cinehub.system;


import com.opencsv.bean.CsvToBeanBuilder;
import com.unisa.cinehub.data.UtenteDataset;
import com.unisa.cinehub.system.pageObjects.RegistrazioneFormElement;
import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.html.testbench.H2Element;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootTest
@ActiveProfiles("test-sistema")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RegistrazioneTest extends TestBenchTestCase {


    @Before
    public void setup() throws Exception {
        setDriver(new FirefoxDriver());
        getDriver().get("http://localhost:8080/register");
    }

    @Test
    public void registrazione_valid() throws InterruptedException {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andrea", "Ercolino", "edrioe@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "CineHub-12", "CineHub-12", true, "Uomo", "cucinare");
        registrazioneFormElement.getBottoneElement().click();
        registrazioneFormElement.getBottoneElement().click();
        waitUntil(ExpectedConditions.urlContains("confirmLinkSend"), 25);
        Assert.assertTrue($(H2Element.class).first().getText().toLowerCase(Locale.ROOT).equals("link di conferma inviato all'indirizzo edrioe@gmail.com"));
    }

    @Test
    public void registrazione_minoreTrediciAnni() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andrea", "Ercolino", "edrioe@gmail.com", LocalDate.of(2017, 07, 22), "edrioe", "CineHub-12", "CineHub-12", true, "Uomo", "cucinare");
        registrazioneFormElement.getBottoneElement().click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.tagName("vaadin-notification")), 15);
        Assert.assertTrue($(NotificationElement.class).waitForFirst().getText().equalsIgnoreCase("devi avere più di 13 anni"));
    }

    @Test
    public void registrazione_missMatchPassword() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andrea", "Ercolino", "edrioe@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "CineHub-12", "12345679", true, "Uomo", "cucinare");
        registrazioneFormElement.getBottoneElement().click();

        DivElement divErrore =  registrazioneFormElement.getConfermaPasswordFieldElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("Password e conferma password non coincidono", divErrore.getText());
    }

    @Test
    public void registrazione_giàRegistrato() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andrea", "Ercolino", "giaregistrato@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "CineHub-12", "CineHub-12", true, "Uomo", "cucinare");
        registrazioneFormElement.getBottoneElement().click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.tagName("vaadin-notification")), 15);
        Assert.assertTrue($(NotificationElement.class).first().getText().equalsIgnoreCase("Esiste già un account con questa email"));
    }

    @Test
    public void registrazione_bannato() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andrea", "Ercolino", "bannato@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "CineHub-12", "CineHub-12", true, "Uomo", "cucinare");
        registrazioneFormElement.getBottoneElement().click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.tagName("vaadin-notification")), 15);
        Assert.assertTrue($(NotificationElement.class).first().getText().equalsIgnoreCase("L'account con email: bannato@gmail.com è stato bannato."));
    }

    @Test
    public void registrazione_passwordMinoreOttoCaratteri() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andrea", "Ercolino", "bannato@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "-12", "-12", true, "Uomo", "cucinare");

        DivElement divErrore =  registrazioneFormElement.getPasswordFieldElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("La password deve contenere almeno: una maiuscola, una minuscola e un numero e deve essere lunga almeno otto caratteri", divErrore.getText());
    }

    @Test
    public void registrazione_nomeNonValido() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andr3a", "Ercolino", "bannato@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "CineHub-12", "CineHub-12", true, "Uomo", "cucinare");

        DivElement divErrore =  registrazioneFormElement.getNomeField().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("Può contenere solo caratteri Alfabetici, punti o spazi", divErrore.getText());
    }

    @Test
    public void registrazione_nomeVuoto() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm(" ", "Ercolino", "bannato@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "CineHub-12", "CineHub-12", true, "Uomo", "cucinare");
        registrazioneFormElement.getNomeField().setValue("");
        DivElement divErrore =  registrazioneFormElement.getNomeField().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").waitForFirst();
        Assert.assertEquals("Il campo non deve essere vuoto", divErrore.getText());
    }

    @Test
    public void registrazione_emailNonValida() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andrea", "Ercolino", "bannatogmail.com", LocalDate.of(1999, 07, 22), "edrioe", "CineHub-12", "CineHub-12", true, "Uomo", "cucinare");

        DivElement divErrore =  registrazioneFormElement.getEmailField().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("deve essere un indirizzo email nel formato corretto", divErrore.getText());
    }

    @Test
    public void registrazione_usernameNotValido() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andrea", "Ercolino", "bannato@gmail.com", LocalDate.of(1999, 07, 22), "edr ioe", "CineHub-12", "CineHub-12", true, "Uomo", "cucinare");

        DivElement divErrore =  registrazioneFormElement.getUsernameElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("Lo username non può contenere spazi", divErrore.getText());
    }

    @Test
    public void registrazione_policyNotChecked() {
        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).first();
        registrazioneFormElement.compilaForm("Andrea", "Ercolino", "bannato@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "CineHub-12", "CineHub-12", false, "Uomo", "cucinare");

        Assert.assertEquals("true", registrazioneFormElement.getBottoneElement().getAttribute("disabled"));
    }


    @Test
    public void registrazione_automatica() throws InterruptedException {

        String fileName = "/home/andreaerk/PerPopolamento.csv";
        List<UtenteDataset> beans = new ArrayList<>();
        try {
            beans = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(UtenteDataset.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        RegistrazioneFormElement registrazioneFormElement = $(RegistrazioneFormElement.class).waitForFirst(40);

        for (UtenteDataset utente : beans){

            registrazioneFormElement.compilaForm(utente.getNome(), utente.getCognome(), utente.getEmail(), LocalDate.of(1999, 07, 22), utente.getEmail(), "CineHub-12", "CineHub-12", true, utente.getSesso(), utente.getHobby());
            registrazioneFormElement.getBottoneElement().click();

            waitUntil(ExpectedConditions.urlContains("confirmLinkSend"), 25);
            getDriver().navigate().back();
            waitUntil(ExpectedConditions.urlContains("register"), 25);
            getDriver().navigate().refresh();

            registrazioneFormElement = $(RegistrazioneFormElement.class).waitForFirst(40);
        }

        Assert.assertTrue(true);
    }




    @After
    public void destroy(){
        getDriver().quit();
    }

}
