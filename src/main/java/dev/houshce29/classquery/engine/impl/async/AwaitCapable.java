package dev.houshce29.classquery.engine.impl.async;

/**
 * Simple interface for anything that can be waited for.
 */
public interface AwaitCapable {

    /**
     * Awaits for this object to complete whatever it is running.
     */
    void await();
}
