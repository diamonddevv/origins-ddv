package net.diamonddev.ddvorigins.util;

public interface TriFunction<A, B, C, R> {
    R accept(A a, B b, C c);
}
