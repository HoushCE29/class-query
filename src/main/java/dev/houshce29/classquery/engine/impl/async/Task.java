package dev.houshce29.classquery.engine.impl.async;

import java.util.Set;

/**
 * A runnable task that can be ran in an async or sync manner.
 */
public interface Task extends Runnable, AwaitCapable {

    /**
     * @return The result of the most recent run.
     */
    Set<Class> getResult();

    /**
     * @return `true` if this delegate is currently running.
     */
    boolean isRunning();
}
