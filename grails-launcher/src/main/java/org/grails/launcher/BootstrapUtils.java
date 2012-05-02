package org.grails.launcher;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Methods to help the bootstrapping process
 *
 * @author Graeme Rocher
 * @since 1.0.5
 */
public class BootstrapUtils {
    public static void addLoggingJarsToRootLoaderAndInit(RootLoader rootLoader, List<File> compileDependencies) throws MalformedURLException, ClassNotFoundException {
        List<File> loggingBootstrapJars = new ArrayList<File>();
        for (File file : compileDependencies) {
            String name = file.getName();
            if(name.contains("slf4j") || name.contains("log4j") || name.contains("spring-core")) {
                loggingBootstrapJars.add(file);
            }
        }
        if(!loggingBootstrapJars.isEmpty()) {
            for (File loggingBootstrapJar : loggingBootstrapJars) {
                rootLoader.addURL(loggingBootstrapJar.toURI().toURL());
            }
            Class cls = rootLoader.loadClass("org.springframework.util.Log4jConfigurer");
            invokeStaticMethod(cls, "initLogging", new Object[]{"classpath:grails-maven/log4j.properties"});
        }
    }


    /**
     * Invokes the named method on a target object using reflection.
     * The method signature is determined by the classes of each argument.
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
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
