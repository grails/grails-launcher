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

package org.grails.launcher

import junit.framework.Assert
import junit.framework.AssertionFailedError
import org.grails.launcher.context.DelegatingGrailsLaunchContext
import org.grails.launcher.context.SerializableGrailsLaunchContext
import org.grails.launcher.version.GrailsVersionParser
import spock.lang.Specification

/**
 * Test case for {@link ReflectiveGrailsLauncher}.
 */
class DelegatingGrailsLaunchContextSpec extends Specification {
    MockGrailsScriptRunner testRunner
    MockBuildSettings testSettings
    MockGrailsConsole testConsole

    def setup() {
        MockGrailsScriptRunner.testCase = this
        MockBuildSettings.testCase = this
        MockGrailsConsole.testCase = this
    }

    def cleanup() {
        MockGrailsScriptRunner.testCase = null
        MockBuildSettings.testCase = null
        MockGrailsConsole.testCase = null
    }

    DelegatingGrailsLaunchContext context(Map props = [:], String grailsVersionString = "2.0.0") {
        def grailsVersion = new GrailsVersionParser().parse(grailsVersionString)
        def context = DelegatingGrailsLaunchContext.copyOf(new CustomClassLoader(this), new SerializableGrailsLaunchContext(grailsVersion))
        props.each { k, v -> context[k] = v }
        context
    }

    void testSetDepedenciesExternallyConfigured() {
        given:
        def context = context()

        expect:
        !context.dependenciesExternallyConfigured

        when:
        context.dependenciesExternallyConfigured = true

        then:
        context.dependenciesExternallyConfigured
    }

    void testExecution() {
        expect:
        context(scriptName: "Compile").launch() == 0
        testRunner.lastScript["name"] == "Compile"
        testRunner.lastScript["args"] == null
        testRunner.lastScript["env"] == null
        testRunner.interactive

        and:
        context(scriptName: "TestApp", args: "-unit -rerun", env: "test").launch() == 1
        testRunner.lastScript["name"] == "TestApp"
        testRunner.lastScript["args"] == "-unit -rerun"
        testRunner.lastScript["env"] == "test"
    }

    def isInteractive(String args) {
        context(scriptName: "Compile", args: args).launch()
        testRunner.interactive
    }

    void "interactive mode"() {
        expect:
        isInteractive("-as -asd")
        isInteractive("-as -asd --asdf-non-interactive")
        isInteractive("--asdf-non-interactive")
        !isInteractive("-as -asd --non-interactive")
        !isInteractive("-as -asd --non-interactive --asdfa")
        !isInteractive("-as -asd -non-interactive --asdfa")
        !isInteractive("-as -asd -non-interactive")
        !isInteractive("-non-interactive")
        !isInteractive("--non-interactive")
    }

    void testExecutionWithCustomSettings() {
        given:
        def testCompileDeps = [ "1", "2" ]
        def testTestDeps = [ "3", "4", "5" ]
        def testRuntimeDeps = [ "7" ]

        def testBuildDeps = [ "8", "9"]
        def testProvidedDeps = [ "10" ]

        when:
        def context = context()
        context.grailsWorkDir = new File("global-work")
        context.projectWorkDir = new File("target")
        context.classesDir = new File("target/classes")
        context.testClassesDir = new File("target/test-classes")
        context.resourcesDir = new File("target/res")
        context.projectPluginsDir = new File("plugins")
        context.globalPluginsDir = new File("global-work/plugins")
        context.testReportsDir = new File("target/test-reports")
        context.compileDependencies = testCompileDeps
        context.testDependencies = testTestDeps
        context.runtimeDependencies = testRuntimeDeps
        context.providedDependencies = testProvidedDeps
        context.buildDependencies = testBuildDeps

        then:
        testSettings.grailsWorkDir == new File("global-work")
        testSettings.projectWorkDir == new File("target")
        testSettings.classesDir == new File("target/classes")
        testSettings.testClassesDir == new File("target/test-classes")
        testSettings.resourcesDir == new File("target/res")
        testSettings.projectPluginsDir == new File("plugins")
        testSettings.globalPluginsDir == new File("global-work/plugins")
        testSettings.testReportsDir == new File("target/test-reports")
        testSettings.compileDependencies == testCompileDeps
        testSettings.testDependencies == testTestDeps
        testSettings.runtimeDependencies == testRuntimeDeps
        testSettings.providedDependencies == testProvidedDeps
        testSettings.buildDependencies == testBuildDeps
    }
}

class MockGrailsConsole {
    static testCase
    static instance

    static getInstance() {
        new MockGrailsConsole()
    }

    MockGrailsConsole() {
        testCase.testConsole = this
    }

}
class MockGrailsScriptRunner {
    static testCase

    def lastScript
    boolean interactive

    MockGrailsScriptRunner(MockBuildSettings settings) {
        testCase.testRunner = this
        Assert.assertSame testCase.testSettings, settings
    }

    int executeCommand(String scriptName, String args) {
        lastScript = [ name: scriptName, args: args ]
        return 0
    }

    int executeCommand(String scriptName, String args, String env) {
        lastScript = [ name: scriptName, args: args, env: env ]
        return 1
    }


}

class MockBuildSettings {
    static testCase

    File grailsWorkDir
    File projectWorkDir
    File classesDir
    File testClassesDir
    File resourcesDir
    File projectPluginsDir
    File globalPluginsDir
    File testReportsDir
    List compileDependencies
    List testDependencies
    List runtimeDependencies
    List providedDependencies
    List buildDependencies
    URLClassLoader rootLoader
    boolean dependenciesExternallyConfigured = false

    MockBuildSettings() {
        testCase.testSettings = this
    }

    MockBuildSettings(File grailsHome) {
        testCase.testSettings = this
    }

    MockBuildSettings(File grailsHome, File baseDir) {
        testCase.testSettings = this
    }
}

class CustomClassLoader extends URLClassLoader {
    def testCase

    CustomClassLoader(test) {
        super([] as URL[])
        testCase = test
    }

    Class<?> loadClass(String name) {
        if (name == "org.codehaus.groovy.grails.cli.GrailsScriptRunner") {
            return MockGrailsScriptRunner
        }

        if (name == "grails.util.BuildSettings") {
            return MockBuildSettings
        }

        if (name == "grails.build.logging.GrailsConsole") {
            return MockGrailsConsole
        }

        throw new AssertionFailedError("Asked to load unrecognised class: ${name}")
    }
}
