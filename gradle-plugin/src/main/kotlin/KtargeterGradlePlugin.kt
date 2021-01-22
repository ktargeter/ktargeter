package org.ktargeter

import org.gradle.api.Plugin
import org.gradle.api.Project

open class KtargeterGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create(
            "ktargeter",
            KtargeterGradleExtension::class.java
        )
    }
}
