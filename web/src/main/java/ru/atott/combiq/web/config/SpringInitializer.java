package ru.atott.combiq.web.config;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.atott.combiq.web.aop.CommonViewAttributesInjector;

import javax.servlet.Filter;
import java.util.Properties;

public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {
                MvcConfiguration.class,
                AppConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/*" };
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        return new Filter[] {
                characterEncodingFilter
        };
    }

    @Configuration
    @ComponentScan(basePackages = "ru.atott.combiq.web")
    @ImportResource({
            "classpath:ru/atott/combiq/dao/dao-context.xml",
            "classpath:ru/atott/combiq/service/service-context.xml",
            "classpath:conf-context.xml"
    })
    public static class AppConfiguration {
        @Bean
        public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
            DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
            creator.setProxyTargetClass(true);
            return creator;
        }
    }

    @Configuration
    @EnableWebMvc
    public static class MvcConfiguration extends WebMvcConfigurerAdapter {
        @Autowired
        private CommonViewAttributesInjector commonViewAttributesInjector;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry
                    .addInterceptor(commonViewAttributesInjector);
        }

        @Bean
        public ViewResolver getViewResolver() {
            FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
            resolver.setCache(true);
            resolver.setSuffix(".ftl");
            resolver.setContentType("text/html;charset=UTF-8");
            return resolver;
        }

        @Bean
        public FreeMarkerConfigurer getFreeMarkerConfigurer() {
            FreeMarkerConfigurer result = new FreeMarkerConfigurer();
            result.setDefaultEncoding("UTF-8");
            result.setTemplateLoaderPath("/WEB-INF/view/");
            Properties properties = new Properties();
            properties.put("url_escaping_charset", "UTF-8");
            result.setFreemarkerSettings(properties);
            return result;
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                    .addResourceHandler("/static/**").addResourceLocations("/static/");
        }
    }
}
