package com.arc.app.config;

import com.arc.app.entity.base.BaseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing()
public class AuditingConfig {
    @Bean
    public BaseEntity auditorAware() {
        return new BaseEntity();
    }
}
