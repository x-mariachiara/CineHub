package com.unisa.cinehub.system;


import com.unisa.cinehub.system.pageObjects.PuntataFormElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.grid.testbench.GridColumnElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTRElement;
import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.chrome.ChromeDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GestionePuntataTest extends TestBenchTestCase {


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
        getDriver().get("http://localhost:8080/gestionecatalogo/puntata");
    }

    @Test
    public void a_addPuntata_valid() {
        $(ButtonElement.class).id("aggiungi-puntata").click();
        PuntataFormElement puntataFormElement = $(PuntataFormElement.class).id("puntata-form");
        puntataFormElement.compilaForm("La prima puntata",
                "blablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
                "1",
                "2",
                "Le Terrificanti Avventure Di Sabrina");
        puntataFormElement.getSaveButtonElement().click();
        GridElement gridElement = $(GridElement.class).first();
        GridColumnElement cellaNome = gridElement.getColumn("Titolo");
        GridTRElement primaRiga = gridElement.getRow(0);
        Assert.assertEquals("La prima puntata", primaRiga.getCell(cellaNome).getText());
    }


    @Test
    public void b_addPuntata_sinossiNonValida() {
        $(ButtonElement.class).id("aggiungi-puntata").click();
        PuntataFormElement puntataFormElement = $(PuntataFormElement.class).id("puntata-form");
        puntataFormElement.compilaForm("La prima puntata",
                "blablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
                "1",
                "1",
                "Le Terrificanti Avventure Di Sabrina");
        DivElement divErrore =  puntataFormElement.getSinossiElement().$(DivElement.class).first().$(DivElement.class).attribute("part", "error-message").first();
        Assert.assertEquals("Inserire meno di mille caratteri", divErrore.getText());
    }

    @Test
    public void c_addPuntata_duplicata() {
        $(ButtonElement.class).id("aggiungi-puntata").click();
        PuntataFormElement puntataFormElement = $(PuntataFormElement.class).id("puntata-form");
        puntataFormElement.compilaForm("Una puntata che ha titolo diverso ma stessa stagione e numero",
                "blablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
                "1",
                "2",
                "Le Terrificanti Avventure Di Sabrina");
        puntataFormElement.getSaveButtonElement().click();
        NotificationElement notifica = $(NotificationElement.class).waitForFirst();
        Assert.assertEquals("Puntata già presente", notifica.getText());
    }

    @After
    public void destroy(){
        getDriver().quit();
    }
}
