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
import java.net.URLClassLoader;
import java.util.List;

import static org.grails.launcher.util.ReflectionUtils.invokeMethod;
import static org.grails.launcher.util.ReflectionUtils.invokeMethodWrapException;

public class DelegatingGrailsLaunchContext implements GrailsLaunchContext {

    private ClassLoader classLoader;
    private Object settings;
    private final GrailsVersion grailsVersion;

    String scriptName;
    String env;
    String args;

    public static DelegatingGrailsLaunchContext copyOf(ClassLoader classLoader, GrailsLaunchContext source) {
        DelegatingGrailsLaunchContext context = new DelegatingGrailsLaunchContext(
                source.getGrailsVersion(), classLoader, source.getGrailsHome(), source.getBaseDir()
        );

        context.setScriptName(source.getScriptName());
        context.setArgs(source.getArgs());
        context.setEnv(source.getEnv());

        context.setDependenciesExternallyConfigured(source.isDependenciesExternallyConfigured());
        context.setPlainOutput(source.isPlainOutput());

        context.setProvidedDependencies(source.getProvidedDependencies());
        context.setCompileDependencies(source.getCompileDependencies());
        context.setTestDependencies(source.getTestDependencies());
        context.setRuntimeDependencies(source.getRuntimeDependencies());
        context.setBuildDependencies(source.getBuildDependencies());

        context.setGrailsWorkDir(source.getGrailsWorkDir());
        context.setProjectWorkDir(source.getProjectWorkDir());

        context.setClassesDir(source.getClassesDir());
        context.setTestClassesDir(source.getTestClassesDir());
        context.setTestReportsDir(source.getTestReportsDir());
        context.setResourcesDir(source.getResourcesDir());

        context.setProjectPluginsDir(source.getProjectPluginsDir());
        context.setGlobalPluginsDir(source.getGlobalPluginsDir());

        return context;
    }

    public DelegatingGrailsLaunchContext(GrailsVersion grailsVersion, ClassLoader classLoader, File grailsHome, File baseDir) {
        this.grailsVersion = grailsVersion;
        this.classLoader = classLoader;

        try {
            this.classLoader = classLoader;
            Class<?> clazz = classLoader.loadClass("grails.util.BuildSettings");

            settings = clazz.getConstructor(File.class, File.class).newInstance(grailsHome, baseDir);

            // Initialise the root loader for the BuildSettings.
            invokeMethod(settings, "setRootLoader", new Class[]{URLClassLoader.class}, new Object[]{classLoader});
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public GrailsVersion getGrailsVersion() {
        return grailsVersion;
    }

    @Override
    public File getGrailsHome() {
        return (File) invokeMethodWrapException(settings, "getGrailsHome");
    }

    @Override
    public void setGrailsHome(File grailsHome) {
        invokeMethodWrapException(settings, "setGrailsHome", new Class<?>[]{File.class}, new Object[]{grailsHome});
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
    public File getGrailsWorkDir() {
        return (File) invokeMethodWrapException(settings, "getGrailsWorkDir", new Object[0]);
    }

    @Override
    public void setGrailsWorkDir(File dir) {
        invokeMethodWrapException(settings, "setGrailsWorkDir", new Class<?>[]{File.class}, new Object[]{dir});
    }

    @Override
    public File getProjectWorkDir() {
        return (File) invokeMethodWrapException(settings, "getProjectWorkDir", new Object[0]);
    }

    @Override
    public void setProjectWorkDir(File dir) {
        invokeMethodWrapException(settings, "setProjectWorkDir", new Class[]{File.class}, new Object[]{dir});
    }

    @Override
    public File getClassesDir() {
        return (File) invokeMethodWrapException(settings, "getClassesDir", new Object[0]);
    }

    @Override
    public void setClassesDir(File dir) {
        invokeMethodWrapException(settings, "setClassesDir", new Class[]{File.class}, new Object[]{dir});
    }

    @Override
    public File getTestClassesDir() {
        return (File) invokeMethodWrapException(settings, "getTestClassesDir", new Object[0]);
    }

    @Override
    public void setTestClassesDir(File dir) {
        invokeMethodWrapException(settings, "setTestClassesDir", new Class<?>[]{File.class}, new Object[]{dir});
    }

    @Override
    public File getResourcesDir() {
        return (File) invokeMethodWrapException(settings, "getResourcesDir", new Object[0]);
    }

    @Override
    public void setResourcesDir(File dir) {
        invokeMethodWrapException(settings, "setResourcesDir", new Class[]{File.class}, new Object[]{dir});
    }

    @Override
    public File getProjectPluginsDir() {
        return (File) invokeMethodWrapException(settings, "getProjectPluginsDir", new Object[0]);
    }

    @Override
    public void setProjectPluginsDir(File dir) {
        invokeMethodWrapException(settings, "setProjectPluginsDir", new Class[]{File.class}, new Object[]{dir});
    }

    @Override
    public File getGlobalPluginsDir() {
        return (File) invokeMethodWrapException(settings, "getGlobalPluginsDir", new Object[0]);
    }

    @Override
    public void setGlobalPluginsDir(File dir) {
        invokeMethodWrapException(settings, "setGlobalPluginsDir", new Class[]{File.class}, new Object[]{dir});
    }

    @Override
    public File getTestReportsDir() {
        return (File) invokeMethodWrapException(settings, "getTestReportsDir", new Object[0]);
    }

    @Override
    public void setTestReportsDir(File dir) {
        invokeMethodWrapException(settings, "setTestReportsDir", new Class<?>[]{File.class}, new Object[]{dir});
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List getCompileDependencies() {
        return (List) invokeMethodWrapException(settings, "getCompileDependencies", new Object[0]);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setCompileDependencies(List dependencies) {
        invokeMethodWrapException(settings, "setCompileDependencies", new Class[]{List.class}, new Object[]{dependencies});
    }

    @Override
    public void setDependenciesExternallyConfigured(boolean b) {
        invokeMethodWrapException(settings, "setDependenciesExternallyConfigured", new Class[]{boolean.class}, new Object[]{b});
    }

    @Override
    public boolean isDependenciesExternallyConfigured() {
        return (Boolean) invokeMethodWrapException(settings, "isDependenciesExternallyConfigured");
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List getTestDependencies() {
        return (List) invokeMethodWrapException(settings, "getTestDependencies", new Object[0]);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setTestDependencies(List dependencies) {
        invokeMethodWrapException(settings, "setTestDependencies", new Class[]{List.class}, new Object[]{dependencies});
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List getProvidedDependencies() {
        return (List) invokeMethodWrapException(settings, "getProvidedDependencies", new Object[0]);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setProvidedDependencies(List dependencies) {
        invokeMethodWrapException(settings, "setProvidedDependencies", new Class[]{List.class}, new Object[]{dependencies});
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setBuildDependencies(List dependencies) {
        invokeMethodWrapException(settings, "setBuildDependencies", new Class[]{List.class}, new Object[]{dependencies});
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List getBuildDependencies() {
        return (List) invokeMethodWrapException(settings, "getBuildDependencies", new Object[0]);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List getRuntimeDependencies() {
        return (List) invokeMethodWrapException(settings, "getRuntimeDependencies", new Object[0]);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setRuntimeDependencies(List dependencies) {
        invokeMethodWrapException(settings, "setRuntimeDependencies", new Class[]{List.class}, new Object[]{dependencies});
    }

    @Override
    public File getBaseDir() {
        return (File) invokeMethodWrapException(settings, "getBaseDir", new Object[0]);
    }

    @Override
    public void setBaseDir(File baseDir) {
        invokeMethodWrapException(settings, "setBaseDir", new Class[]{File.class}, new Object[]{baseDir});
    }

    @Override
    public boolean isPlainOutput() {
        try {
            Class<?> clazz = classLoader.loadClass("grails.build.logging.GrailsConsole");
            Object console = invokeMethod(clazz, "getInstance");
            return (Boolean) invokeMethod(console, "isAnsiEnabled");
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void setPlainOutput(boolean isPlain) {
        try {
            Class<?> clazz = classLoader.loadClass("grails.build.logging.GrailsConsole");
            Object console = invokeMethod(clazz, "getInstance");
            invokeMethod(console, "setAnsiEnabled", new Class<?>[]{boolean.class}, new Object[]{!isPlain});
        } catch (Exception e) {
            // do nothing, Grails 1.3.x or lower
        }
    }

    public int launch() throws Exception {
        return new ScriptRunner().run();
    }

    class ScriptRunner {
        int run() throws Exception {
            Object grailsScriptRunner = classLoader.loadClass("org.codehaus.groovy.grails.cli.GrailsScriptRunner").
                    getDeclaredConstructor(new Class[]{settings.getClass()}).
                    newInstance(settings);

            Class[] paramTypes;
            Object[] params;

            if (getEnv() == null) {
                paramTypes = new Class[]{String.class, String.class};
                params = new Object[]{getScriptName(), getArgs()};
            } else {
                paramTypes = new Class[]{String.class, String.class, String.class};
                params = new Object[]{getScriptName(), getArgs(), getEnv()};
            }

            return (Integer) invokeMethod(grailsScriptRunner, "executeCommand", paramTypes, params);
        }
    }
}
