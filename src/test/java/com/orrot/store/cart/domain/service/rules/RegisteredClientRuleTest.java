package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.common.podam.MockerFactory;
import com.orrot.store.common.specification.BusinessRuleResult;
import com.orrot.store.onlineuser.adapter.output.OnlineClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegisteredClientRuleTest {

    private static final Long DEFAULT_CLIENT_ID = 1L;

    @InjectMocks
    private RegisteredClientRule registeredClientRule;

    @Mock
    private OnlineClientRepository onlineClientRepository;

    @Nested
    @DisplayName("When checking if the user is registered")
    class WhenCheckingIfUserIsRegistered {

        @Test
        @DisplayName("Should return success if the user is not assigned")
        void shouldReturnSuccessIfUserIsNotAssigned() {
            // Given
            Cart cart = MockerFactory.createDummy(Cart.class)
                    .withOnlineClientOwnerId(null);

            // When
            var result = registeredClientRule.isSatisfiedBy(cart);

            // Then
            assertThat(result)
                    .extracting(BusinessRuleResult::isRuleSatisfied,
                            rule -> rule.notifications().size())
                    .as("The rule should be satisfied and have no notifications")
                    .contains(true, 0);

        }

        @Test
        @DisplayName("Should return success if the user is registered")
        void shouldReturnSuccessIfUserIsRegistered() {
            // Given
            Cart cart = MockerFactory.createDummy(Cart.class)
                    .withOnlineClientOwnerId(DEFAULT_CLIENT_ID);

            given(onlineClientRepository.existsById(DEFAULT_CLIENT_ID))
                    .willReturn(true);

            // When
            BusinessRuleResult result = registeredClientRule.isSatisfiedBy(cart);

            // Then
            assertThat(result)
                    .extracting(BusinessRuleResult::isRuleSatisfied,
                            rule -> rule.notifications().size())
                    .as("The rule should be satisfied and have no notifications")
                    .contains(true, 0);
        }

        @Test
        @DisplayName("Should return error if the user is not registered")
        void shouldReturnErrorIfUserIsNotRegistered() {
            // Given
            Cart cart = MockerFactory.createDummy(Cart.class)
                    .withOnlineClientOwnerId(4L);

            // When
            BusinessRuleResult result = registeredClientRule.isSatisfiedBy(cart);

            // Then
            assertThat(result)
                    .extracting(BusinessRuleResult::isRuleSatisfied,
                            rule -> rule.notifications().size(), rule -> rule.notifications().getFirst())
                    .as("The rule result should be unsatisfied and contain the error message")
                    .contains(false, 1, "The User with id '4' must be registered so the cart could be assigned");
        }
    }
}