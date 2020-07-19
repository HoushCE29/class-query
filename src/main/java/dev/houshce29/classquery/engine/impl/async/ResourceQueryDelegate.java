package dev.houshce29.classquery.engine.impl.async;

import dev.houshce29.classquery.ClassQuery;

import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Delegate for filtering scanned resources' classes.
 */
public class ResourceQueryDelegate extends Delegate {
    private final String javaPackage;
    private final URL resource;
    private final ClassQuery query;

    ResourceQueryDelegate(DelegateContainerManager containerManager, String javaPackage, URL resource, ClassQuery query) {
        super(containerManager);
        this.javaPackage = javaPackage;
        this.resource = resource;
        this.query = query;
    }

    @Override
    public Set<Class> execute() {
        Delegate scan = new ResourceScanDelegate(getContainerManager(), javaPackage, resource);
        addChild(scan);
        getContainerManager().submit(scan);
        awaitChildren();
        return scan.getResult().stream()
                .filter(query.getSelect())
                .filter(query.getFilters())
                .collect(Collectors.toSet());
    }
}
