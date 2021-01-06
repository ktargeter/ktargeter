package ktargeter

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
import java.util.*

class KtargeterExtension(ktargeterAnnotations: List<String>) : SyntheticResolveExtension {
    private val annotations = ktargeterAnnotations.associateBy(
        { it.substringAfter(':') },
        { it.substringBefore(':') }
    )

    override fun generateSyntheticProperties(
        thisDescriptor: ClassDescriptor,
        name: Name,
        bindingContext: BindingContext,
        fromSupertypes: ArrayList<PropertyDescriptor>,
        result: MutableSet<PropertyDescriptor>
    ) {
        thisDescriptor.constructors.forEach { constructor ->
            constructor.original.valueParameters.forEach { parameter ->
                annotations.keys.forEach { ktargeterAnnotation ->
                    if (parameter.annotations.hasAnnotation(FqName(ktargeterAnnotation))) {
                        // TODO: handle this case
                    }
                }
            }
        }
        result.forEach { descriptor ->
            annotations.keys.forEach { ktargeterAnnotation ->
                if (descriptor.annotations.hasAnnotation(FqName(ktargeterAnnotation))) {
                    // TODO: handle this case
                }
            }
        }
        super.generateSyntheticProperties(thisDescriptor, name, bindingContext, fromSupertypes, result)
    }
}
