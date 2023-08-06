package com.daylog.config;

import com.daylog.handler.ex.CustomUnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isUserSession = parameter.getParameterType().equals(UserSession.class);
        return isUserSession;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //String accessToken = webRequest.getHeader("Authorization");
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

        UserSession userSession = new UserSession(1L);

        return userSession;
    }
}
