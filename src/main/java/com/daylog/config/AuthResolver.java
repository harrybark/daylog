package com.daylog.config;

import com.daylog.handler.ex.CustomUnAuthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isUserSession = parameter.getParameterType().equals(UserSession.class);
        return isUserSession;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authorization");

        if ( accessToken == null || "".equals(accessToken)) {
            throw new CustomUnAuthorizedException();
        }

        UserSession userSession = new UserSession(1L);

        return userSession;
    }
}
