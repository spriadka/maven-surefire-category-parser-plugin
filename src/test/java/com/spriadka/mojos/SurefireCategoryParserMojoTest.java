package com.spriadka.mojos;

import com.spriadka.mojos.mojos.MojoConstants;
import com.spriadka.mojos.mojos.SurefireCategoryParserMojo;
import org.junit.Assert;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.eclipse.aether.DefaultRepositorySystemSession;

import java.io.File;

public class SurefireCategoryParserMojoTest extends AbstractMojoTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testShouldInitialize() throws Exception {
        File pom = getTestFile("src/test/resources/mojo-initialization/mojo-initialization.xml");
        assertNotNull(pom);
        Assert.assertTrue(pom.exists());
        SurefireCategoryParserMojo surefireCategoryParserMojo = new SurefireCategoryParserMojo();
        configureMojo(surefireCategoryParserMojo, extractPluginConfiguration("surefire-category-parser-mojo", pom));
        Assert.assertNotNull(surefireCategoryParserMojo);
    }

    public void testShouldUseConfiguredValues() throws Exception {
        File pom = getTestFile("src/test/resources/mojo-configuration/mojo-configuration.xml");
        assertNotNull(pom);
        Assert.assertTrue(pom.exists());
        SurefireCategoryParserMojo surefireCategoryParserMojo = new SurefireCategoryParserMojo();
        configureMojo(surefireCategoryParserMojo, extractPluginConfiguration("surefire-category-parser-mojo", pom));
        Assert.assertNotNull("Mojo's include category parameter should be configured", surefireCategoryParserMojo.getIncludeTestCategory());
        Assert.assertEquals("Mojo's include category parameter should be equal to the one specified in pom", "include", surefireCategoryParserMojo.getIncludeTestCategory());
        Assert.assertNotNull("Mojo's exclude category parameter should be configured", surefireCategoryParserMojo.getExcludeTestCategory());
        Assert.assertEquals("Mojo's exclude category parameter should be equal to the one specified in pom", "exclude", surefireCategoryParserMojo.getExcludeTestCategory());
        Assert.assertNotNull("Mojo's aggregated include property parameter should be configured", surefireCategoryParserMojo.getAggregatedIncludeProperty());
        Assert.assertEquals("Mojo's aggregated include property should be equal to the one specified in pom", "aggregated-include",
                surefireCategoryParserMojo.getAggregatedIncludeProperty());
        Assert.assertNotNull("Mojo's aggregated exclude property parameter should be configured", surefireCategoryParserMojo.getExcludeTestCategory());
        Assert.assertEquals("Mojo's aggregated exclude property should be equal to the one specified in pom", "aggregated-exclude",
                surefireCategoryParserMojo.getAggregateExcludeProperty());
    }

    public void testShouldUseDefaultValuesIfNotConfigured() throws Exception {
        File pom = getTestFile("src/test/resources/mojo-default-configuration/mojo-default-configuration.xml");
        assertNotNull(pom);
        Assert.assertTrue(pom.exists());
        SurefireCategoryParserMojo surefireCategoryParserMojo = (SurefireCategoryParserMojo) lookupConfiguredMojo("parse-categories", pom);
        Assert.assertEquals("Mojo should have default value set for include test category property",
                MojoConstants.DEFAULT_INCLUDE_CATEGORY, surefireCategoryParserMojo.getIncludeTestCategory());
        Assert.assertEquals("Mojo should have default value set for exclude test category property",
                MojoConstants.DEFAULT_EXCLUDE_CATEGORY, surefireCategoryParserMojo.getExcludeTestCategory());
        Assert.assertEquals("Mojo should have default value set for aggregate include property",
                MojoConstants.DEFAULT_AGGREGATED_INCLUDE_PROPERTY, surefireCategoryParserMojo.getAggregatedIncludeProperty());
        Assert.assertEquals("Mojo should have default value set for aggregate exclude property",
                MojoConstants.DEFAULT_AGGREGATED_EXCLUDE_PROPERTY, surefireCategoryParserMojo.getAggregateExcludeProperty());
    }

    private Mojo lookupConfiguredMojo(String goal, File pom) throws Exception
    {
        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setBaseDirectory(pom.getParentFile());
        ProjectBuildingRequest configuration = request.getProjectBuildingRequest();
        configuration.setRepositorySession(new DefaultRepositorySystemSession());
        MavenProject project = lookup(ProjectBuilder.class).build(pom, configuration).getProject();
        return super.lookupConfiguredMojo(project, goal);
    }
}
