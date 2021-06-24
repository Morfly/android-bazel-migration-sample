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

    private val allArtifacts = mutableSetOf<String>()

    private val ignoredArtifactNames = setOf(
        "kotlin-stdlib",
        "kotlinx-coroutines-core",
        "dagger"
    )

    private val gradleConfigs = setOf(
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
            .filter { it.name in gradleConfigs }
            .flatMap { it.dependencies }
            .forEach { dep ->
                when (dep) {
                    is ExternalDependency -> {
                        if (dep.name !in ignoredArtifactNames) {
                            artifactDeps += "${dep.group}:${dep.name}"
                            allArtifacts += "${dep.group}:${dep.name}:${dep.version}"
                        }
                    }
                    is ProjectDependency -> {
                        moduleDeps += dep.dependencyProject.path
                            .split(":")
                            .filter { it.isNotBlank() }
                            .joinToString(prefix = "//", separator = "/")
                    }
                }
            }

        // format dependencies
        val artifacts = artifactDeps.joinToString(separator = ",\n") { artifact ->
            "\t\tartifact(\"$artifact\")"
        }

        val deps = moduleDeps.joinToString(separator = ",\n") { module ->
            "\t\t\"$module\""
        }

        // map templates to right gradle modules
        val buildFileContent = with(project.plugins) {
            when {
                hasPlugin("com.android.application") ->
                    application_build(project.name, project.packageName()!!, deps, artifacts)

                hasPlugin("com.android.library") ->
                    library_build(project.name, project.packageName()!!, artifacts)

                project == project.rootProject ->
                    root_build()

                else -> error("Unable to find template for project '${project.name}'")
            }
        }

        val buildFile = File("${project.projectDir.path}/BUILD.bazel")
        buildFile.createNewFile()
        buildFile.writeText(buildFileContent)
    }

    private fun createWorkspaceFile() {
        val artifacts = allArtifacts.joinToString(separator = ",\n") { artifact ->
            "\t\t\"$artifact\""
        }
        val workspaceFile = File("${project.projectDir}/WORKSPACE")
        workspaceFile.createNewFile()
        workspaceFile.writeText(workspace(artifacts))
    }
}