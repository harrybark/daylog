package com.daylog.config;

import com.daylog.domain.Session;
import com.daylog.handler.ex.CustomUnAuthorizedException;
import com.daylog.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
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

        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(CustomUnAuthorizedException::new);

        return new UserSession(session.getUser().getId());
    }
}
