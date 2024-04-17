package com.ddh.board.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ddh.board.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해줌
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  // OncePerRequestFilter class : Http Request의 한 번의 요청에 대해 한 번만 실행해주는 Filter
  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {

      String token = parseBearerToken(request);

      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      String email = jwtProvider.validate(token);

      if (email == null) {
        filterChain.doFilter(request, response);
        return;
      }

      AbstractAuthenticationToken authorizationToken = new UsernamePasswordAuthenticationToken(email, null,
          AuthorityUtils.NO_AUTHORITIES);

      authorizationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
      securityContext.setAuthentication(authorizationToken);

      SecurityContextHolder.setContext(securityContext);

    } catch (Exception exception) {
      exception.printStackTrace();
    }

    filterChain.doFilter(request, response); // 다음 filter로 넘김

  }

  private String parseBearerToken(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");

    boolean hasAuthorization = StringUtils.hasText(authorization);
    // hasText : String 타입의 값이 null, 문자열 길이가 0, 공백만으로 채워져있다면 false 그렇지 않으면 true 반환

    if (!hasAuthorization)
      return null;

    boolean isBearer = authorization.startsWith("Bearer ");

    if (!isBearer)
      return null;

    String token = authorization.substring(7);
    return token;
  }
}
