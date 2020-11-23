package com.tianhengyun.common.tang4jbase.utils;

import java.util.Objects;

/**
 * Key-Value值Consumer
 * @author fjy
 *
 */

@FunctionalInterface
public interface KeyValueConsumer<T, K, V> {

    void accept(T t, K k, V v);

    default KeyValueConsumer<T, K, V> andThen(KeyValueConsumer<? super T, ? super K, ? super V> after) {
        Objects.requireNonNull(after);

        return (t, k, v) -> {
            accept(t, k, v);
            after.accept(t, k, v);
        };
    }
}