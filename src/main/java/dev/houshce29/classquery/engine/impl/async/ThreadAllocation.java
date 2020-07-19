package dev.houshce29.classquery.engine.impl.async;

/**
 * Allocation stats for container threads.
 * This class is internal to the async package; external exposure
 * can lead to potential misuse and unintended behavior!
 */
final class ThreadAllocation {
    private final int maxThreadCount;
    private int usedThreadCount;

    ThreadAllocation(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        this.usedThreadCount = 0;
    }

    /**
     * Attempts to allocate a spot in the thread allocation counts.
     * @return `true` if a spot was successfully obtained.
     */
    public boolean allocate() {
        return manageAllocation(true);
    }

    /**
     * Attempts to deallocate a spot in the thread allocation counts.
     * @return `true` if a spot was successfully released.
     */
    public boolean deallocate() {
        return manageAllocation(false);
    }

    /**
     * @return The max number of threads available to be allocated.
     */
    public int getMaxThreadCount() {
        return maxThreadCount;
    }

    /**
     * @return The current amount of threads allocated.
     */
    public int getUsedThreadCount() {
        return usedThreadCount;
    }

    private synchronized boolean manageAllocation(boolean obtain) {
        // Try to allocate a spot
        if (obtain) {
            // If available room, increment
            if (usedThreadCount < maxThreadCount) {
                usedThreadCount++;
                return true;
            }
            // No room, return that obtaining didn't occur
            return false;
        }
        // Only deallocate if there are positively allocated counts
        if (usedThreadCount > 0) {
            usedThreadCount--;
            return true;
        }
        // Otherwise, return that releasing didn't occur
        return false;
    }
}
