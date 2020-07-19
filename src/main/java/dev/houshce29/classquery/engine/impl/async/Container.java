package dev.houshce29.classquery.engine.impl.async;

/**
 * Platform on which a delegate can be ran.
 */
public interface Container extends AwaitCapable {

    /**
     * Executes a delegate.
     * @param delegate The delegate to run.
     */
    void execute(Task delegate);
}
