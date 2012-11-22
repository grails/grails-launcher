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

import org.grails.launcher.context.GrailsLaunchContext;

import java.io.*;

public class Main {

    private final String location;

    public Main(String location) {
        this.location = location;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Expecting one argument; the location of a serialized " + GrailsLaunchContext.class.getName());
            System.exit(1);
        }

        try {
            System.exit(new Main(args[0]).run());
        } catch (Exception e) {
            System.err.println("Fatal error forking Grails JVM: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public int run() throws Exception {
        File serialisedContextFile = new File(location);

        FileInputStream fileInputStream = new FileInputStream(serialisedContextFile);
        GrailsLaunchContext launchContext;
        try {
            launchContext = hydrate(fileInputStream);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException ignore) {

            }
        }

        InProcessGrailsLauncher grailsLauncher = new InProcessGrailsLauncher();
        return grailsLauncher.launch(launchContext);
    }

    private GrailsLaunchContext hydrate(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (GrailsLaunchContext) objectInputStream.readObject();
    }

}
