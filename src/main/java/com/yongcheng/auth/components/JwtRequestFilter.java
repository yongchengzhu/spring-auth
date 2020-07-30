package com.yongcheng.auth.components;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yongcheng.auth.sevices.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    
      final String authorizationHeader = request.getHeader("Authorization");
      String token = null;
      String username = null;

      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        token = authorizationHeader.substring(7);

        // Catch but don't throw errors because we don't want to parse for token
        // if the endpoint is unprotected.
        try {
          username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (IllegalArgumentException ex) {
          logger.warn("Received inappropriate JWT token.");
        } catch (ExpiredJwtException ex) {
          logger.warn("Received expired JWT token.");
        } catch (UnsupportedJwtException ex) {
          logger.warn("Received unsupported JWT token format.");
        } catch (MalformedJwtException ex) {
          logger.warn("Received malformed JWT token.");
        } catch (SignatureException ex) {
          logger.warn("Received corrupted JWT token signature.");
        }
      } else {
        logger.warn("JWT doesn't begin with 'Bear ' string.");
      }

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      // At this point, token can either be null or valid.
      filterChain.doFilter(request, response);
  }

}