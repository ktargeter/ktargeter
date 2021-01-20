package ktargeter

import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrConstructor
import org.jetbrains.kotlin.ir.declarations.IrProperty
import org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl
import org.jetbrains.kotlin.ir.util.IdSignature
import org.jetbrains.kotlin.ir.util.properties
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.utils.addToStdlib.safeAs

class KtargeterTransformer(
    private val annotationMapping: Map<String, String>
) : IrElementTransformerVoidWithContext() {

    override fun visitConstructor(declaration: IrConstructor): IrStatement {
        declaration.withParentCalss { parent ->
            declaration.valueParameters.forEach { param ->
                parent.withProperty(param.name) { property ->
                    param.annotations.forEach { annotation ->
                        rearrangeAnnotations(property, annotation)
                    }
                }
                param.annotations = param.annotations.filter {
                    it.fqName() !in annotationMapping.keys
                }
            }
        }
        return super.visitConstructor(declaration)
    }


    override fun visitPropertyNew(declaration: IrProperty): IrStatement {
        declaration.annotations.forEach { annotation ->
            rearrangeAnnotations(declaration, annotation)
            declaration.annotations = declaration.annotations.filter {
                it.fqName() !in annotationMapping.keys
            }
        }
        return super.visitPropertyNew(declaration)
    }

    private fun IrConstructorCall.fqName(): String? {
        return this.type.safeAs<IrSimpleTypeImpl>()
            ?.classifier?.signature?.safeAs<IdSignature.PublicSignature>()
            ?.let { it.packageFqName + "." + it.shortName }
    }

    private fun IrConstructor.withParentCalss(block: (IrClassImpl) -> Unit) =
        when (val parent = this.parent) {
            is IrClassImpl -> block(parent)
            else -> Unit
        }

    private fun IrClassImpl.withProperty(name: Name, block: (IrProperty) -> Unit) =
        this.properties.find { it.name == name }?.let(block)

    private fun rearrangeAnnotations(property: IrProperty, annotation: IrConstructorCall) {
        val fqName = annotation.fqName()
        if (fqName != null && annotationMapping.containsKey(fqName)) {
            when (annotationMapping[fqName]!!) {
                "get" -> property.getter?.let {
                    it.annotations = it.annotations + annotation
                }
                "set" -> property.setter?.let {
                    it.annotations = it.annotations + annotation
                }
                "field" -> property.backingField?.let {
                    it.annotations = it.annotations + annotation
                }
            }
        }
    }
}
