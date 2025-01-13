package com.orrot.store.common.podam;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.RandomDataProviderStrategyImpl;

import java.lang.reflect.Type;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockerFactory {

    private static PodamFactory PODAM_INSTANCE;

    static {
        init();
    }

    private static void init() {
        var configuration = new RandomDataProviderStrategyImpl();
        configuration.setDefaultNumberOfCollectionElements(1);
        configuration.addOrReplaceAttributeStrategy(OneToMany.class, (clazz, list) -> null);
        configuration.addOrReplaceAttributeStrategy(ManyToMany.class, (clazz, list) -> null);
        PODAM_INSTANCE = new PodamFactoryImpl(configuration);
    }

    public static <T> T createDummy(Class<T> clazz, Type... type) {
        return PODAM_INSTANCE.manufacturePojo(clazz, type);
    }
}
