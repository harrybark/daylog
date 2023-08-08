package com.daylog.config;

import com.daylog.handler.ex.CustomUnAuthorizedException;
import com.daylog.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.apache.tomcat.util.codec.binary.Base64.*;


@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final String KEY = "v8up323RprJyfvrYvGCHYZG5jq3vgFg7tkhaJoQhY6U=";

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isUserSession = parameter.getParameterType().equals(UserSession.class);
        return isUserSession;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //String accessToken = webRequest.getHeader("Authorization");
        /*
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        if ( httpServletRequest == null ) {
            throw new CustomUnAuthorizedException();
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        if ( cookies.length == 0 ) {
            throw new CustomUnAuthorizedException();
        }

        String accessToken = Arrays.stream(cookies)
                .filter(item -> item.getName().equals("SESSION"))
                .findAny()
                .map(Cookie::getValue)
                .orElseThrow(CustomUnAuthorizedException::new);

        if ( accessToken == null || "".equals(accessToken)) {
            throw new CustomUnAuthorizedException();
        }
        */

        String jws = webRequest.getHeader("Authorization");
        if ( jws == null || jws.equals("") ) {
            throw new CustomUnAuthorizedException();
        }

        try {

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(appConfig.getJwtKey())
                    .build()
                    .parseClaimsJws(jws);

            String userId = claimsJws.getBody().getSubject();
            return new UserSession(Long.parseLong(userId));

        } catch (JwtException e) {
            throw new CustomUnAuthorizedException();
        }
    }
}
