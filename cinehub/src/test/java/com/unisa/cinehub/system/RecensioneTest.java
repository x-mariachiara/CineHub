package com.unisa.cinehub.system;


import com.unisa.cinehub.Application;
import com.unisa.cinehub.system.pageObjects.RecensioneComponentElement;
import com.unisa.cinehub.system.pageObjects.RecensioneObjectElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.ImageElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextAreaElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test-sistema")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RecensioneTest extends TestBenchTestCase {

    @BeforeEach
    public void startup() {
        Application.restart();
    }

    @Before
    public void setup() throws Exception {
        setDriver(new ChromeDriver());
        getDriver().get("http://localhost:8080/login");
        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement password = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("giaregistrato@gmail.com");
        password.setValue("pippo");
        button.click();
        getDriver().get("http://localhost:8080/");
    }

    @Test
    public void recensione_valid(){
        ImageElement locandina = $(ImageElement.class).id("locandina-17");
        locandina.click();
        ButtonElement scriviRec = $(ButtonElement.class).id("aggiungi-recensione");
        scriviRec.scrollIntoView();
        scriviRec.click();
        RecensioneObjectElement recensioneObjectElement = $(RecensioneObjectElement.class).waitForFirst();
        recensioneObjectElement.scriviRecensione("4", "Bel Film!");
        List<RecensioneComponentElement> recensioni = $(RecensioneComponentElement.class).all();
        String contenuto = recensioni.get(1).$("vaadin-vertical-layout").first().$("p").attributeContains("class", "contenuto").first().getText();
        Assert.assertEquals("Bel Film!", contenuto);
    }

    @Test
    public void recensione_nonLoggato(){
        getDriver().get("http://localhost:8080/logout");
        waitUntil(ExpectedConditions.urlContains("login"));
        getDriver().get("http://localhost:8080/home");
        ImageElement locandina = $(ImageElement.class).id("locandina-17");
        locandina.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.className("titolo-media")));
        //waitUntil(ExpectedConditions.presenceOfElementLocated(By.className()));
        ButtonElement scriviRec = $(ButtonElement.class).id("aggiungi-recensione");
        scriviRec.scrollIntoView();

        scriviRec.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.className("login-view")));
    }

    @Test
    public void risposta_valid(){
        ImageElement locandina = $(ImageElement.class).id("locandina-16");
        locandina.click();
        ButtonElement scriviRec = $(ButtonElement.class).id("aggiungi-recensione");
        scriviRec.scrollIntoView();
        RecensioneComponentElement recensioneComponentElement = $(RecensioneComponentElement.class).first();
        recensioneComponentElement.getRispondiButton().click();
        TextAreaElement contenutoRisposta = $(TextAreaElement.class).id("contenuto-risposta");

        contenutoRisposta.setValue("Sono d'accordo!");
        $(ButtonElement.class).id("invia-risposta-button").click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.className("risposta-rec")));

    }

    @Test
    public void mettiMiPiace() {
        ImageElement locandina = $(ImageElement.class).id("locandina-16");
        locandina.click();
        ButtonElement scriviRec = $(ButtonElement.class).id("aggiungi-recensione");
        scriviRec.scrollIntoView();
        RecensioneComponentElement recensioneComponentElement = $(RecensioneComponentElement.class).first();
        Integer oldNumMipiace = Integer.parseInt(recensioneComponentElement.getNumMipiaceElement().getText());
        recensioneComponentElement.getMiPiaceButton().click();
        System.out.println(oldNumMipiace + " " +  recensioneComponentElement.getNumMipiaceElement().getText());
        Assert.assertTrue(oldNumMipiace < Integer.parseInt(recensioneComponentElement.getNumMipiaceElement().getText()));
    }

    @Test
    public void cambiaMiPiace() {
        ImageElement locandina = $(ImageElement.class).id("locandina-16");
        locandina.click();
        ButtonElement scriviRec = $(ButtonElement.class).id("aggiungi-recensione");
        scriviRec.scrollIntoView();
        RecensioneComponentElement recensioneComponentElement = $(RecensioneComponentElement.class).first();
        Integer oldNumNonMipiace = Integer.parseInt(recensioneComponentElement.getNumNonMiPiaceElement().getText());
        Integer oldNumMiPiace = Integer.parseInt(recensioneComponentElement.getNumMipiaceElement().getText());
        recensioneComponentElement.getNonMiPiaceButton().click();
        System.out.println(oldNumMiPiace + " " + oldNumNonMipiace + " " + recensioneComponentElement.getNumMipiaceElement().getText() + " " + recensioneComponentElement.getNumNonMiPiaceElement().getText());
        Assert.assertTrue(oldNumNonMipiace < Integer.parseInt(recensioneComponentElement.getNumNonMiPiaceElement().getText()));
        Assert.assertTrue(oldNumMiPiace > Integer.parseInt(recensioneComponentElement.getNumMipiaceElement().getText()));
    }

    @Test
    public void togliMiPiace() {
        ImageElement locandina = $(ImageElement.class).id("locandina-16");
        locandina.click();
        ButtonElement scriviRec = $(ButtonElement.class).id("aggiungi-recensione");
        scriviRec.scrollIntoView();
        RecensioneComponentElement recensioneComponentElement = $(RecensioneComponentElement.class).first();
        Integer oldNumMipiace = Integer.parseInt(recensioneComponentElement.getNumMipiaceElement().getText());
        recensioneComponentElement.getMiPiaceButton().click();
        Assert.assertTrue(oldNumMipiace > Integer.parseInt(recensioneComponentElement.getNumMipiaceElement().getText()));
    }

    @After
    public void destroy(){
        getDriver().quit();
    }

}
