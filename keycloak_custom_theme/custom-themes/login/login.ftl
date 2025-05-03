<!DOCTYPE html>
<html lang="${locale.current?string?js_string}">

<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="robots" content="noindex, nofollow">

    <title>${msg("LogIn")}</title>

    <link rel="stylesheet" href="${url.resourcesPath}/css/styles.css" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-1ycn6IcaQQmQa7KBw0YJ/yIU5g3zFYfxd1lRdQXyGPQrKHGbbcc9gfsKtQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    <#if properties.favIconUrl?has_content>
        <link rel="shortcut icon" href="${properties.favIconUrl}"/>
    </#if>

    <#if scripts??>
        <#list scripts as script>
            <script src="${script}" type="text/javascript"></script>
        </#list>
    </#if>
</head>

<body class="${properties.kcBodyClass!}">
<div class="${properties.kcLoginClass!}">
    <div id="keycloak-login-wrapper">
        <#if realm.internationalizationEnabled && locale.supported?size gt 1>
            <div class="kc-dropdown">
                <div id="kc-locale">
                    <#include "../common/keycloak-locales.ftl"/>
                </div>
            </div>
        </#if>
        <div class="kc-logo-wrapper"><img src="${url.resourcesPath}/img/logo.png" alt="Logo"/></div>
        <div class="login-form">
            <#if message?has_content>
                <div class="alert alert-${message.type}">
                    <#if message.type = 'warning' || message.type = 'error'>
                        <span class="pficon pficon-error-circle"></span>
                    <#else>
                        <span class="pficon pficon-ok"></span>
                    </#if>
                    <span class="kc-feedback-text">${message.summary}</span>
                </div>
            </#if>
            <form id="kc-form-login" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
                <div class="${properties.kcFormGroupClass!} username-icon">
                    <label for="username" class="${properties.kcLabelClass!}">${msg("username")}</label>
                    <input tabindex="1" id="username" class="${properties.kcInputClass!}" name="username" type="text" value="${login.username!}"
                           autofocus autocomplete="off"/>
                    <i class="icon"></i>
                </div>

                <div class="${properties.kcFormGroupClass!} password-icon">
                    <label for="password" class="${properties.kcLabelClass!}">${msg("password")}</label>
                    <input tabindex="2" id="password" class="${properties.kcInputClass!}" name="password" type="password"
                           autocomplete="off"/>
                    <i class="icon"></i>
                </div>

                <#if realm.rememberMe>
                    <div class="${properties.kcFormGroupClass!}">
                        <div class="${properties.kcCheckboxClass!}">
                            <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox"
                                   <#if login.rememberMe??>checked</#if>/>
                            <label for="rememberMe" class="${properties.kcLabelClass!}">${msg("Remember Me")}</label>
                        </div>
                    </div>
                </#if>

                <div id="kc-form-options">
                    <#if realm.resetPasswordAllowed>
                        <div class="kc-form-options-item"><a tabindex="4" href="${url.loginAction}&amp;execution=e1s">${msg("Forgot Password")}</a>
                        </div>
                    </#if>
                    <#if realm.registrationAllowed>
                        <div class="kc-form-options-item"><a tabindex="5" href="${url.registrationAction}">${msg("No Account")}</a></div>
                    </#if>
                </div>

                <div id="kc-form-buttons" class="${properties.kcFormGroupClass!}">
                    <input tabindex="6" class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!}"
                           name="login" id="kc-login" type="submit" value="${msg("LogIn")}"/>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
