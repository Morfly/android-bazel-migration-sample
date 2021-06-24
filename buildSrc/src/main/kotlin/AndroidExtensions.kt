import com.android.build.gradle.BaseExtension
import groovy.util.XmlSlurper
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import java.io.File

val xmlParser = XmlSlurper()


fun Project.packageName(): String? {
    val ext = extensions.findByType<BaseExtension>()

    return ext?.defaultConfig?.applicationId

        ?: manifest()
            ?.let(xmlParser::parse)
            ?.getProperty("@package")
            ?.toString()
}


fun Project.manifest(): File? {
    val ext = extensions.findByType<BaseExtension>()

    return ext
        ?.sourceSets
        ?.map { it.manifest.srcFile }
        ?.firstOrNull()
}