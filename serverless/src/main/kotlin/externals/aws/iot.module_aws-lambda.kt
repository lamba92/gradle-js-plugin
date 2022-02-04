@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

typealias IoTHandler = Handler<dynamic /* String | Number | any */, Unit>

typealias IoTPreProvisioningHookHandler = Handler<IoTPreProvisioningHookEvent, IoTPreProvisioningHookResult>

external interface IoTPreProvisioningHookEvent {
    var claimCertificateId: String
    var certificateId: String
    var certificatePem: String
    var templateArn: String
    var clientId: String
    var parameters: Record<String, String>
}

external interface IoTPreProvisioningHookResult {
    var allowProvisioning: Boolean
    var parameterOverrides: Record<String, String>
}