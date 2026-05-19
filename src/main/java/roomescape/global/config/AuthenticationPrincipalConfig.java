package roomescape.global.config;

import java.util.List;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import roomescape.global.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import roomescape.global.LoginMemberArgumentResolver;
import roomescape.service.MemberService;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {
    private final MemberService memberService;

    public AuthenticationPrincipalConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver(memberService));
    }
}
