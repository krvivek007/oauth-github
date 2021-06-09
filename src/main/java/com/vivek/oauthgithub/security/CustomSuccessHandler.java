package com.vivek.oauthgithub.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //TODO Add code to lookup role for authenticated user.
        //TODO here I am hardcoding the role.
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_A"));
        authorities.add(new SimpleGrantedAuthority("ROLE_B"));
        Authentication newAuth = null;
        if (authentication.getClass() == OAuth2AuthenticationToken.class) {
            DefaultOAuth2User principal = (DefaultOAuth2User)((OAuth2AuthenticationToken)authentication).getPrincipal();
            DefaultOAuth2User user = new DefaultOAuth2User(authorities, principal.getAttributes(), "id");
            if (principal != null) {
                newAuth = new OAuth2AuthenticationToken(user, authorities,(((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId()));
            }
        }
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        super.onAuthenticationSuccess(request, response, newAuth);
    }
}