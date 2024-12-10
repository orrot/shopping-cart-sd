package com.orrot.store.config.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareShoppingCart implements AuditorAware<String> {

    public static final String DEFAULT_USER = "default_user";

    @Override
    public Optional<String> getCurrentAuditor() {
        // Try to get current user here, for the moment just put any name
        return Optional.of(DEFAULT_USER);
    }
}
