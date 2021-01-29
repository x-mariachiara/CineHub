package com.unisa.cinehub.system;


import com.unisa.cinehub.Application;
import com.unisa.cinehub.system.pageObjects.RecensioneComponentElement;
import com.unisa.cinehub.system.pageObjects.RecensioneObjectElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.ImageElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextAreaElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void a_recensione_valid(){
        ImageElement locandina = $(ImageElement.class).id("locandina-17");
        locandina.click();
        ButtonElement scriviRec = $(ButtonElement.class).id("aggiungi-recensione");
        scriviRec.scrollIntoView();
        scriviRec.click();
        RecensioneObjectElement recensioneObjectElement = $(RecensioneObjectElement.class).waitForFirst();
        recensioneObjectElement.scriviRecensione("4", "Bel Film!");
        $(ButtonElement.class).id("invia-recensione").click();
        RecensioneComponentElement recensione = $(RecensioneComponentElement.class).waitForFirst(20);
        String contenuto = recensione.$("vaadin-vertical-layout").first().$("p").attributeContains("class", "contenuto").first().getText();
        Assert.assertEquals("Bel Film!", contenuto);
    }

    @Test
    public void g_recensione_nonValid(){
        ImageElement locandina = $(ImageElement.class).id("locandina-17");
        locandina.click();
        ButtonElement scriviRec = $(ButtonElement.class).id("aggiungi-recensione");
        scriviRec.scrollIntoView();
        scriviRec.click();
        RecensioneObjectElement recensioneObjectElement = $(RecensioneObjectElement.class).waitForFirst();
        recensioneObjectElement.scriviRecensione("4", "CiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaooCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiaoCiao");
        $(ButtonElement.class).id("invia-recensione").click();
        NotificationElement notificationElement = $(NotificationElement.class).waitForFirst();
        Assert.assertEquals("Recensione non valida", notificationElement.getText());
    }

    @Test
    public void b_recensione_nonLoggato(){
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
        waitUntil(ExpectedConditions.urlContains("login"));
    }

    @Test
    public void c_risposta_valid(){
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
    public void e_mettiMiPiace() {
        ImageElement locandina = $(ImageElement.class).id("locandina-16");
        locandina.click();
        ButtonElement scriviRec = $(ButtonElement.class).id("aggiungi-recensione");
        scriviRec.scrollIntoView();

        RecensioneComponentElement recensioneComponentElement = $(RecensioneComponentElement.class).first();
        Integer oldNumMipiace = Integer.parseInt(recensioneComponentElement.getNumMipiaceElement().getText());
        recensioneComponentElement.getMiPiaceButton().click();

        Assert.assertTrue(oldNumMipiace < Integer.parseInt(recensioneComponentElement.getNumMipiaceElement().getText()));
    }

    @Test
    public void f_cambiaMiPiace() {
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
    public void d_togliMiPiace() {
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
