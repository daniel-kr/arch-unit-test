package org.example.archunittest.platform;

import com.tngtech.archunit.ArchConfiguration;
import com.tngtech.archunit.base.Optional;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.resolvers.ClassResolver;
import com.tngtech.archunit.core.importer.resolvers.ClassResolverFromClasspath;

import java.net.URL;

public class CustomClassResolver implements ClassResolver {
    private final ClassFileImporter classFileImporter = new ClassFileImporter();
    private final ClassResolverFromClasspath defaultResolver = new ClassResolverFromClasspath();

    @Override
    public void setClassUriImporter(ClassUriImporter classUriImporter) {
        defaultResolver.setClassUriImporter(classUriImporter);
    }

    @Override
    public Optional<JavaClass> tryResolve(String typeName) {
        if (!typeName.startsWith("org.example.archunittest.platform")) {
            return defaultResolver.tryResolve(typeName);
        }
        try {
            ArchConfiguration.get().setClassResolver(ClassResolverFromClasspath.class);
            URL url = getClass().getResource("/" + typeName.replace(".", "/") + ".class");
            return url != null ? Optional.of(classFileImporter.importUrl(url).get(typeName)) : Optional.empty();
        } finally {
            ArchConfiguration.get().setClassResolver(CustomClassResolver.class);
        }
    }
}