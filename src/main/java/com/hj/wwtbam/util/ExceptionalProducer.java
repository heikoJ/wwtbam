package com.hj.wwtbam.util;

/**
 * Created by heiko on 06.10.15.
 */
@FunctionalInterface
public interface ExceptionalProducer<T,E extends Exception> {

    T produce() throws E;
}
