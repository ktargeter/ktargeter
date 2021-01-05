package ktargeter

import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class KtargeterGradleSubplugin : KotlinGradleSubplugin<AbstractCompile> {

    override fun isApplicable(project: Project, task: AbstractCompile) =
        project.plugins.hasPlugin(KtargeterGradlePlugin::class.java)

    override fun apply(
        project: Project,
        kotlinCompile: AbstractCompile,
        javaCompile: AbstractCompile?,
        variantData: Any?,
        androidProjectHandler: Any?,
        kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?
    ): List<SubpluginOption> {
        val extension = project.extensions.findByType(
            KtargeterGradleExtension::class.java
        ) ?: KtargeterGradleExtension()

        if (extension.enabled && extension.annotations.isEmpty()) {
            error("ktargeter is enabled, but no annotations were set")
        }

        val annotationOptions = extension.annotations.map {
            SubpluginOption(key = "ktargeterAnnotation", value = it)
        }
        val enabledOption = SubpluginOption(key = "enabled", value = extension.enabled.toString())
        return annotationOptions + enabledOption
    }

    override fun getCompilerPluginId() = "ktargeter"

    override fun getPluginArtifact() = SubpluginArtifact(
        groupId = "ktargeter",
        artifactId = "kotlin-plugin",
        version = "0.1.0"
    )
}
