import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register


class MigrationPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        target.tasks.register<MigrateToBazelTask>("migrateToBazel")
    }
}