package com.unisa.cinehub.system;


import com.unisa.cinehub.system.pageObjects.RegisterViewElement;
import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.html.testbench.H2Element;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Locale;

@SpringBootTest
@ActiveProfiles("test-sistema")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RegistrazioneTest extends TestBenchTestCase {


    @Before
    public void setup() throws Exception {
        setDriver(new ChromeDriver());
        getDriver().get("http://localhost:8080/register");
    }

    @Test
    public void registrazione_valid() {
        RegisterViewElement registerViewElement = $(RegisterViewElement.class).first();
        registerViewElement.compilaForm("Andrea", "Ercolino", "edrioe@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "12345678", "12345678", true);
        registerViewElement.getBottoneElement().click();
        waitUntil(ExpectedConditions.urlContains("confirmLinkSend"), 15);
        Assert.assertTrue($(H2Element.class).first().getText().toLowerCase(Locale.ROOT).equals("link di conferma inviato all'indirizzo edrioe@gmail.com"));
    }

    @Test
    public void registrazione_minoreTrediciAnni() {
        RegisterViewElement registerViewElement = $(RegisterViewElement.class).first();
        registerViewElement.compilaForm("Andrea", "Ercolino", "edrioe@gmail.com", LocalDate.of(2017, 07, 22), "edrioe", "12345678", "12345678", true);
        registerViewElement.getBottoneElement().click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.tagName("vaadin-notification")), 15);
        Assert.assertTrue($(NotificationElement.class).first().getText().equalsIgnoreCase("devi avere più di 13 anni"));
    }

    @Test
    public void registrazione_missMatchPassword() {
        RegisterViewElement registerViewElement = $(RegisterViewElement.class).first();
        registerViewElement.compilaForm("Andrea", "Ercolino", "edrioe@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "12345678", "12345679", true);
        registerViewElement.getBottoneElement().click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.tagName("vaadin-notification")), 15);
        Assert.assertTrue($(NotificationElement.class).first().getText().equalsIgnoreCase("password e conferma password non coincidono"));
    }

    @Test
    public void registrazione_giàRegistrato() {
        RegisterViewElement registerViewElement = $(RegisterViewElement.class).first();
        registerViewElement.compilaForm("Andrea", "Ercolino", "giaregistrato@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "12345678", "12345678", true);
        registerViewElement.getBottoneElement().click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.tagName("vaadin-notification")), 15);
        Assert.assertTrue($(NotificationElement.class).first().getText().equalsIgnoreCase("Esiste già un account con questa email"));
    }

    @Test
    public void registrazione_bannato() {
        RegisterViewElement registerViewElement = $(RegisterViewElement.class).first();
        registerViewElement.compilaForm("Andrea", "Ercolino", "bannato@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "12345678", "12345678", true);
        registerViewElement.getBottoneElement().click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.tagName("vaadin-notification")), 15);
        Assert.assertTrue($(NotificationElement.class).first().getText().equalsIgnoreCase("L'account con email: bannato@gmail.com è stato bannato."));
    }

    @Test
    public void registrazione_passwordMinoreOttoCaratteri() {
        RegisterViewElement registerViewElement = $(RegisterViewElement.class).first();
        registerViewElement.compilaForm("Andrea", "Ercolino", "bannato@gmail.com", LocalDate.of(1999, 07, 22), "edrioe", "ciao", "ciao", true);
        DivElement divErrore =  registerViewElement.getPasswordFieldElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("La password deve essere di almeno 8 caratteri", divErrore.getText());
    }




    @After
    public void destroy(){
        getDriver().quit();
    }

}
