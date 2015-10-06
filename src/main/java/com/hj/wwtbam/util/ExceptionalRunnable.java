package com.hj.wwtbam.util;

/**
 * Created by heiko on 06.10.15.
 */
@FunctionalInterface
public interface ExceptionalRunnable< E extends Exception> {
    void run() throws E;
}