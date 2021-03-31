package com.example.demo.controller.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {

  private final Environment environment;

  public JwtInterceptor(final Environment environment){
    this.environment = environment;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if(handler.getClass() != HandlerMethod.class){
      return true;
    }
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
    if (loginRequired == null) {
        return true;
    }
    String authorization = request.getHeader("Authorization");
    if(!this.isAuthorizationBearer(authorization)){
      this.unauthorize(response);
      return false;
    }
    String token = this.getToken(authorization);
    if(!this.verify(token)){
      this.unauthorize(response);
      return false;
    }
    return true;
  }

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
  public void unauthorize(HttpServletResponse response) throws IOException{
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }
}