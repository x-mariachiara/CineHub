package com.unisa.cinehub;

import com.unisa.cinehub.data.entity.Moderatore;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.ResponsabileCatalogo;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.time.LocalDate;
import java.util.Arrays;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    @Primary
    public UserDetailsService niceConfiguration() {
        Recensore recensore = new Recensore("recensore@gmail.com", "Recen", "Sore", LocalDate.of(1996, 2, 4),"recy", "pass", false, true);
        //User.withUsername(recensore.getUsername()).password(recensore.getPassword()).roles("test").build();
        ResponsabileCatalogo responsabileCatalogo = new ResponsabileCatalogo("catalogo@gmail.com", "Cata", "Logo", LocalDate.of(1996, 2, 6),"caty", "pass", false, true);
        Moderatore moderatoreAccount = new Moderatore("account@gmail.com", "Acc", "Ount", LocalDate.of(1996, 5, 4),"accy", "pass", false, true, Moderatore.Tipo.MODACCOUNT);
        Moderatore moderatoreRecensioni = new Moderatore("recensioni@gmail.com", "Recen", "Sioni", LocalDate.of(1997, 5, 4),"reccy", "pass", false, true, Moderatore.Tipo.MODCOMMENTI);

        return new InMemoryUserDetailsManager(Arrays.asList(
                User.withUsername(recensore.getEmail()).password(recensore.getPassword()).roles("test").build(),
                User.withUsername(responsabileCatalogo.getEmail()).password(responsabileCatalogo.getPassword()).roles("test").build(),
                User.withUsername(moderatoreAccount.getEmail()).password(moderatoreAccount.getPassword()).roles("test").build(),
                User.withUsername(moderatoreRecensioni.getEmail()).password(moderatoreRecensioni.getPassword()).roles("test").build()
        ));

    }
}
