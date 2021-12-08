package org.example.archunittest.platform;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackages;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;

public class UsageRules {

    static final String PLATFORM_PACKAGE = "org.example.archunittest.platform";

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
