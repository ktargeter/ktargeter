package ktargeter

import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.FieldVisitor

internal class KtargeterClassBuilder(
    private val ktargeterAnnotations: List<String>,
    delegateBuilder: ClassBuilder
) : DelegatingClassBuilder(delegateBuilder) {
    override fun newField(
        origin: JvmDeclarationOrigin,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        value: Any?
    ): FieldVisitor {
        val visitor = super.newField(origin, access, name, desc, signature, value)
        val descriptor = origin.descriptor as? PropertyDescriptor ?: return visitor
        // TODO: make it work
        if (ktargeterAnnotations.none { descriptor.annotations.hasAnnotation(FqName(it)) }) {
            return visitor
        }
        return visitor
    }
}
