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

public class GrailsVersionParser {

    public GrailsVersion parse(String string) {
        int major;
        int minor;
        int patch;
        String tag;

        String[] components = string.split("\\.", 4);

        major = components.length > 0 ? asInt(components[0]) : 0;

        if (containsTag(components[1])) {
            String[] parts = components[1].split("-", 2);
            minor = asInt(parts[0]);
            patch = 0;
            tag = parts[1];
        } else if (isTag(components[1])) {
            minor = 0;
            patch = 0;
            tag = components[1];
        }  else {
            minor = asInt(components[1]);

            if (components.length > 2) {
                if (containsTag(components[2])) {
                    String[] parts = components[2].split("-", 2);
                    patch = asInt(parts[0]);
                    tag = parts[1];
                } else if (isTag(components[2])) {
                    patch = 0;
                    tag = components[2];
                } else {
                    patch = asInt(components[2]);

                    if (components.length > 3) {
                        tag = components[3];
                    } else {
                        tag = null;
                    }
                }
            } else {
                patch = 0;
                tag = null;
            }
        }

        return new GrailsVersion(string, major, minor, patch, tag);
    }

    private int asInt(String value) throws NumberFormatException {
        return asInteger(value);
    }

    private Integer asInteger(String value) throws NumberFormatException {
        return Integer.valueOf(value);
    }

    private boolean isInteger(String value) {
        try {
            asInteger(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isTag(String component) {
        return !component.matches("\\d+");
    }

    private boolean containsTag(String component) {
        return component.matches("\\d+-.*");
    }

}

