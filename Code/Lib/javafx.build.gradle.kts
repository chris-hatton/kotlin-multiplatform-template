
val isMinJava12 : Boolean by extra(JavaVersion.current() >= JavaVersion.VERSION_12)

val javaFxSdkHomeKey = "JAVAFX_SDK_HOME"

val javaFxSdkHome : String? by extra(System.getenv(javaFxSdkHomeKey))

if(isMinJava12 && javaFxSdkHome == null) {
    throw Exception("JavaFx target is enabled because Java 12 is present, so JAVAFX_SDK_HOME is expected but not set.")
}

