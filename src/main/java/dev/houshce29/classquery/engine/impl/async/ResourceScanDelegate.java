package dev.houshce29.classquery.engine.impl.async;

import dev.houshce29.classquery.engine.EngineFactory;
import dev.houshce29.classquery.engine.impl.Extractor;

import java.net.URL;
import java.util.Collections;
import java.util.Set;

/**
 * Delegate for scanning classes within a resource.
 */
public class ResourceScanDelegate extends Delegate {
    private final String javaPackage;
    private final URL resource;

    ResourceScanDelegate(DelegateContainerManager containerManager, String javaPackage, URL resource) {
        super(containerManager);
        this.javaPackage = javaPackage;
        this.resource = resource;
    }

    @Override
    public Set<Class> execute() {
        for (Extractor extractor : EngineFactory.extractors()) {
            if (extractor.canExtract(resource)) {
                return extractor.extract(resource, javaPackage);
            }
        }
        return Collections.emptySet();
    }
}
