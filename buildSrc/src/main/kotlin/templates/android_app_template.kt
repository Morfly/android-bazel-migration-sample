@file:Suppress("FunctionName", "SpellCheckingInspection")

package templates


fun android_app_template(
    name: String,
    packageName: String,
    deps: String,
    artifacts: String
) = """
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")
load("@rules_android//android:rules.bzl", "android_binary")
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
$deps,
$artifacts
    ],
)

android_binary(
    name = "bin",
    custom_package = "$packageName",
    manifest = "src/main/AndroidManifest.xml",
    manifest_values = {
        "minSdkVersion": "21",
        "targetSdkVersion": "29",
        "versionCode": "1",
        "versionName": "1.0",
    },
    multidex = "native",
    incremental_dexing = 1,
    debug_key = ":debug.keystore",
    visibility = ["//visibility:public"],
    deps = [":$name"],
)
"""