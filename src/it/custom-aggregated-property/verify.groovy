println "Hello World!"

def logFile = new File("${basedir}/build.log")

if (!logFile.exists()) {
    throw new FileNotFoundException("Did not found logfile")
}

def lines = logFile.readLines()

if (!lines.any {
    line -> line.contains("Included categories: [com.spriadka.mojos.Cow,com.spriadka.mojos.Dog]")
}) {
    return false
}

return true