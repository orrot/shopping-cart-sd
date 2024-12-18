package com.orrot.store.common.specification;

public record BrokenRule(String reason) {

    @Override
    public String toString() {
        return reason;
    }
}
