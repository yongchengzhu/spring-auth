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
        // extract the jwt and username from the header.
        token = authorizationHeader.substring(7);

        // Parse token from header and
        // handle all exceptions from JwtParser::parseClaimsJws
        try {
          username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (IllegalArgumentException ex) {
          throw new ServletException("Unable to get JWT token");
        } catch (ExpiredJwtException ex) {
          throw new ServletException("JWT token has expired");
        } catch (UnsupportedJwtException ex) {
          throw new ServletException("Received unsupported JWT format");
        } catch (MalformedJwtException ex) {
          throw new ServletException("JWT was not correctly constructed");
        } catch (SignatureException ex) {
          throw new ServletException("Failed to verify JWT signature");
        }
      } else {
        throw new ServletException("JWT Token does not begin with Bearer string");
      }

      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      filterChain.doFilter(request, response);
  }

}