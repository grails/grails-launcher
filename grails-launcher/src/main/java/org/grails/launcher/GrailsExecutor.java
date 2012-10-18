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

import org.grails.launcher.rootloader.RootLoader;
import org.grails.launcher.rootloader.RootLoaderFactory;

import java.net.MalformedURLException;

public class GrailsExecutor {

    int execute(GrailsLaunchContext context) throws MalformedURLException, ClassNotFoundException {
        RootLoader rootLoader = new RootLoaderFactory().create(context);
        final GrailsLauncher launcher = new GrailsLauncher(rootLoader, null, context.getBaseDir().getAbsolutePath());
        launcher.setPlainOutput(true);
        launcher.setDependenciesExternallyConfigured(true);
        launcher.setProvidedDependencies(context.getProvidedDependencies());
        launcher.setCompileDependencies(context.getCompileDependencies());
        launcher.setTestDependencies(context.getTestDependencies());
        launcher.setRuntimeDependencies(context.getRuntimeDependencies());
        launcher.setGrailsWorkDir(context.getGrailsWorkDir());
        launcher.setProjectWorkDir(context.getProjectWorkDir());
        launcher.setClassesDir(context.getClassesDir());
        launcher.setTestClassesDir(context.getTestClassesDir());
        launcher.setResourcesDir(context.getResourcesDir());
        launcher.setProjectPluginsDir(context.getProjectPluginsDir());
        launcher.setBuildDependencies(context.getBuildDependencies());
        return launcher.launch(context.getScriptName(), context.getArgs(), context.getEnv());
    }

}
