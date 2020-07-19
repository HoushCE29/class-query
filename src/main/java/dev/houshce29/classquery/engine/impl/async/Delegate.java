package dev.houshce29.classquery.engine.impl.async;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Delegate that waterfalls into more tasks.
 */
public abstract class Delegate implements Task {
    private final List<Task> children = new ArrayList<>();
    private final DelegateContainerManager containerManager;
    private AwaitCapable selfWaitReference;
    private Set<Class> result = new HashSet<>();
    private volatile boolean running;

    public Delegate(DelegateContainerManager containerManager) {
        this.containerManager = containerManager;
    }

    /**
     * Helper method to subclasses to persist children delegate instances to.
     * @param child Child delegate to persist.
     */
    protected void addChild(Task child) {
        children.add(child);
    }

    /**
     * Helper method to subclasses to await this delegate's children's completion.
     */
    protected void awaitChildren() {
        for (Task child : children) {
            child.await();
        }
    }

    /**
     * Helper method to subclasses that returns the collective result of the delegate's children.
     * @return Merged result of the delegate's children.
     */
    protected Set<Class> getMergedChildResult() {
        Set<Class> merged = new HashSet<>();
        for (Task child : children) {
            if (child.getResult() != null) {
                merged.addAll(child.getResult());
            }
        }
        return merged;
    }

    /**
     * @return The container manager instance used by this delegate.
     */
    public DelegateContainerManager getContainerManager() {
        return containerManager;
    }

    @Override
    public void await() {
        selfWaitReference.await();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public Set<Class> getResult() {
        return result;
    }

    @Override
    public void run() {
        running = true;
        result = execute();
        running = false;
    }

    /**
     * Executes some logic (which can be thread-blocking) and
     * returns the result of it.
     * @return Set of classes consolidated.
     */
    public abstract Set<Class> execute();

    /**
     * Sets the reference to which this delegate should emit to
     * external sources as being the owning object for invoking
     * `run()` on this object.
     * @param selfWaitReference The await-capable reference that owns the responsibility of
     *                          invoking the run method of this delegate.
     */
    void setSelfWaitReference(AwaitCapable selfWaitReference) {
        this.selfWaitReference = selfWaitReference;
    }
}
