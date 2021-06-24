import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.TaskAction
import templates.application_build
import templates.library_build
import templates.root_build
import templates.workspace
import java.io.File


open class MigrateToBazelTask : DefaultTask() {

    private val allArtifacts = mutableListOf<String>()

    private val configs = setOf(
        "implementation",
        "api"
    )

    @TaskAction
    fun migrateToBazel() {
        project
            .allprojects
            .forEach { proj -> migrateModule(proj) }

        createWorkspaceFile()
    }

    private fun migrateModule(project: Project) {
        val artifactDeps = mutableListOf<String>()
        val moduleDeps = mutableListOf<String>()

        // find dependencies
        project.configurations
            .filter { it.name in configs }
            .flatMap { it.dependencies }
            .mapNotNull { dep ->
                when (dep) {
                    is ExternalDependency -> {
                        allArtifacts += "${dep.group}:${dep.name}:${dep.version}"
                        artifactDeps += "${dep.group}:${dep.name}"
                    }
                    is ProjectDependency -> {
                        moduleDeps += dep.dependencyProject.path
                            .split(":")
                            .filter { it.isNotBlank() }
                            .joinToString(prefix = "//", separator = "/")
                    }
                    else -> null
                }
            }

        // format deependencies
        val artifacts = artifactDeps.joinToString(
            prefix = "[\n",
            separator = ",\n",
            postfix = "]"
        ) { artifact ->
            "    artifact(\"$artifact\")"
        }

        val deps = moduleDeps.joinToString(
            prefix = "[\n",
            separator = ",\n",
            postfix = "]"
        ) { module ->
            "    \"$module\""
        }

        // map templates to right gradle modules
        val buildFileContent = with(project.plugins) {
            when {
                hasPlugin("com.android.application") ->
                    application_build(project.name, project.packageName()!!, deps, artifacts)

                hasPlugin("com.android.library") ->
                    library_build(project.name, project.packageName()!!, artifacts)

                !hasPlugin("java") ->
                    root_build()

                else -> error("Unable to find template for project '${project.name}'")
            }
        }

        val buildFile = File("${project.projectDir.path}/BUILD.bazel")
        buildFile.createNewFile()
        buildFile.writeText(buildFileContent)
    }

    private fun createWorkspaceFile() {
        val artifacts = allArtifacts.joinToString(
            prefix = "[\n",
            separator = ",\n",
            postfix = "]"
        ) { artifact ->
            "    artifact(\"$artifact\")"
        }
        val workspaceFile = File("${project.projectDir}/WORKSPACE")
        workspaceFile.createNewFile()
        workspaceFile.writeText(workspace(artifacts))
    }
}