package templates

fun library_build(
    name: String,
    packageName: String,
    artifacts: String
) = """
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")
load("@rules_jvm_external//:defs.bzl", "artifact")

kt_android_library(
    name = "$name",
    srcs = glob(["src/main/java/**/*.kt"]),
    resource_files = glob(["src/main/res/**"]),
    custom_package = "$packageName",
    manifest = "src/main/AndroidManifest.xml",
    visibility = ["//visibility:public"],
    deps = [
        "//:dagger",
        "//:kotlin_coroutines_jvm",
 $artifacts
    ],
)
""".trimIndent()