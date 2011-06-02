## grails-launcher 

A thin jar with **no dependencies** for launching Grails (with an isolated classpath) programatically (e.g from Maven or Gradle) in the same JVM.

    import grails.launcher.GrailsLauncher
    import grails.launcher.RootLoader

    // Setup the classpath for Grails
    def classpath = []

    grailsJars.each { path ->
        classpath << new URL(path)
    }

    // Create a root class loader
    def classloader = new RootLoader(classpath)

    def launcher = new GrailsLauncher(classloader, null, "/a/grails/project")
    launcher.launch("test-app", "integration some.package.*")