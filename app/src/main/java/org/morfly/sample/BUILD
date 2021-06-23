load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")
load("@rules_jvm_external//:defs.bzl", "artifact")

LIB_FILES = [
        "AppComponent.kt",
        "Navigation.kt",
        "SquareFrameLayout.kt",
        "ViewModelFactory.kt"
]

ARTIFACTS = [
        artifact("androidx.core:core-ktx"),
        artifact("androidx.lifecycle:lifecycle-viewmodel-ktx"),
        artifact("androidx.lifecycle:lifecycle-runtime-ktx"),
        artifact("androidx.fragment:fragment-ktx"),
        artifact("androidx.appcompat:appcompat"),
        artifact("com.google.android.material:material"),
        artifact("androidx.constraintlayout:constraintlayout"),
        artifact("io.coil-kt:coil"),
]

kt_android_library(
    name = "lib",
    srcs = LIB_FILES,
    custom_package = "org.morfly.sample",
    manifest = "//app:src/main/AndroidManifest.xml",
    visibility = ["//app:__subpackages__"],
    exports = ARTIFACTS,
    deps = [
        "//app:res",
        "//images",
        "//:dagger",
    ] + ARTIFACTS,
)

kt_android_library(
    name = "app",
    srcs = glob(
        ["**/*.kt"],
        exclude = LIB_FILES
    ),
    custom_package = "org.morfly.sample",
    manifest = "//app:src/main/AndroidManifest.xml",
    visibility = ["//app:__subpackages__"],
    deps = [
        "//app:res",
        "//app/src/main/java/org/morfly/sample/details",
        "//app/src/main/java/org/morfly/sample/feed",
        "//images",
        "//:dagger",
    ],
)

