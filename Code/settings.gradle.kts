
rootProject.name = "Multiplatform-Template"

includeBuild("Client/Android"				) { name = "android" }
includeBuild("Client/Desktop"				) { name = "desktop" }
includeBuild("Client/iOS/SupportingFiles"	) { name = "ios"     }
includeBuild("Server"						) { name = "server"  }

//includeBuild("Server"					) { name = "server"  }
