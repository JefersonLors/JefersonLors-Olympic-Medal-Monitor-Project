package com.authentication_ms.securityConfiguration;

import com.authentication_ms.repository.AuthenticationRepository;
import com.authentication_ms.repository.UserRepository;
import com.authentication_ms.service.JWTokenService;
import com.authentication_ms.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private JWTokenService tokenService;

    @Autowired
    private UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token =  this.recoverToken(request);

        if( token != null ){
            String login = tokenService.validateToken(token);
            UserDetails user = this.authenticationRepository.findByLogin(login);
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if( token == null || token.isEmpty() || !token.startsWith("Bearer "))
            return null;
        return token.replace("Bearer ", "");
    }
}
