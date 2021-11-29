package com.github.lamba92.npmtomaven

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

class NotFoundInterceptor(private val configuration: Configuration) {

    class Configuration {

        internal var action: suspend PipelineContext<Any, ApplicationCall>.() -> Unit = {}

        fun notFoundAction(action: suspend PipelineContext<Any, ApplicationCall>.() -> Unit) {
            this.action = action
        }
    }

    companion object : ApplicationFeature<Application, Configuration, NotFoundInterceptor> {

        override val key: AttributeKey<NotFoundInterceptor> = AttributeKey("NotFoundInterceptor")

        override fun install(
            pipeline: Application,
            configure: Configuration.() -> Unit
        ): NotFoundInterceptor {
            val feature = NotFoundInterceptor(Configuration().apply(configure))
            pipeline.sendPipeline.intercept(ApplicationSendPipeline.Before) { message ->
                val is404 = when (message) {
                    is HttpStatusCode -> message == HttpStatusCode.NotFound
                    is HttpStatusCodeContent -> message.status == HttpStatusCode.NotFound
                    else -> false
                }

                if (!is404 || call.attributes.contains(key)) return@intercept
                call.attributes.put(key, feature)
                feature.configuration.action(this)
                finish()
            }
            return feature
        }
    }
}