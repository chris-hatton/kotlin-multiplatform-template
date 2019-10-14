
val isMinJava12 : Boolean by extra(JavaVersion.current() >= JavaVersion.VERSION_12)

val javaFxSdkHome : String by extra

if(isMinJava12 && javaFxSdkHome.isBlank()) {
    throw Exception("JavaFx target is enabled because Java 12 is present, so javaFxSdkHome property is expected but not set.")
}

