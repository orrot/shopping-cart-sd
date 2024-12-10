package com.orrot.store.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.ZonedDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "zonedDateTimeProvider")
public class AuditingConfig {

    @Bean("zonedDateTimeProvider")
    public DateTimeProvider provideZonedDateTime() {
        return () -> Optional.of(ZonedDateTime.now());
    }
}



