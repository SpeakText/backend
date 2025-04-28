package com.speaktext.backend.auth.config;

import com.speaktext.backend.auth.presentation.interceptor.AdminArgumentResolver;
import com.speaktext.backend.auth.presentation.interceptor.AuthorArgumentResolver;
import com.speaktext.backend.auth.presentation.interceptor.MemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebAuthConfig implements WebMvcConfigurer {

    private final MemberArgumentResolver memberArgumentResolver;
    private final AuthorArgumentResolver authorArgumentResolver;
    private final AdminArgumentResolver adminArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberArgumentResolver);
        resolvers.add(authorArgumentResolver);
        resolvers.add(adminArgumentResolver);
    }

}
