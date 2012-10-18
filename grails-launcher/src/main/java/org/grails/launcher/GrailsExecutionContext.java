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

package org.grails.launcher;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class GrailsExecutionContext implements Serializable {
    private List<File> compileDependencies;
    private List<File> runtimeDependencies;
    private List<File> buildDependencies;
    private List<File> providedDependencies;
    private List<File> testDependencies;

    private File grailsWorkDir;
    private File projectWorkDir;
    private File classesDir;
    private File testClassesDir;
    private File resourcesDir;
    private File projectPluginsDir;
    private File baseDir;

    private String scriptName;
    private String env;
    private String args;

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public File getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    public List<File> getCompileDependencies() {
        return compileDependencies;
    }

    public void setCompileDependencies(List<File> compileDependencies) {
        this.compileDependencies = compileDependencies;
    }

    public List<File> getRuntimeDependencies() {
        return runtimeDependencies;
    }

    public void setRuntimeDependencies(List<File> runtimeDependencies) {
        this.runtimeDependencies = runtimeDependencies;
    }

    public List<File> getBuildDependencies() {
        return buildDependencies;
    }

    public void setBuildDependencies(List<File> buildDependencies) {
        this.buildDependencies = buildDependencies;
    }

    public List<File> getProvidedDependencies() {
        return providedDependencies;
    }

    public void setProvidedDependencies(List<File> providedDependencies) {
        this.providedDependencies = providedDependencies;
    }

    public List<File> getTestDependencies() {
        return testDependencies;
    }

    public void setTestDependencies(List<File> testDependencies) {
        this.testDependencies = testDependencies;
    }

    public File getGrailsWorkDir() {
        return grailsWorkDir;
    }

    public void setGrailsWorkDir(File grailsWorkDir) {
        this.grailsWorkDir = grailsWorkDir;
    }

    public File getProjectWorkDir() {
        return projectWorkDir;
    }

    public void setProjectWorkDir(File projectWorkDir) {
        this.projectWorkDir = projectWorkDir;
    }

    public File getClassesDir() {
        return classesDir;
    }

    public void setClassesDir(File classesDir) {
        this.classesDir = classesDir;
    }

    public File getTestClassesDir() {
        return testClassesDir;
    }

    public void setTestClassesDir(File testClassesDir) {
        this.testClassesDir = testClassesDir;
    }

    public File getResourcesDir() {
        return resourcesDir;
    }

    public void setResourcesDir(File resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public File getProjectPluginsDir() {
        return projectPluginsDir;
    }

    public void setProjectPluginsDir(File projectPluginsDir) {
        this.projectPluginsDir = projectPluginsDir;
    }
}
