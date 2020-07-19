package dev.houshce29.classquery.engine.impl.async;

import dev.houshce29.classquery.ClassQuery;
import dev.houshce29.classquery.engine.EngineFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;

/**
 * Top-level delegate that opens package resources and queries them accordingly.
 */
public class PackageQueryDelegate extends Delegate {
    private final String javaPackage;
    private final ClassQuery query;

    PackageQueryDelegate(DelegateContainerManager containerManager, String javaPackage, ClassQuery query) {
        super(containerManager);
        this.javaPackage = javaPackage;
        this.query = query;
    }

    @Override
    public Set<Class> execute() {
        Enumeration<URL> packages;
        try {
            packages = EngineFactory.classLoader()
                    .getResources(javaPackage.replaceAll("[.]", "/"));
        }
        catch (IOException ex) {
            return Collections.emptySet();
        }
        while (packages.hasMoreElements()) {
            Delegate resourceQuery = new ResourceQueryDelegate(
                    getContainerManager(), javaPackage, packages.nextElement(), query);
            addChild(resourceQuery);
            getContainerManager().submit(resourceQuery);
        }
        awaitChildren();
        return getMergedChildResult();
    }
}
