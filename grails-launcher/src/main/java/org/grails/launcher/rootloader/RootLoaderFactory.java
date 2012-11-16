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

package org.grails.launcher.rootloader;

import org.grails.launcher.context.GrailsLaunchContext;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.grails.launcher.util.ReflectionUtils.invokeStaticMethod;
import static org.grails.launcher.util.ReflectionUtils.invokeStaticMethodWrapException;

public class RootLoaderFactory {

    public RootLoader create(GrailsLaunchContext context) throws MalformedURLException, ClassNotFoundException {
        return create(context, ClassLoader.getSystemClassLoader());
    }

    public RootLoader create(GrailsLaunchContext context, ClassLoader parentLoader) throws MalformedURLException {
        URL[] urls = generateBuildPath(context.getBuildDependencies());
        final RootLoader rootLoader = new RootLoader(urls, parentLoader);

        List<File> loggingBootstrapJars = new ArrayList<File>();
        for (File file : context.getCompileDependencies()) {
            String name = file.getName();
            if (name.contains("slf4j") || name.contains("log4j") || name.contains("spring-core")) {
                loggingBootstrapJars.add(file);
            }
        }

        if (!loggingBootstrapJars.isEmpty()) {
            for (File loggingBootstrapJar : loggingBootstrapJars) {
                rootLoader.addURL(loggingBootstrapJar.toURI().toURL());
            }

            URL log4jFileUrl = getClass().getResource("log4j.properties");
            if (log4jFileUrl != null) {
                try {
                    Class cls = rootLoader.loadClass("org.springframework.util.Log4jConfigurer");
                    invokeStaticMethod(cls, "initLogging", new Object[]{log4jFileUrl.toExternalForm()});
                } catch (Exception ignore) {

                }
            }
        }

        return rootLoader;
    }

    private static URL[] generateBuildPath(List<File> systemDependencies) {
        List<URL> urls = new ArrayList<URL>();
        for (File systemDependency : systemDependencies) {
            try {
                urls.add(systemDependency.toURI().toURL());
            } catch (MalformedURLException e) {
                // ignore
            }
        }
        return urls.toArray(new URL[urls.size()]);
    }

}


