package fun.haolo.bigLandlord.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry//项目中的所有接口都支持跨域
                .addMapping("/**")
                //所有地址都可以访问，也可以配置具体地址
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                //"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 跨域允许时间
                .maxAge(3600);
    }
}