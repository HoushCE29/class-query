package dev.houshce29.classquery.engine.impl.async;

/**
 * Platform that runs the delegate in a new thread.
 */
public class AsyncContainer implements Container {
    private final ThreadAllocation allocation;
    private DelegateThread thread;

    public AsyncContainer(ThreadAllocation allocation) {
        this.allocation = allocation;
    }

    @Override
    public void execute(Task delegate) {
        thread = new DelegateThread(delegate, allocation);
        thread.start();
    }

    @Override
    public void await() {
        if (thread != null) {
            waitOnJoin();
        }
    }

    private void waitOnJoin() {
        try {
            thread.join();
        }
        catch (InterruptedException ex) {
            // Swallow
        }
    }

    /**
     * Internal class that extends the standard Thread class
     * to consume the target delegate to run.
     */
    private static final class DelegateThread extends Thread {
        private final Task delegate;
        private final ThreadAllocation allocation;

        private DelegateThread(Task delegate, ThreadAllocation allocation) {
            this.delegate = delegate;
            this.allocation = allocation;
        }

        @Override
        public void run() {
            delegate.run();
            allocation.deallocate();
        }
    }
}
