package com.shaheen.csnotification.config;

import com.shaheen.csnotification.exception.BadCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Order(0)
@Component
public class APIKeyAuthFilter extends OncePerRequestFilter {

  @Value("${com.shaheen.apiSecurity.apiKeyHeader.name}")
  private String principalRequestHeader;

  @Value("${com.shaheen.apiSecurity.apiKeyHeader.value}")
  private String principalRequestValue;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String apiKeyValue = request.getHeader(principalRequestHeader);
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication =
        new Authentication() {
          @Override
          public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
          }

          @Override
          public Object getCredentials() {
            return null;
          }

          @Override
          public Object getDetails() {
            return null;
          }

          @Override
          public Object getPrincipal() {
            return null;
          }

          @Override
          public boolean isAuthenticated() {
            return false;
          }

          @Override
          public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}

          @Override
          public String getName() {
            return null;
          }
        };

    if (principalRequestValue.equals(apiKeyValue)) {
      authentication.setAuthenticated(true);
    } else {
      authentication.setAuthenticated(false);
      throw new BadCredentialsException("The API key was not found or not the expected value.");
    }
    context.setAuthentication(authentication);
  }
}
