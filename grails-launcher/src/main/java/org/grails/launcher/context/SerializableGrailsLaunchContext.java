/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grails.launcher.context;

import org.grails.launcher.version.GrailsVersion;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class SerializableGrailsLaunchContext implements Serializable, GrailsLaunchContext {

    private GrailsVersion grailsVersion;

    private List<File> compileDependencies;
    private List<File> runtimeDependencies;
    private List<File> buildDependencies;
    private List<File> providedDependencies;
    private List<File> testDependencies;

    private File grailsHome;
    private File grailsWorkDir;
    private File projectWorkDir;
    private File classesDir;
    private File testClassesDir;
    private File testReportsDir;
    private File resourcesDir;
    private File projectPluginsDir;
    private File globalPluginsDir;
    private File baseDir;

    private String scriptName;
    private String env;
    private String args;

    boolean plainOutput;
    boolean dependenciesExternallyConfigured;

    public SerializableGrailsLaunchContext(GrailsVersion grailsVersion) {
        this.grailsVersion = grailsVersion;
    }

    @Override
    public GrailsVersion getGrailsVersion() {
        return grailsVersion;
    }

    @Override
    public File getGrailsHome() {
        return grailsHome;
    }

    @Override
    public void setGrailsHome(File grailsHome) {
        this.grailsHome = grailsHome;
    }

    @Override
    public String getScriptName() {
        return scriptName;
    }

    @Override
    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    @Override
    public String getEnv() {
        return env;
    }

    @Override
    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    public String getArgs() {
        return args;
    }

    @Override
    public void setArgs(String args) {
        this.args = args;
    }

    @Override
    public File getBaseDir() {
        return baseDir;
    }

    @Override
    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public List<File> getCompileDependencies() {
        return compileDependencies;
    }

    @Override
    public void setCompileDependencies(List<File> compileDependencies) {
        this.compileDependencies = compileDependencies;
    }

    @Override
    public List<File> getRuntimeDependencies() {
        return runtimeDependencies;
    }

    @Override
    public void setRuntimeDependencies(List<File> runtimeDependencies) {
        this.runtimeDependencies = runtimeDependencies;
    }

    @Override
    public List<File> getBuildDependencies() {
        return buildDependencies;
    }

    @Override
    public void setBuildDependencies(List<File> buildDependencies) {
        this.buildDependencies = buildDependencies;
    }

    @Override
    public List<File> getProvidedDependencies() {
        return providedDependencies;
    }

    @Override
    public void setProvidedDependencies(List<File> providedDependencies) {
        this.providedDependencies = providedDependencies;
    }

    @Override
    public List<File> getTestDependencies() {
        return testDependencies;
    }

    @Override
    public void setTestDependencies(List<File> testDependencies) {
        this.testDependencies = testDependencies;
    }

    @Override
    public File getGrailsWorkDir() {
        return grailsWorkDir;
    }

    @Override
    public void setGrailsWorkDir(File grailsWorkDir) {
        this.grailsWorkDir = grailsWorkDir;
    }

    @Override
    public File getProjectWorkDir() {
        return projectWorkDir;
    }

    @Override
    public void setProjectWorkDir(File projectWorkDir) {
        this.projectWorkDir = projectWorkDir;
    }

    @Override
    public File getClassesDir() {
        return classesDir;
    }

    @Override
    public void setClassesDir(File classesDir) {
        this.classesDir = classesDir;
    }

    @Override
    public File getTestClassesDir() {
        return testClassesDir;
    }

    @Override
    public void setTestClassesDir(File testClassesDir) {
        this.testClassesDir = testClassesDir;
    }

    @Override
    public File getTestReportsDir() {
        return testReportsDir;
    }

    @Override
    public void setTestReportsDir(File testReportsDir) {
        this.testReportsDir = testReportsDir;
    }

    @Override
    public File getResourcesDir() {
        return resourcesDir;
    }

    @Override
    public void setResourcesDir(File resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    @Override
    public File getProjectPluginsDir() {
        return projectPluginsDir;
    }

    @Override
    public void setProjectPluginsDir(File projectPluginsDir) {
        this.projectPluginsDir = projectPluginsDir;
    }

    @Override
    public File getGlobalPluginsDir() {
        return globalPluginsDir;
    }

    @Override
    public void setGlobalPluginsDir(File globalPluginsDir) {
        this.globalPluginsDir = globalPluginsDir;
    }

    @Override
    public boolean isPlainOutput() {
        return plainOutput;
    }

    @Override
    public void setPlainOutput(boolean plainOutput) {
        this.plainOutput = plainOutput;
    }

    @Override
    public boolean isDependenciesExternallyConfigured() {
        return dependenciesExternallyConfigured;
    }

    @Override
    public void setDependenciesExternallyConfigured(boolean dependenciesExternallyConfigured) {
        this.dependenciesExternallyConfigured = dependenciesExternallyConfigured;
    }
}
