package demo

data class User(
    @DemoAnnotation
    val firstName: String,
    @get:DemoAnnotation
    val lastName: String,
    @field:DemoAnnotation
    val active: Boolean
)
