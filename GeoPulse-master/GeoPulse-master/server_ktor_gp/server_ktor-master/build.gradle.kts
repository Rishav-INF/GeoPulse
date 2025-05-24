
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation("io.ktor:ktor-serialization-gson:3.1.3")
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("mysql:mysql-connector-java:8.0.33") // MySQL Driver
    implementation("com.zaxxer:HikariCP:5.0.1") // Connection pooling
    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")
    //SMTP dependencies injection
    implementation("jakarta.mail:jakarta.mail-api:2.0.1")
    implementation("com.sun.activation:jakarta.activation:2.0.1")
    implementation("com.sun.mail:jakarta.mail:2.0.1")


    // Exposed Core
    implementation("org.jetbrains.exposed:exposed-core:0.44.1")

    // Exposed JDBC
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.1")

    // Exposed DAO (if using)
    implementation("org.jetbrains.exposed:exposed-dao:0.44.1")

    // Java Time support (for LocalDateTime)
    implementation("org.jetbrains.exposed:exposed-java-time:0.44.1")

    // Your JDBC driver (example for PostgreSQL)
    implementation("org.postgresql:postgresql:42.7.1")

    // Kotlin serialization (if needed)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")


}
