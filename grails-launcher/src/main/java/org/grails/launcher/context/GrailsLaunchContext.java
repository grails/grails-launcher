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
import java.util.List;

public interface GrailsLaunchContext {

    GrailsVersion getGrailsVersion();

    File getGrailsHome();

    void setGrailsHome(File grailsHome);

    String getScriptName();

    void setScriptName(String scriptName);

    String getEnv();

    void setEnv(String env);

    String getArgs();

    void setArgs(String args);

    File getBaseDir();

    void setBaseDir(File baseDir);

    List<File> getCompileDependencies();

    void setCompileDependencies(List<File> compileDependencies);

    List<File> getRuntimeDependencies();

    void setRuntimeDependencies(List<File> runtimeDependencies);

    List<File> getBuildDependencies();

    void setBuildDependencies(List<File> buildDependencies);

    List<File> getProvidedDependencies();

    void setProvidedDependencies(List<File> providedDependencies);

    List<File> getTestDependencies();

    void setTestDependencies(List<File> testDependencies);

    File getGrailsWorkDir();

    void setGrailsWorkDir(File grailsWorkDir);

    File getProjectWorkDir();

    void setProjectWorkDir(File projectWorkDir);

    File getClassesDir();

    void setClassesDir(File classesDir);

    File getTestClassesDir();

    void setTestClassesDir(File testClassesDir);

    File getResourcesDir();

    void setResourcesDir(File resourcesDir);

    File getProjectPluginsDir();

    void setProjectPluginsDir(File projectPluginsDir);

    boolean isPlainOutput();

    void setPlainOutput(boolean plainOutput);

    void setDependenciesExternallyConfigured(boolean dependenciesExternallyConfigured);

    boolean isDependenciesExternallyConfigured();

    void setTestReportsDir(File dir);

    File getTestReportsDir();

    void setGlobalPluginsDir(File dir);

    File getGlobalPluginsDir();
}
