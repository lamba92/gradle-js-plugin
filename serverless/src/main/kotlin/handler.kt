import externals.aws.APIGatewayEvent
import externals.aws.APIGatewayProxyResult
import externals.aws.Context
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import org.jetbrains.gradle.respond
import kotlin.js.Promise

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(DelicateCoroutinesApi::class, ExperimentalJsExport::class)
@JsExport
@JsName("handler")
fun handler(event: APIGatewayEvent, context: Context): Promise<APIGatewayProxyResult> =
    GlobalScope.promise { event.respond() }