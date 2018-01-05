package com.spriadka.mojos.mojos;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Mojo(name = "parse-categories", defaultPhase = LifecyclePhase.INITIALIZE)
public class SurefireCategoryParserMojo extends AbstractMojo {

    @Parameter(defaultValue = MojoConstants.DEFAULT_INCLUDE_CATEGORY)
    private String includeTestCategory;

    @Parameter(defaultValue = MojoConstants.DEFAULT_EXCLUDE_CATEGORY)
    private String excludeTestCategory;

    @Parameter(defaultValue = MojoConstants.DEFAULT_AGGREGATED_INCLUDE_PROPERTY)
    private String aggregatedIncludeProperty;

    @Parameter(defaultValue = MojoConstants.DEFAULT_AGGREGATED_EXCLUDE_PROPERTY)
    private String aggregateExcludeProperty;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    public void execute() throws MojoExecutionException, MojoFailureException {
        Set<String> excludedCategories = new HashSet<>();
        Set<String> includedCategories = new HashSet<>();
        mavenProject.getActiveProfiles()
                .stream()
                .forEach(activeProfile -> {
                    Properties profileProperties = activeProfile.getProperties();
                    includedCategories.add(profileProperties.getProperty(includeTestCategory));
                    excludedCategories.add(profileProperties.getProperty(excludeTestCategory));
                });
        String parsedIncludeCategoryProperty = includedCategories.stream().filter(Objects::nonNull).collect(Collectors.joining(","));
        String parsedExcludedCategoryProperty = excludedCategories.stream().filter(Objects::nonNull).collect(Collectors.joining(","));
        mavenProject.getProperties().setProperty(aggregatedIncludeProperty, parsedIncludeCategoryProperty);
        mavenProject.getProperties().setProperty(aggregateExcludeProperty, parsedExcludedCategoryProperty);
        getLog().info(String.format("Included categories: [%s]", parsedIncludeCategoryProperty));
        getLog().info(String.format("Excluded categories: [%s]", parsedExcludedCategoryProperty));
    }

    public String getIncludeTestCategory() {
        return includeTestCategory;
    }

    public String getAggregatedIncludeProperty() {
        return aggregatedIncludeProperty;
    }

    public String getAggregateExcludeProperty() {
        return aggregateExcludeProperty;
    }

    public String getExcludeTestCategory() {

        return excludeTestCategory;
    }
}
