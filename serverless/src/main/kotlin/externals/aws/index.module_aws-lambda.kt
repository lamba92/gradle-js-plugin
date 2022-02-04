@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package externals.aws

external interface `T$59` {
    var awsSdkVersion: String
    var clientId: String
}

external interface `T$60` {
    var challengeName: String /* "CUSTOM_CHALLENGE" | "PASSWORD_VERIFIER" | "SMS_MFA" | "DEVICE_SRP_AUTH" | "DEVICE_PASSWORD_VERIFIER" | "ADMIN_NO_SRP_AUTH" | "SRP_A" */
    var challengeResult: Boolean
    var challengeMetadata: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$61` {
    var userAttributes: `T$17`
    var validationData: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var codeParameter: String?
        get() = definedExternally
        set(value) = definedExternally
    var linkParameter: String?
        get() = definedExternally
        set(value) = definedExternally
    var usernameParameter: String?
        get() = definedExternally
        set(value) = definedExternally
    var newDeviceUsed: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var session: Array<`T$60`>?
        get() = definedExternally
        set(value) = definedExternally
    var challengeName: String?
        get() = definedExternally
        set(value) = definedExternally
    var privateChallengeParameters: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var challengeAnswer: String?
        get() = definedExternally
        set(value) = definedExternally
    var password: String?
        get() = definedExternally
        set(value) = definedExternally
    var clientMetadata: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var userNotFound: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$62` {
    var groupsToOverride: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var iamRolesToOverride: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var preferredRole: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$63` {
    var claimsToAddOrOverride: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var claimsToSuppress: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var groupOverrideDetails: `T$62`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$64` {
    var autoConfirmUser: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var autoVerifyPhone: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var autoVerifyEmail: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var smsMessage: String?
        get() = definedExternally
        set(value) = definedExternally
    var emailMessage: String?
        get() = definedExternally
        set(value) = definedExternally
    var emailSubject: String?
        get() = definedExternally
        set(value) = definedExternally
    var challengeName: String?
        get() = definedExternally
        set(value) = definedExternally
    var issueTokens: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var failAuthentication: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var publicChallengeParameters: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var privateChallengeParameters: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var challengeMetadata: String?
        get() = definedExternally
        set(value) = definedExternally
    var answerCorrect: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var userAttributes: `T$17`?
        get() = definedExternally
        set(value) = definedExternally
    var finalUserStatus: String? /* "CONFIRMED" | "RESET_REQUIRED" */
        get() = definedExternally
        set(value) = definedExternally
    var messageAction: String? /* "SUPPRESS" */
        get() = definedExternally
        set(value) = definedExternally
    var desiredDeliveryMediums: Array<String? /* "EMAIL" | "SMS" */>?
        get() = definedExternally
        set(value) = definedExternally
    var forceAliasCreation: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var claimsOverrideDetails: `T$63`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CognitoUserPoolTriggerEvent {
    var version: Number
    var triggerSource: String /* "PreSignUp_SignUp" | "PreSignUp_ExternalProvider" | "PostConfirmation_ConfirmSignUp" | "PreAuthentication_Authentication" | "PostAuthentication_Authentication" | "CustomMessage_SignUp" | "CustomMessage_AdminCreateUser" | "CustomMessage_ResendCode" | "CustomMessage_ForgotPassword" | "CustomMessage_UpdateUserAttribute" | "CustomMessage_VerifyUserAttribute" | "CustomMessage_Authentication" | "DefineAuthChallenge_Authentication" | "CreateAuthChallenge_Authentication" | "VerifyAuthChallengeResponse_Authentication" | "PreSignUp_AdminCreateUser" | "PostConfirmation_ConfirmForgotPassword" | "TokenGeneration_HostedAuth" | "TokenGeneration_Authentication" | "TokenGeneration_NewPasswordChallenge" | "TokenGeneration_AuthenticateDevice" | "TokenGeneration_RefreshTokens" | "UserMigration_Authentication" | "UserMigration_ForgotPassword" */
    var region: String
    var userPoolId: String
    var userName: String?
        get() = definedExternally
        set(value) = definedExternally
    var callerContext: `T$59`
    var request: `T$61`
    var response: `T$64`
}

typealias CognitoUserPoolEvent = CognitoUserPoolTriggerEvent
