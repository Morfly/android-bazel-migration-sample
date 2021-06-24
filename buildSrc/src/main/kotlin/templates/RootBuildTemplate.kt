package templates

fun root_build() = """
    load("@dagger//:workspace_defs.bzl", "dagger_rules")

    dagger_rules()

    java_import(
        name = "kotlin_coroutines_jvm",
        jars = glob(["coroutines/*.jar"]),
        visibility = ["//visibility:public"]
    )
""".trimIndent()