package com.unisa.cinehub.test.system.login;



import com.vaadin.testbench.TestBenchTestCase;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;


import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.chrome.ChromeDriver;


public class LoginTest extends TestBenchTestCase {

    @Before
    public void setup() throws Exception {
        /*System.setProperty("webdriver.chrome.driver", "C:\\Users\\maria\\Desktop\\chromedriver.exe");*/
        setDriver(new ChromeDriver());
        getDriver().get("http://localhost:8080/login");

    }

    @Test
    public void clickButton() {

        TextFieldElement email = $(TextFieldElement.class).get(0);
        TextFieldElement password = $(TextFieldElement.class).get(1);
        ButtonElement loginButton = $(ButtonElement.class).first();

        email.setValue("andreaercolino@gmail.com");
        password.setValue("arturo");
        loginButton.click();


    }

}
