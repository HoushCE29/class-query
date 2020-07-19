package dev.houshce29.classquery.engine.impl.async;

/**
 * Service shared across some multi-threaded context meant to
 * manage thread routing for delegates.
 */
public class DelegateContainerManager {
    private final ThreadAllocation threadAllocation;

    public DelegateContainerManager(int maxThreadCount) {
        this.threadAllocation = new ThreadAllocation(maxThreadCount);
    }

    /**
     * Executes the given delegate in some container.
     * @param delegate Delegate to be executed next.
     */
    public void submit(Delegate delegate) {
        Container container;
        // If successful in obtaining a key, this can be run in
        // an async container.
        // NOTE: AllocationKey is synchronized for obtaining and
        //       releasing thread tallies internally, thus no
        //       synchronized block is needed here.
        if (threadAllocation.allocate()) {
            container = new AsyncContainer(threadAllocation);
        }
        else {
            container = new InlineContainer();
        }
        delegate.setSelfWaitReference(container);
        container.execute(delegate);
    }

    /**
     * @return The max number of threads allowed to be used for container instances.
     */
    public int getMaxThreadCount() {
        return threadAllocation.getMaxThreadCount();
    }

    /**
     * @return The current amount of threads being used in container instances.
     */
    public int getUsedThreadCount() {
        return threadAllocation.getUsedThreadCount();
    }
}
