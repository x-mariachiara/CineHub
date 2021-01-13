package com.unisa.cinehub.security;

import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.model.service.UtenteService;
import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

@Component
public class SecurityUtils {

    @Autowired
    private static UtenteService utenteService;



    public SecurityUtils(UtenteService utenteService) {
        this.utenteService = utenteService;
    }




    static boolean isFrameworkInternalRequest(HttpServletRequest request){
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(ServletHelper.RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    public static Utente getLoggedIn() {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(p instanceof UserDetails) {
            Utente utente =  utenteService.findByEmail(((UserDetails) p).getUsername());
            return utente;
        } else {
            Utente utente =  utenteService.findByEmail(p.toString());
            return utente;
        }
    }

}
