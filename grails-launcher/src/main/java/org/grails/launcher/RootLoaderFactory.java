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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RootLoaderFactory {

    RootLoader create(GrailsExecutionContext context) throws MalformedURLException, ClassNotFoundException {
        URL[] urls = generateBuildPath(context.getBuildDependencies());
        final RootLoader rootLoader = new RootLoader(urls, ClassLoader.getSystemClassLoader());

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
            Class cls = rootLoader.loadClass("org.springframework.util.Log4jConfigurer");
            invokeStaticMethod(cls, "initLogging", new Object[]{"classpath:grails-maven/log4j.properties"});
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

    /**
     * Invokes the named method on a target object using reflection. The method signature is determined by the classes of each argument.
     *
     * @param target The object to call the method on.
     * @param name The name of the method to call.
     * @param args The arguments to pass to the method (may be an empty array).
     * @return The value returned by the method.
     */
    private static Object invokeStaticMethod(Class target, String name, Object[] args) {
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }

        try {
            return target.getMethod(name, argTypes).invoke(target, args);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}


