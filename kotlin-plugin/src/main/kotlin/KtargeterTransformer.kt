package ktargeter

import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrProperty
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl
import org.jetbrains.kotlin.ir.util.IdSignature
import org.jetbrains.kotlin.utils.addToStdlib.safeAs

class KtargeterTransformer(
    private val annotationMapping: Map<String, String>
) : IrElementTransformerVoidWithContext() {
    override fun visitPropertyNew(declaration: IrProperty): IrStatement {
        // works for non-data classes
        declaration.annotations.forEach { annotation ->
            val fqName = annotation.fqName()
            if (fqName != null && annotationMapping.containsKey(fqName)) {
                when (annotationMapping[fqName]!!) {
                    "get" -> declaration.getter?.let {
                        it.annotations = it.annotations + annotation
                    }
                    "set" -> declaration.setter?.let {
                        it.annotations = it.annotations + annotation
                    }
                    "field" -> declaration.backingField?.let {
                        it.annotations = it.annotations + annotation
                    }
                }
            }
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
}
