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
public class ForkedGrailsLauncher {


    private GrailsExecutionContext executionContext;
    private int maxMemory = 1024;
    private int minMemory = 512;
    private int maxPerm = 256;
    private boolean debug;

    public ForkedGrailsLauncher(GrailsExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

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

    public void fork() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        StringBuilder cp = new StringBuilder();
        for (File file : executionContext.getBuildDependencies()) {
            cp.append(file).append(File.pathSeparator);
        }


        FileOutputStream fos = null;
        File tempFile = null;
        try {
            String baseName = executionContext.getBaseDir().getCanonicalFile().getName();
            tempFile = File.createTempFile(baseName, "grails-execution-context");
            tempFile.deleteOnExit();

            fos = new FileOutputStream(tempFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(executionContext);


            List<String> cmd = new ArrayList<String>(Arrays.asList("java", "-Xmx" + maxMemory + "M", "-Xms" + minMemory + "M", "-XX:MaxPermSize=" + maxPerm + "m", "-Dgrails.build.execution.context=" + tempFile.getCanonicalPath(), "-cp", cp.toString()));
            if (debug) {
                cmd.addAll(Arrays.asList("-Xdebug", "-Xnoagent", "-Dgrails.full.stacktrace=true", "-Djava.compiler=NONE", "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"));
            }
            cmd.add(getClass().getName());
            processBuilder
                    .directory(executionContext.getBaseDir())
                    .redirectErrorStream(true)
                    .command(cmd);

            Process process = processBuilder.start();

            new Thread(new TextDumper(process.getInputStream(), System.out)).start();
            new Thread(new TextDumper(process.getErrorStream(), System.err)).start();

            int result = process.waitFor();
            if (result == 1) {

                throw new RuntimeException("Forked Grails VM exited with error");
            }
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

    public static void main(String[] args) {
        String location = System.getProperty("grails.build.execution.context");
        if (location != null) {
            File f = new File(location);
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                GrailsExecutionContext ec = (GrailsExecutionContext) ois.readObject();

                System.setProperty("grails.console.enable.terminal", "false");
                System.setProperty("grails.console.enable.interactive", "false");

                System.exit(new GrailsExecutor().execute(ec));

            } catch (FileNotFoundException e) {
                fatalError(e);
            } catch (ClassNotFoundException e) {
                fatalError(e);
            } catch (IOException e) {
                fatalError(e);
            } catch (Throwable e) {
                fatalError(e);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
        } else {
            System.exit(1);
        }
    }

    protected static void fatalError(Throwable e) {
        System.err.println("Fatal error forking Grails JVM: " + e.getMessage());
        e.printStackTrace(System.err);
        System.exit(1);
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
