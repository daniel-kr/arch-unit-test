package org.example.archunittest.plugin;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchRules;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import org.example.archunittest.platform.UsageRules;
import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = {
        ArchUnitTest.PLUGIN_PACKAGE,
        // "org.example.archunittest.platform" //Uncommenting this line makes the test pass
}, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {

    static final String PLUGIN_PACKAGE = "org.example.archunittest.plugin";

    @ArchTest
    public static final ArchRules usageRules = ArchRules.in(UsageRules.class);
}
