package com.unisa.cinehub.system;


import com.unisa.cinehub.system.PageObjects.CastFormElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.grid.testbench.GridColumnElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTRElement;
import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("testsistema")
public class GestioneCastTest extends TestBenchTestCase {

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
        getDriver().get("http://localhost:8080/gestionecatalogo/cast");
    }

    @Test
    public void aggiungiCast_valid() {
        $(ButtonElement.class).id("aggiungi-cast").click();
        CastFormElement castFormElement = $(CastFormElement.class).first();
        castFormElement.compilaForm("Kevin", "Spacey");
        castFormElement.getSalvaButtonElement().click();
        GridElement gridElement = $(GridElement.class).first();
        GridColumnElement cellaNome = gridElement.getColumn("Nome");
        GridColumnElement cellaCognome = gridElement.getColumn("Cognome");
        GridTRElement primaRiga = gridElement.getRow(0);
        Assert.assertEquals("Kevin", primaRiga.getCell(cellaNome).getText());
        Assert.assertEquals("Spacey", primaRiga.getCell(cellaCognome).getText());
    }

    @Test
    public void aggiungiCast_nomeVuoto() {
        $(ButtonElement.class).id("aggiungi-cast").click();
        CastFormElement castFormElement = $(CastFormElement.class).first();
        castFormElement.compilaForm(" ", "Spacey");
        castFormElement.getCognomeFieldElement().click();
        DivElement divErrore =  castFormElement.getNomeFieldElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("Non deve essere vuoto", divErrore.getText());
    }

    @Test
    public void aggiungiCast_cognomeVuoto() {
        $(ButtonElement.class).id("aggiungi-cast").click();
        CastFormElement castFormElement = $(CastFormElement.class).first();
        castFormElement.compilaForm("Kevin", " ");
        castFormElement.getCognomeFieldElement().click();
        DivElement divErrore =  castFormElement.getCognomeFieldElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("Non deve essere vuoto", divErrore.getText());
    }

    @Test
    public void eliminaCast_valid() {
        GridElement gridElement = $(GridElement.class).first();
        Integer vechhioNumeroDiRighe = gridElement.getRowCount();
        GridTRElement primaRiga = gridElement.getRow(0);
        GridColumnElement gridColumnElement = gridElement.getColumn("Nome");
        primaRiga.getCell(gridColumnElement).click();
        CastFormElement castFormElement = $(CastFormElement.class).waitForFirst();
        castFormElement.getEliminaButtonElement().click();
        Assert.assertTrue(vechhioNumeroDiRighe > gridElement.getRowCount());
    }



    @After
    public void destroy(){
        getDriver().quit();
    }

}
