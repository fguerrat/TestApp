plugins{
    `kotlin-dsl`
}

repositories {
    // Repositorio Maven Central
    mavenCentral()
    mavenLocal()
    google()
    jcenter()
    // Repositorio de SAP con protocolo inseguro permitido

//    maven {
//         url = uri("http://nexus.wdf.sap.corp:8081/nexus/content/repositories/build.snapshots")
//        isAllowInsecureProtocol = true
//    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
//    implementation("com.sap.cloud.android:odata-android-gradle-plugin:3.3.1")

}