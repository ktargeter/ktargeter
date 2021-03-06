package org.ktargeter

import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

class KtargeterCommandLineProcessor : CommandLineProcessor {
    override val pluginId = "ktargeter"

    override val pluginOptions = listOf(
        CliOption(
            optionName = "ktargeterAnnotation",
            valueDescription = "<annotation>",
            description = "annotation names in format <target>:<fully qualified annotation name>",
            required = false,
            allowMultipleOccurrences = true
        )
    )

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) = when (option.optionName) {
        "ktargeterAnnotation" -> configuration.appendList(KEY_ANNOTATIONS, value)
        else -> error("Unexpected config option ${option.optionName}")
    }
}

val KEY_ANNOTATIONS = CompilerConfigurationKey<List<String>>("ktargeter annotations")
