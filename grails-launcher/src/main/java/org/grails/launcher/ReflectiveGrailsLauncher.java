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

import org.grails.launcher.context.DelegatingGrailsLaunchContext;
import org.grails.launcher.context.GrailsLaunchContext;

public class ReflectiveGrailsLauncher implements GrailsLauncher {

    private final ClassLoader classLoader;

    public ReflectiveGrailsLauncher(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public int launch(GrailsLaunchContext context) throws Exception {
        return DelegatingGrailsLaunchContext.copyOf(classLoader, context).launch();
    }
}
