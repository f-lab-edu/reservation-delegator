package org.flab.reservation.utils;

@FunctionalInterface
public interface ThrowableRunnable {

    void run() throws Exception;
}
