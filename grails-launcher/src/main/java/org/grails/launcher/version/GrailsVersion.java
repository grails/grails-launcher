/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.launcher.version;

/**
 * Exposes build related information about a particular version of Grails.
 * <p>
 * Useful for build tools to setup a Grails execution conditionally based on the
 * version.
 */
public class GrailsVersion {
    
    private final String string;
    private final int major;
    private final int minor;
    private final int patch;
    private final String tag;

    public GrailsVersion(String string, int major, int minor, int patch, String tag) {
        this.string = string;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.tag = tag;
    }

    public String getString() {
        return string;
    }
    
    public int getMajor() {
        return major;
    }
    
    public int getMinor() {
        return minor;
    }
    
    public int getPatch() {
        return patch;
    }
    
    public String getTag() {
        return tag;
    }
    

    public boolean is(int major) {
        return this.major == major;
    }
    
    public boolean is(int major, int minor) {
        return this.major == major && this.minor == minor;
    }
    
    public boolean is(int major, int minor, int patch) {
        return this.major == major && this.minor == minor && this.patch == patch;
    }
    
    public boolean is13() {
        return is(1,3);
    }

    /**
     * Grails 1.3.0 and 1.3.1 did not declare grails-bootstrap's dependency on Ivy.
     * <p>
     * If this returns true, an explicit dependency for Ivy must be added (e.g. "org.apache.ivy:ivy:2.1.0")
     */
    public boolean isRequiresExplicitIvyDependency() {
        return is13() && patch < 2;
    }
}