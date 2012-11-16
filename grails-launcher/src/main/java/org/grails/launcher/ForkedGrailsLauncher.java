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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Allows for Grails commands to be executed in a Forked VM
 *
 * @author Graeme Rocher
 * @since 2.1
 */
public class ForkedGrailsLauncher implements GrailsLauncher {

    private int maxMemory = 1024;
    private int minMemory = 512;
    private int maxPerm = 256;
    private boolean debug;

    public void setMaxMemory(int maxMemory) {
        this.maxMemory = maxMemory;
    }

    public void setMinMemory(int minMemory) {
        this.minMemory = minMemory;
    }

    public void setMaxPerm(int maxPerm) {
        this.maxPerm = maxPerm;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int launch(GrailsLaunchContext launchContext) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        StringBuilder cp = new StringBuilder();
        for (File file : launchContext.getBuildDependencies()) {
            cp.append(file).append(File.pathSeparator);
        }


        FileOutputStream fos = null;
        File tempFile;
        try {
            String baseName = launchContext.getBaseDir().getCanonicalFile().getName();
            tempFile = File.createTempFile(baseName, "grails-execution-context");
            tempFile.deleteOnExit();

            fos = new FileOutputStream(tempFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(launchContext);

            List<String> cmd = new ArrayList<String>(Arrays.asList("java", "-Xmx" + maxMemory + "M", "-Xms" + minMemory + "M", "-XX:MaxPermSize=" + maxPerm + "m", "-cp", cp.toString()));
            if (debug) {
                cmd.addAll(Arrays.asList("-Xdebug", "-Xnoagent", "-Dgrails.full.stacktrace=true", "-Djava.compiler=NONE", "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"));
            }
            cmd.add(getClass().getName());
            cmd.add(tempFile.getCanonicalPath());
            processBuilder
                    .directory(launchContext.getBaseDir())
                    .redirectErrorStream(true)
                    .command(cmd);

            Process process = processBuilder.start();

            new Thread(new TextDumper(process.getInputStream(), System.out)).start();
            new Thread(new TextDumper(process.getErrorStream(), System.err)).start();

            return process.waitFor();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Fatal error forking Grails JVM: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Fatal error forking Grails JVM: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Fatal error forking Grails JVM: " + e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }


    private static class TextDumper implements Runnable {
        InputStream in;
        Appendable app;

        public TextDumper(InputStream in, Appendable app) {
            this.in = in;
            this.app = app;
        }

        public void run() {
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String next;
            try {
                while ((next = br.readLine()) != null) {
                    if (app != null) {
                        app.append(next);
                        app.append("\n");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("exception while reading process stream", e);
            }
        }
    }
}
