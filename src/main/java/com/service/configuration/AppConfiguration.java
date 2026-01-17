package com.service.configuration;


import brave.TracingCustomizer;
import brave.propagation.B3Propagation;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
