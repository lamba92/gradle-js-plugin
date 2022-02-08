import externals.aws.APIGatewayEvent
import externals.aws.APIGatewayProxyResult
import externals.aws.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import org.jetbrains.gradle.respond
import kotlin.js.Promise

@JsExport
@JsName("handler")
fun handler(event: APIGatewayEvent, context: Context): Promise<APIGatewayProxyResult> =
    GlobalScope.promise { event.respond() }
