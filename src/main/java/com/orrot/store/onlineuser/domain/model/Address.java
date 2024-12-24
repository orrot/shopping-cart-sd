package com.orrot.store.onlineuser.domain.model;

import jakarta.validation.constraints.NotNull;

public record Address(
        @NotNull(message = "Line 1 of the address is mandatory") String line1,
        String line2,
        @NotNull(message = "City of the address is mandatory") String city,
        @NotNull(message = "Zip Code is mandatory") String zipCode) {
}
