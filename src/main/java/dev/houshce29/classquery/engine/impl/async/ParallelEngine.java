package dev.houshce29.classquery.engine.impl.async;

import dev.houshce29.classquery.ClassQuery;
import dev.houshce29.classquery.engine.Engine;
import dev.houshce29.classquery.internal.Experimental;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Engine that runs delegates in parallel.
 */
@Experimental
public class ParallelEngine implements Engine {
    private final int maxThreads;

    public ParallelEngine(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    @Override
    public Set<Class> go(ClassQuery query) {
        DelegateContainerManager manager = new DelegateContainerManager(maxThreads);
        List<Delegate> managedDelegates = new ArrayList<>();
        for (String javaPackage : query.getPackages()) {
            Delegate packageQuery = new PackageQueryDelegate(manager, javaPackage, query);
            managedDelegates.add(packageQuery);
            manager.submit(packageQuery);
        }
        Set<Class> classes = new HashSet<>();
        for (Delegate delegate : managedDelegates) {
            delegate.await();
            classes.addAll(delegate.getResult());
        }
        return classes;
    }
}
