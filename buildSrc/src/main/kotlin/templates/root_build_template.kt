@file:Suppress("FunctionName", "SpellCheckingInspection")

package templates


fun root_build_template() = """
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()
"""