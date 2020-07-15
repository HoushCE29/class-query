package dev.houshce29.classquery;

/**
 * Top-level query model. This is not executable.
 */
public class Select {
    private final ClassQuery query;

    Select(ClassQuery query) {
        this.query = query;
    }

    /**
     * Applies a "from" definition.
     * @param javaPackage Initial Java package to search for classes in.
     * @param morePackages Zero or more additional Java packages to search.
     * @return "From" model.
     */
    public From from(String javaPackage, String... morePackages) {
        query.from(javaPackage);
        query.from(morePackages);
        return new From(query);
    }
}
