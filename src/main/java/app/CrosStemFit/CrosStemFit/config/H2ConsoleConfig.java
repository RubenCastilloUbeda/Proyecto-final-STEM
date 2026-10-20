package app.CrosStemFit.CrosStemFit.config;

import jakarta.servlet.Servlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleConfig {

    @Bean
    @ConditionalOnProperty(prefix = "spring.h2.console", name = "enabled", havingValue = "true")
    public ServletRegistrationBean<Servlet> h2ConsoleServletRegistration() {
        ServletRegistrationBean<Servlet> registration =
                new ServletRegistrationBean<>(createH2Servlet(), "/h2-console/*");
        registration.setLoadOnStartup(1);
        return registration;
    }

    private Servlet createH2Servlet() {
        String[] candidates = {
                "org.h2.server.web.JakartaWebServlet",
                "org.h2.server.web.WebServlet"
        };

        for (String className : candidates) {
            try {
                Class<?> clazz = Class.forName(className);
                Object instance = clazz.getDeclaredConstructor().newInstance();
                if (instance instanceof Servlet servlet) {
                    return servlet;
                }
            } catch (ReflectiveOperationException ignored) {
                // Try next candidate.
            }
        }

        throw new IllegalStateException("No compatible H2 console servlet was found in classpath");
    }
}
