package com.unisa.cinehub.system;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test-sistema")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoginTest extends TestBenchTestCase {

    @Before
    public void setup() throws Exception {
        setDriver(new ChromeDriver());
        getDriver().get("http://localhost:8080/login");
    }

    @Test
    public void login_valid() {

        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement password = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("giaregistrato@gmail.com");
        password.setValue("pippo");
        button.click();

    }

    @Test
    public void login_passwordErrata() throws InterruptedException {
        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement password = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("giaregistrato@gmail.com");
        password.setValue("ciao");
        button.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/vaadin-vertical-layout/vaadin-login-form")));
        LoginFormElement loginFormElement = $(LoginFormElement.class).first();
        Assertions.assertTrue(loginFormElement.getErrorComponent().isDisplayed());
        //DivElement errore = $(DivElement.class).attribute("part", "error-message").first();

    }

    @After
    public void destroy(){
        getDriver().quit();
    }


}
