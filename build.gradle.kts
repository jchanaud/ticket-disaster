import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

plugins {
    id("io.micronaut.application") version "4.5.4"
    id("com.gradleup.shadow") version "8.3.7"
    id("io.micronaut.test-resources") version "4.5.4"
    id("io.micronaut.aot") version "4.5.4"
    id("java")
    id("gg.jte.gradle") version "3.2.1"
}

version = "0.1"
group = "io.github.jchanaud"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.kafka:micronaut-kafka")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.redis:micronaut-redis-lettuce")
    implementation("io.micronaut.views:micronaut-views-jte")

    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut:micronaut-http-client")
}


application {
    mainClass = "io.github.jchanaud.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}

jte {
    precompile()
}
graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.github.jchanaud.*")
    }
    testResources {
        sharedServer = true
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}
tasks.named<io.micronaut.gradle.docker.MicronautDockerfile>("dockerfile") {
    // Need jdk since I'm not precompiling jte templates during build
    baseImage = "eclipse-temurin:21-jdk"
}
tasks.named<DockerBuildImage>("dockerBuild") {
    images.set(listOf("twobeeb/ticketdisaster"))
}