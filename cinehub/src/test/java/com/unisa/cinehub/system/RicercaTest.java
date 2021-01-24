package com.unisa.cinehub.system;


import com.unisa.cinehub.system.PageObjects.CardMediaElement;
import com.unisa.cinehub.system.PageObjects.RicercaObjectElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("testsistema")
public class RicercaTest extends TestBenchTestCase {

    @Before
    public void setup() throws Exception {
        setDriver(new ChromeDriver());
        getDriver().get("http://localhost:8080/home");
    }

    @Test
    public void ricercaPerTitolo_valid(){
        RicercaObjectElement ricercaObjectElement = $(RicercaObjectElement.class).first();
        ricercaObjectElement.getTextFieldElement().setValue("Hol");
        ricercaObjectElement.getCercaButton().click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.className("card-media")));
        CardMediaElement cardMediaElement = $(CardMediaElement.class).first();

        Assert.assertTrue(cardMediaElement.getTitleElement().getText().contains("Hol"));
    }


    @Test
    public void ricercaPerGenere_valid(){
        RicercaObjectElement ricercaObjectElement = $(RicercaObjectElement.class).first();
        System.out.println(ricercaObjectElement.getMultiComboBoxElement().getTagName() + "\n\n");
        ricercaObjectElement.getMultiComboBoxElement().openComboBox();
//        ricercaObjectElement.getMultiComboBoxElement().$(DivElement.class).first().$("vaadin-combo-box-light").first().callFunction("setAttribute", "opened", "true");
        TestBenchElement comboBoxItemContainer = $("vaadin-combo-box-overlay").waitForFirst();
        List<TestBenchElement> comboBoxItems = comboBoxItemContainer.$("div").get(1).$("div").id("content").$("div").id("scroller").$("iron-list").first().$("vaadin-combo-box-item").all();
        TestBenchElement fantascienza;
        for (TestBenchElement singleItem : comboBoxItems) {
            if(singleItem.$("div").id("content").getText().equalsIgnoreCase("fantascienza")){
                fantascienza = singleItem.$("div").id("content");
                fantascienza.click();
                break;
            }
        }

        ButtonElement cercabutt = $(ButtonElement.class).first();
        cercabutt.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.className("card-media")), 4);
        CardMediaElement cardMediaElement = $(CardMediaElement.class).first();
        Assert.assertEquals("matrix", cardMediaElement.getTitleElement().getText().toLowerCase());

    }

    @Test
    public void ricerca_nonValida(){
        RicercaObjectElement ricercaObjectElement = $(RicercaObjectElement.class).first();
        ricercaObjectElement.getCercaButton().click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.tagName("vaadin-notification")));
        NotificationElement notifica = $(NotificationElement.class).first();

        Assert.assertEquals("Devi inserire un titolo e/o almeno un genere", notifica.getText());
    }



//    @After
//    public void destroy(){
//        getDriver().quit();
//    }
}
