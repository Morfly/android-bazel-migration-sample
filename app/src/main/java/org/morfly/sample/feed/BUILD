load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")
load("@rules_jvm_external//:defs.bzl", "artifact")

kt_android_library(
    name = "feed",
    srcs = glob(["**/*.kt"]),
    custom_package = "org.morfly.sample",
    manifest = "//app:src/main/AndroidManifest.xml",
    visibility = ["//app:__subpackages__"],
    deps = [
        "//app:res",
        "//app/src/main/java/org/morfly/sample:lib",
        "//app/src/main/java/org/morfly/sample/details",
        "//images",
        "//:dagger",
    ],
)