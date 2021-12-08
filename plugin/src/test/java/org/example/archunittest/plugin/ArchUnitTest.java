package org.example.archunittest.plugin;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.example.archunittest.platform.PublicApi;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackages;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = {
        ArchUnitTest.PLUGIN_PACKAGE,
        // ArchUnitTest.PLATFORM_PACKAGE //Uncommenting this line makes the test pass
}, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {

    static final String PLATFORM_PACKAGE = "org.example.archunittest.platform";
    static final String PLUGIN_PACKAGE = "org.example.archunittest.plugin";

    @ArchTest
    public static final ArchRule api_usage_rule = ArchRuleDefinition
            .classes()
            .that(
                    resideOutsideOfPackages(PLATFORM_PACKAGE))
            .should().onlyDependOnClassesThat(
                    haveTypeOrArrayComponentType(annotatedWith(PublicApi.class).<JavaClass>forSubType())
                            .or(resideOutsideOfPackages(PLATFORM_PACKAGE)));

    public static DescribedPredicate<JavaClass> haveTypeOrArrayComponentType(final DescribedPredicate<? super JavaClass> componentTypePredicate) {
        return new DescribedPredicate<>("have type or array component type " + componentTypePredicate.getDescription()) {
            @Override
            public boolean apply(JavaClass javaClass) {
                boolean applies = componentTypePredicate.apply(javaClass);
                if (applies || !javaClass.isArray()) {
                    return applies;
                }
                JavaClass componentType = javaClass.getComponentType();
                return this.apply(componentType);
            }
        };
    }
}
