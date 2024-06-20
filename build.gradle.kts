repositories {
    mavenCentral()
}

plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "com.ggdev"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

val asciidoctorExt: Configuration by configurations.creating

dependencies {
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
}
val snippetsDir by extra { file("build/generated-snippets") }

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    implementation("org.mariadb.jdbc:mariadb-java-client")


    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
    implementation("com.google.code.gson:gson:2.8.9")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")



    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("io.projectreactor:reactor-test")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    useJUnitPlatform()
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    configurations(asciidoctorExt.name)
    baseDirFollowsSourceDir()
    dependsOn(tasks.test)
}


tasks.bootJar {
    //dependsOn(tasks.asciidoctor)
    archiveFileName.set("shorturl-1.0.jar")
    mainClass.set("com.ggdev.MainApplication")
}