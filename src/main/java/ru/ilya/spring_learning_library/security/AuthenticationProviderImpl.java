package ru.ilya.spring_learning_library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
    String password = authentication.getCredentials().toString();

    if (!password.equals(userDetails.getPassword())) {
      throw new BadCredentialsException("Incorrect password");
    }

    return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return true;
  }
}
