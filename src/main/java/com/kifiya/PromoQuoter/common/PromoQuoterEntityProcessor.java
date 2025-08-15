package com.kifiya.PromoQuoter.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class PromoQuoterEntityProcessor extends RequestResponseBodyMethodProcessor {

    public PromoQuoterEntityProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(RequestBody.class)
                || AbstractEntity.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        // Treat any AbstractEntity subclass as @ResponseBody
        return super.supportsReturnType(returnType)
                || AbstractEntity.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(
            Object returnValue,
            MethodParameter returnType,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest) throws HttpMediaTypeNotAcceptableException, IOException {

        HttpServletResponse servletResponse = webRequest.getNativeResponse(HttpServletResponse.class);

        if (servletResponse != null
                && AbstractEntity.class.isAssignableFrom(returnType.getParameterType())
                && isCreateOperation(webRequest)) {
            servletResponse.setStatus(HttpServletResponse.SC_CREATED);
        }

        super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }

    private boolean isCreateOperation(NativeWebRequest webRequest) {
        String method = Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)).getMethod();
        return "POST".equalsIgnoreCase(method);
    }
}

