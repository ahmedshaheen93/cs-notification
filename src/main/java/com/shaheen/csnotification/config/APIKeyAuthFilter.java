package com.shaheen.csnotification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class APIKeyAuthFilter extends OncePerRequestFilter {

  @Value("${com.shaheen.apiSecurity.apiKeyHeader.name}")
  private String principalRequestHeader;

  @Value("${com.shaheen.apiSecurity.apiKeyHeader.value}")
  private String principalRequestValue;

  private Set<SimpleGrantedAuthority> getAuthority() {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    return authorities;
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    String apiKeyValue = request.getHeader(principalRequestHeader);

    if (principalRequestValue.equals(apiKeyValue) && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = new User(principalRequestHeader, apiKeyValue, getAuthority());
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
}
