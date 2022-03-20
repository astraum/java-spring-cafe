package kr.codesquad.cafe.system;

import kr.codesquad.cafe.system.interceptor.LoginRequiredInterceptor;
import kr.codesquad.cafe.system.interceptor.ReplyAuthenticationInterceptor;
import kr.codesquad.cafe.system.interceptor.UserAuthenticationInterceptor;
import kr.codesquad.cafe.system.interceptor.ArticleAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/join").setViewName("users/form");
        registry.addViewController("/questions/new").setViewName("qna/form");
        registry.addViewController("/badRequest").setViewName("badRequest");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor())
                .addPathPatterns("/questions/**")
                .addPathPatterns("/users/**")
                .addPathPatterns("api/questions/{articleId}/answers");

        registry.addInterceptor(userAuthenticationInterceptor())
                .addPathPatterns("/users/{userId}/form");

        registry.addInterceptor(articleAuthenticationInterceptor())
                .addPathPatterns("/questions/{articleId}/form")
                .addPathPatterns("/questions/{articleId}/update")
                .addPathPatterns("/questions/{articleId}/delete");

        registry.addInterceptor(replyAuthenticationInterceptor())
                .addPathPatterns("/questions/{articleId}/answers/{replyId}/delete");
    }

    @Bean
    public LoginRequiredInterceptor loginRequiredInterceptor() {
        return new LoginRequiredInterceptor();
    }

    @Bean
    public UserAuthenticationInterceptor userAuthenticationInterceptor() {
        return new UserAuthenticationInterceptor();
    }

    @Bean
    public ArticleAuthenticationInterceptor articleAuthenticationInterceptor() {
        return new ArticleAuthenticationInterceptor();
    }

    @Bean
    public ReplyAuthenticationInterceptor replyAuthenticationInterceptor() {
        return new ReplyAuthenticationInterceptor();
    }
}
