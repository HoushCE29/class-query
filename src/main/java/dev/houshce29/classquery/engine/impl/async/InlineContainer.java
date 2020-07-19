package dev.houshce29.classquery.engine.impl.async;

/**
 * Platform that runs in its parent's thread.
 */
public class InlineContainer implements Container {

    @Override
    public void execute(Task delegate) {
        delegate.run();
    }

    @Override
    public void await() {
        // Do nothing, this should be ran in the same thread as the caller.
        // By the time this would have been invoked, the execution will already be complete.
        // Calling this from some _upstream_ thread may cause unintended behavior.
    }
}
