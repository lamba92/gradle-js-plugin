@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias CodePipelineHandler = Handler<CodePipelineEvent, Unit>

external interface S3ArtifactLocation {
    var bucketName: String
    var objectKey: String
}

external interface S3ArtifactStore {
    var type: String /* "S3" */
    var s3Location: S3ArtifactLocation
}

typealias ArtifactLocation = S3ArtifactStore

external interface Artifact {
    var name: String
    var revision: String?
    var location: ArtifactLocation
}

external interface Credentials {
    var accessKeyId: String
    var secretAccessKey: String
    var sessionToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface EncryptionKey {
    var type: String
    var id: String
}

external interface `T$50` {
    var FunctionName: String
    var UserParameters: String
}

external interface `T$51` {
    var configuration: `T$50`
}

external interface `T$53` {
    var actionConfiguration: `T$51`
    var inputArtifacts: Array<Artifact>
    var outputArtifacts: Array<Artifact>
    var artifactCredentials: Credentials
    var encryptionKey: EncryptionKey? /* externals.aws.EncryptionKey? & `T$52`? */
        get() = definedExternally
        set(value) = definedExternally
    var continuationToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$54` {
    var id: String
    var accountId: String
    var data: `T$53`
}

external interface CodePipelineEvent {
//    var CodePipeline.job: `T$54`
}