package com.service.configuration;


import brave.TracingCustomizer;
import brave.propagation.B3Propagation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        // Builder này được Spring AI tự động cấu hình
        return builder.build();
    }

    @Bean
    public TracingCustomizer tracingCustomizer() {
        return builder -> builder.traceId128Bit(false);
    }

    @Bean
    public TracingCustomizer b3PropagationTracingCustomizer() {
        return builder -> builder
                .propagationFactory(B3Propagation.newFactoryBuilder().injectFormat(B3Propagation.Format.MULTI).build());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply to all endpoints
                        .allowedOrigins("http://localhost:5173") // 👈 allow your React app URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow cookies/auth headers if needed
            }
        };
    }
}
