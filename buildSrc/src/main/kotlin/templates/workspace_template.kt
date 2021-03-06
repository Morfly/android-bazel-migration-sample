@file:Suppress("SpellCheckingInspection")

package templates


fun workspace(artifacts: String) = """
workspace(name = "android-migration-sample")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# ===== dagger =====

DAGGER_TAG = "2.36"
DAGGER_SHA = "1e6d5c64d336af2e14c089124bf2bd9d449010db02534ce820abc6a7f0429c86"

http_archive(
    name = "dagger",
    sha256 = DAGGER_SHA,
    strip_prefix = "dagger-dagger-%s" % DAGGER_TAG,
    urls = ["https://github.com/google/dagger/archive/dagger-%s.zip" % DAGGER_TAG],
)

# ===== jvm external rules =====

load("@dagger//:workspace_defs.bzl","DAGGER_ARTIFACTS", "DAGGER_REPOSITORIES")

RULES_JVM_EXTERNAL_VERSION = "4.1"
RULES_JVM_EXTERNAL_SHA = "f36441aa876c4f6427bfb2d1f2d723b48e9d930b62662bf723ddfb8fc80f0140"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_VERSION,
    urls = ["https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_VERSION],
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = DAGGER_ARTIFACTS + [
$artifacts
    ],
    repositories = DAGGER_REPOSITORIES + [
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
    override_targets = {
        "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm": "@//third_party:kotlinx_coroutines_core_jvm",
        "org.jetbrains.kotlin:kotlin-reflect": "@//third_party:kotlin_reflect",
    },
)

maven_install(
    name = "maven_secondary",
    artifacts = ["org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.1"],
    repositories = ["https://repo1.maven.org/maven2"],
)

# ===== android rules =====

RULES_ANDROID_VERSION = "0.1.1"
RULES_ANDROID_SHA = "cd06d15dd8bb59926e4d65f9003bfc20f9da4b2519985c27e190cddc8b7a7806"

http_archive(
    name = "rules_android",
    sha256 = RULES_ANDROID_SHA,
    strip_prefix = "rules_android-%s" % RULES_ANDROID_VERSION,
    urls = ["https://github.com/bazelbuild/rules_android/archive/v%s.zip" % RULES_ANDROID_VERSION],
)

load("@rules_android//android:rules.bzl", "android_sdk_repository")

android_sdk_repository(
    name = "androidsdk",
    api_level = 29,
    build_tools_version = "29.0.3",
)

# ===== kotlin rules =====

RULES_KOTLIN_VERSION = "v1.5.0-beta-3"
RULES_KOTLIN_SHA = "58edd86f0f3c5b959c54e656b8e7eb0b0becabd412465c37a2078693c2571f7f"

http_archive(
    name = "io_bazel_rules_kotlin",
    sha256 = RULES_KOTLIN_SHA,
    type = "tar.gz",
    urls = ["https://github.com/bazelbuild/rules_kotlin/releases/download/%s/rules_kotlin_release.tgz" % RULES_KOTLIN_VERSION],
)

load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains")
kotlin_repositories()
kt_register_toolchains()
""".trimIndent()