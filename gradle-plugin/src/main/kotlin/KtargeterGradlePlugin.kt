package org.ktargeter

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class KtargeterGradlePlugin : KotlinCompilerPluginSupportPlugin {

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean =
        kotlinCompilation.target.project.plugins.hasPlugin(KtargeterGradlePlugin::class.java)

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        val extension = project.extensions.findByType(
            KtargeterGradleExtension::class.java
        ) ?: KtargeterGradleExtension()
        val supportedTargets = setOf("get", "set", "field")
        extension.annotations.values.toSet().forEach {
            if (it !in supportedTargets) error("annotation target $it is not supported")
        }
        val annotationOptions = extension.annotations.map {
            SubpluginOption(key = "ktargeterAnnotation", value = "${it.value}:${it.key}")
        }
        return project.provider { annotationOptions }
    }

    override fun apply(target: Project): Unit = with(target) {
        extensions.create(
            "ktargeter",
            KtargeterGradleExtension::class.java
        )
    }

    override fun getCompilerPluginId() = "ktargeter"

    override fun getPluginArtifact() = SubpluginArtifact(
        groupId = "org.ktargeter",
        artifactId = "compiler-plugin",
        version = "0.2.1"
    )
}
