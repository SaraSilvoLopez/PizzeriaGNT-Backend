package com.example.demo.controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// @Component
// @Order(1)
public class JwtFilter implements Filter {

  @Autowired
  private Environment environment;

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request  = (HttpServletRequest) req;
    String authorization = request.getHeader("Authorization");
    if(!this.isAuthorizationBearer(authorization)){
      this.unauthorize(res);
      return;
    }
    String token = this.getToken(authorization);
    if(!this.verify(token)){
      this.unauthorize(res);
      return;
    }
    chain.doFilter(req, res);
  }

  @Override
  public void destroy() {}

  @Override
  public void init(FilterConfig arg0) throws ServletException {}
  public boolean isAuthorizationBearer(String authorization){
    return authorization != null && authorization.startsWith("Bearer ");
  }
  public String getToken(String authorization){
    return authorization.substring("Bearer ".length());
  }
  public boolean verify(String token){
      try {
        Algorithm algorithm = Algorithm.HMAC256(environment.getProperty("JWT_SECRET"));
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        // TODO: hacer algo con el payload
        return true;
    } catch (JWTVerificationException  exception){
        return false;
    }
  }
  public void unauthorize(ServletResponse res) throws IOException{
    HttpServletResponse response = (HttpServletResponse) res;
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }
}