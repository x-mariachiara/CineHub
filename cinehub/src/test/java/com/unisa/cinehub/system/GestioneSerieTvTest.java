package com.unisa.cinehub.system;


import com.unisa.cinehub.system.pageObjects.MediaFormElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.grid.testbench.GridColumnElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTRElement;
import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test-sistema")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GestioneSerieTvTest extends TestBenchTestCase {

    @Before
    public void setup() throws Exception {
        setDriver(new ChromeDriver());
        getDriver().get("http://localhost:8080/login");
        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement password = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("pirupiru@gmail.com");
        password.setValue("ciao");
        button.click();
        getDriver().get("http://localhost:8080/gestionecatalogo/serietv");

    }

    @Test
    public void aggiungiSerieTv_valid() {
        ButtonElement aggiungiSerieTvButton = $(ButtonElement.class).id("aggiungi-serietv");
        aggiungiSerieTvButton.click();
        MediaFormElement mediaFormElement = $(MediaFormElement.class).waitForFirst();
        mediaFormElement.compilaForm("La sposa cadavere",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQMSZAifcX3QMBCdV25Aa31UphELkCIekOFMFYxY25kHk6s5nWk",
                "https://www.youtube.com/embed/ekVmEqw_5Ck",
                "2005",
                "Nell'Europa dell'Ottocento un giovane e talentuoso pianista, infila, senza saperlo, un anello di fidanzamento al dito di una donna morta. Quando questa si risveglia, conduce Victor nel mondo dell'aldilà.");
        mediaFormElement.getGeneriComboBox().openComboBox();
        TestBenchElement comboBoxItemContainer = $("vaadin-combo-box-overlay").waitForFirst();
        List<TestBenchElement> comboBoxItems = comboBoxItemContainer.$("div").get(1).$("div").id("content").$("div").id("scroller").$("iron-list").first().$("vaadin-combo-box-item").all();
        for(TestBenchElement item: comboBoxItems) {
            if (item.getText().equalsIgnoreCase("ANIMAZIONE") || item.getText().equalsIgnoreCase("HORROR")){
                item.click();
            }
        }
        mediaFormElement.getAttoriComboBox().click();

        mediaFormElement.getAttoriComboBox().openComboBox();
        comboBoxItemContainer = $("vaadin-combo-box-overlay").waitForFirst();
        comboBoxItems = comboBoxItemContainer.$("div").get(1).$("div").id("content").$("div").id("scroller").$("iron-list").first().$("vaadin-combo-box-item").all();
        comboBoxItems.get(0).click();

        mediaFormElement.getRegistiComboBox().click();

        mediaFormElement.getRegistiComboBox().openComboBox();
        comboBoxItemContainer = $("vaadin-combo-box-overlay").waitForFirst();
        comboBoxItems = comboBoxItemContainer.$("div").get(1).$("div").id("content").$("div").id("scroller").$("iron-list").first().$("vaadin-combo-box-item").all();
        comboBoxItems.get(1).click();

        mediaFormElement.getSaveButton().click();
        GridElement gridElement = $(GridElement.class).first();
        GridColumnElement gridColumnElement = gridElement.getColumn("Titolo");
        GridTRElement celleUltimaRiga = gridElement.getRow(0);
        Assert.assertEquals("La sposa cadavere", celleUltimaRiga.getCell(gridColumnElement).getText());
    }

    @Test
    public void aggiungiSerieTv_senzaGeneri() {
        ButtonElement aggiungiSerieTvButton = $(ButtonElement.class).id("aggiungi-serietv");
        aggiungiSerieTvButton.click();
        MediaFormElement mediaFormElement = $(MediaFormElement.class).waitForFirst();
        mediaFormElement.compilaForm("La sposa cadavere",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQMSZAifcX3QMBCdV25Aa31UphELkCIekOFMFYxY25kHk6s5nWk",
                "https://www.youtube.com/embed/ekVmEqw_5Ck",
                "2005",
                "Nell'Europa dell'Ottocento un giovane e talentuoso pianista, infila, senza saperlo, un anello di fidanzamento al dito di una donna morta. Quando questa si risveglia, conduce Victor nel mondo dell'aldilà.");
        mediaFormElement.getSaveButton().click();
        NotificationElement notifica = $(NotificationElement.class).waitForFirst();
        Assert.assertEquals("Si è verificato un errore", notifica.getText());
    }

    @Test
    public void aggiungiSerieTv_linkTrailerSbagliato() {
        ButtonElement aggiungiSerieTvButton = $(ButtonElement.class).id("aggiungi-serietv");
        aggiungiSerieTvButton.click();
        MediaFormElement mediaFormElement = $(MediaFormElement.class).waitForFirst();
        mediaFormElement.compilaForm("La sposa cadavere",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQMSZAifcX3QMBCdV25Aa31UphELkCIekOFMFYxY25kHk6s5nWk",
                "https://www.ciao.com/embed/ekVmEqw_5Ck",
                "2005",
                "Nell'Europa dell'Ottocento un giovane e talentuoso pianista, infila, senza saperlo, un anello di fidanzamento al dito di una donna morta. Quando questa si risveglia, conduce Victor nel mondo dell'aldilà.");
        mediaFormElement.getAnnoUscitaFieldElement().focus();

        DivElement divErrore =  mediaFormElement.getLinkTrailerFieldElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("Deve essere un url embed di youtube", divErrore.getText());
    }

    @Test
    public void aggiungiSerieTv_linkLocandinaSbagliato() {
        ButtonElement aggiungiSerieTvButton = $(ButtonElement.class).id("aggiungi-serietv");
        aggiungiSerieTvButton.click();
        MediaFormElement mediaFormElement = $(MediaFormElement.class).waitForFirst();
        mediaFormElement.compilaForm("La sposa cadavere",
                "ciao",
                "https://www.youtube.com/embed/ekVmEqw_5Ck",
                "2005",
                "Nell'Europa dell'Ottocento un giovane e talentuoso pianista, infila, senza saperlo, un anello di fidanzamento al dito di una donna morta. Quando questa si risveglia, conduce Victor nel mondo dell'aldilà.");
        mediaFormElement.getAnnoUscitaFieldElement().focus();

        DivElement divErrore =  mediaFormElement.getLinkLocandinaFieldElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("deve essere un URL valido", divErrore.getText());
    }

    @Test
    public void aggiungiSerieTv_serietvGiaPresente() {
        ButtonElement aggiungiSerieTvButton = $(ButtonElement.class).id("aggiungi-serietv");
        aggiungiSerieTvButton.click();
        MediaFormElement mediaFormElement = $(MediaFormElement.class).waitForFirst();
        mediaFormElement.compilaForm("Le Terrificanti Avventure Di Sabrina",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQMSZAifcX3QMBCdV25Aa31UphELkCIekOFMFYxY25kHk6s5nWk",
                "https://www.youtube.com/embed/ekVmEqw_5Ck",
                "2018",
                "Nell'Europa dell'Ottocento un giovane e talentuoso pianista, infila, senza saperlo, un anello di fidanzamento al dito di una donna morta. Quando questa si risveglia, conduce Victor nel mondo dell'aldilà.");
        mediaFormElement.getAnnoUscitaFieldElement().focus();
        mediaFormElement.getGeneriComboBox().openComboBox();
        TestBenchElement comboBoxItemContainer = $("vaadin-combo-box-overlay").waitForFirst();
        List<TestBenchElement> comboBoxItems = comboBoxItemContainer.$("div").get(1).$("div").id("content").$("div").id("scroller").$("iron-list").first().$("vaadin-combo-box-item").all();
        for(TestBenchElement item: comboBoxItems) {
            if (item.getText().equalsIgnoreCase("ANIMAZIONE") || item.getText().equalsIgnoreCase("HORROR")){
                item.click();
            }
        }

        mediaFormElement.getSaveButton().click();
        NotificationElement notifica = $(NotificationElement.class).waitForFirst();
        Assert.assertEquals("SerieTV già esiste", notifica.getText());
    }

    @Test
    @Transactional
    public void cancellaSerieTV_valid() {
        GridElement gridElement = $(GridElement.class).first();
        Integer vechhioNumeroDiRighe = gridElement.getRowCount();
        GridTRElement primaRiga = gridElement.getRow(0);
        GridColumnElement gridColumnElement = gridElement.getColumn("Titolo");
        primaRiga.getCell(gridColumnElement).click();
        MediaFormElement mediaFormElement = $(MediaFormElement.class).waitForFirst();
        mediaFormElement.getCancellaButton().click();
        System.out.println(vechhioNumeroDiRighe + " " + gridElement.getRowCount());
        Assert.assertTrue(vechhioNumeroDiRighe > gridElement.getRowCount());
    }


    @Test
    public void modificaSerieTV_titoloVuoto() {
        GridElement gridElement = $(GridElement.class).first();
        GridTRElement primaRiga = gridElement.getRow(0);
        GridColumnElement gridColumnElement = gridElement.getColumn("Titolo");
        primaRiga.getCell(gridColumnElement).click();
        MediaFormElement mediaFormElement = $(MediaFormElement.class).waitForFirst();

        mediaFormElement.getTitoloFieldElement().setValue("");
        DivElement divErrore =  mediaFormElement.getTitoloFieldElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("Non deve essere vuoto", divErrore.getText());

    }

    @Test
    public void modificaSerieTV_annoMinoreInvenzioneFilmografia() {
        GridElement gridElement = $(GridElement.class).first();
        GridTRElement primaRiga = gridElement.getRow(0);
        GridColumnElement gridColumnElement = gridElement.getColumn("Titolo");
        primaRiga.getCell(gridColumnElement).click();
        MediaFormElement mediaFormElement = $(MediaFormElement.class).waitForFirst();

        mediaFormElement.getAnnoUscitaFieldElement().setValue("1700");
        DivElement divErrore =  mediaFormElement.getAnnoUscitaFieldElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("Prima del 1895 non esistevano i film.", divErrore.getText());

    }

    @After
    public void destroy(){
        getDriver().quit();
    }

}
