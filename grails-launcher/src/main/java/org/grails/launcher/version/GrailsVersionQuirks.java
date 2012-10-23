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

package org.grails.launcher.version;

public class GrailsVersionQuirks {

    private final GrailsVersion grailsVersion;

    public GrailsVersionQuirks(String grailsVersion) {
        this(GrailsVersion.parse(grailsVersion));
    }

    public GrailsVersionQuirks(GrailsVersion grailsVersion) {
        this.grailsVersion = grailsVersion;
    }

    /**
     * Grails 1.3.0 and 1.3.1 did not declare grails-bootstrap's dependency on Ivy.
     *
     * If this returns true, an explicit dependency for Ivy must be added (e.g. "org.apache.ivy:ivy:2.1.0")
     */
    public boolean isRequiresExplicitIvyDependency() {
        return grailsVersion.is(1, 3) && grailsVersion.getPatch() < 2;
    }

    public boolean isHasGrailsDependenciesPom() {
        return grailsVersion.is(2);
    }

    public boolean isSupportsProvidedDependencies() {
        return grailsVersion.is(2);
    }

    public boolean isSupportsBuildDependencies() {
        return grailsVersion.is(2);
    }

}
