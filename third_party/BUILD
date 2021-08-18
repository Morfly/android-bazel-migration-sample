load("@rules_java//java:defs.bzl", "java_library", "java_import")
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_import")
load("@rules_jvm_external//:defs.bzl", "artifact")

package(default_visibility = ["//visibility:public"])

kt_jvm_import(
    name = "kotlin_reflect",
    jars = ["kotlin-reflect-1.5.10.jar_desugared.jar"],
)

java_library(
    name = "sun_misc_stubs",
    srcs = [
        "sun/misc/Signal.java",
        "sun/misc/SignalHandler.java",
    ],
    visibility = ["//visibility:private"],
    neverlink = True,
)

kt_jvm_import(
    name = "kotlinx_coroutines_core_jvm",
    deps = [
        ":sun_misc_stubs",
        artifact("org.jetbrains.kotlin:kotlin-stdlib-common"),
        artifact("org.jetbrains.kotlin:kotlin-stdlib-jdk8"),
    ],
    jars = ["@maven_secondary//:v1/https/repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.5.1/kotlinx-coroutines-core-jvm-1.5.1.jar"],
)