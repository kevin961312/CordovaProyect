"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.registerLocationListener = exports.setAppDeviceId = exports.setUserId = exports.setAppSessionId = exports.setLocation = exports.getVersion = exports.getApplicationHostname = exports.getParentId = exports.getBrowserId = exports.getSessionId = exports.registerDiagnosticsListener = exports.getDiagnostics = exports.updateDetection = exports.invalidateSession = exports.initWithConfiguration = void 0;
var cordova = require("cordova");
var PluginService = "CleafyCordovaPlugin";
function initWithConfiguration(onSuccess, onError, _a) {
    var apiEndpoint = _a.apiEndpoint, applicationHostname = _a.applicationHostname, _b = _a.isDefaultEnabled, isDefaultEnabled = _b === void 0 ? true : _b, _c = _a.automaticUpdateAnalysis, automaticUpdateAnalysis = _c === void 0 ? "NONE" : _c, _d = _a.emulatorDebugAnalysis, emulatorDebugAnalysis = _d === void 0 ? "NONE" : _d, _e = _a.isPackageAnalysisEnabled, isPackageAnalysisEnabled = _e === void 0 ? true : _e, _f = _a.isExtendedPackageInformation, isExtendedPackageInformation = _f === void 0 ? false : _f, _g = _a.isSensitiveIdentifierCollectionEnabled, isSensitiveIdentifierCollectionEnabled = _g === void 0 ? true : _g, _h = _a.isHashSensitiveIdentifiers, isHashSensitiveIdentifiers = _h === void 0 ? false : _h, _j = _a.isAllowUntrustedCertificates, isAllowUntrustedCertificates = _j === void 0 ? false : _j, _k = _a.detectorsConfiguration, _l = _k.isHttpDetectorEnabled, isHttpDetectorEnabled = _l === void 0 ? false : _l, _m = _k.isCertDetectorEnabled, isCertDetectorEnabled = _m === void 0 ? false : _m, _o = _k.certDetectorEndpoint, certDetectorEndpoint = _o === void 0 ? "" : _o, _p = _k.isMonitoredAppEnabled, isMonitoredAppEnabled = _p === void 0 ? true : _p, _q = _k.isActivityDetectorEnabled, isActivityDetectorEnabled = _q === void 0 ? false : _q, _r = _k.isAdvancedHttpCertDetectorEnabled, isAdvancedHttpCertDetectorEnabled = _r === void 0 ? false : _r, _s = _k.isRootDetectorEnabled, isRootDetectorEnabled = _s === void 0 ? true : _s, _t = _k.isTaskInjectionEnabled, isTaskInjectionEnabled = _t === void 0 ? false : _t, _u = _k.isMockLocationDetectorEnabled, isMockLocationDetectorEnabled = _u === void 0 ? true : _u, _v = _k.isAdvancedMockLocationDetectorEnabled, isAdvancedMockLocationDetectorEnabled = _v === void 0 ? false : _v, _w = _k.isOnCallDetectorEnabled, isOnCallDetectorEnabled = _w === void 0 ? true : _w, _x = _k.isHumanDetectorEnabled, isHumanDetectorEnabled = _x === void 0 ? false : _x;
    var configuration = {
        apiEndpoint: apiEndpoint,
        applicationHostname: applicationHostname,
        isDefaultEnabled: isDefaultEnabled,
        automaticUpdateAnalysis: automaticUpdateAnalysis,
        emulatorDebugAnalysis: emulatorDebugAnalysis,
        isPackageAnalysisEnabled: isPackageAnalysisEnabled,
        isExtendedPackageInformation: isExtendedPackageInformation,
        isSensitiveIdentifierCollectionEnabled: isSensitiveIdentifierCollectionEnabled,
        isHashSensitiveIdentifiers: isHashSensitiveIdentifiers,
        isAllowUntrustedCertificates: isAllowUntrustedCertificates,
        detectorsConfiguration: {
            isHttpDetectorEnabled: isHttpDetectorEnabled,
            isCertDetectorEnabled: isCertDetectorEnabled,
            certDetectorEndpoint: certDetectorEndpoint,
            isMonitoredAppEnabled: isMonitoredAppEnabled,
            isActivityDetectorEnabled: isActivityDetectorEnabled,
            isAdvancedHttpCertDetectorEnabled: isAdvancedHttpCertDetectorEnabled,
            isRootDetectorEnabled: isRootDetectorEnabled,
            isTaskInjectionEnabled: isTaskInjectionEnabled,
            isMockLocationDetectorEnabled: isMockLocationDetectorEnabled,
            isAdvancedMockLocationDetectorEnabled: isAdvancedMockLocationDetectorEnabled,
            isOnCallDetectorEnabled: isOnCallDetectorEnabled,
            isHumanDetectorEnabled: isHumanDetectorEnabled,
        },
    };
    cordova.exec(onSuccess, onError, PluginService, "initWithConfiguration", [configuration]);
}
exports.initWithConfiguration = initWithConfiguration;
function invalidateSession(onSuccess, onError) {
    cordova.exec(onSuccess, onError, PluginService, "invalidateSession");
}
exports.invalidateSession = invalidateSession;
function updateDetection(onSuccess, onError) {
    cordova.exec(onSuccess, onError, PluginService, "updateDetection");
}
exports.updateDetection = updateDetection;
function getDiagnostics(onSuccess, onError) {
    cordova.exec(onSuccess, onError, PluginService, "getDiagnostics");
}
exports.getDiagnostics = getDiagnostics;
function registerDiagnosticsListener(onSuccess, onError) {
    cordova.exec(onSuccess, onError, PluginService, "registerDiagnosticsListener");
}
exports.registerDiagnosticsListener = registerDiagnosticsListener;
function getSessionId(onSuccess, onError) {
    cordova.exec(onSuccess, onError, PluginService, "getSessionId");
}
exports.getSessionId = getSessionId;
function getBrowserId(onSuccess, onError) {
    cordova.exec(onSuccess, onError, PluginService, "getBrowserId");
}
exports.getBrowserId = getBrowserId;
function getParentId(onSuccess, onError) {
    cordova.exec(onSuccess, onError, PluginService, "getParentId");
}
exports.getParentId = getParentId;
function getApplicationHostname(onSuccess, onError) {
    cordova.exec(onSuccess, onError, PluginService, "getApplicationHostname");
}
exports.getApplicationHostname = getApplicationHostname;
function getVersion(onSuccess, onError) {
    cordova.exec(onSuccess, onError, PluginService, "getVersion");
}
exports.getVersion = getVersion;
function setLocation(onSuccess, onError, location) {
    cordova.exec(onSuccess, onError, PluginService, "setLocation", [location]);
}
exports.setLocation = setLocation;
function setAppSessionId(onSuccess, onError, appSessionId) {
    cordova.exec(onSuccess, onError, PluginService, "setAppSessionId", [appSessionId]);
}
exports.setAppSessionId = setAppSessionId;
function setUserId(onSuccess, onError, userId) {
    cordova.exec(onSuccess, onError, PluginService, "setUserId", [userId]);
}
exports.setUserId = setUserId;
function setAppDeviceId(onSuccess, onError, appDeviceId) {
    cordova.exec(onSuccess, onError, PluginService, "setAppDeviceId", [appDeviceId]);
}
exports.setAppDeviceId = setAppDeviceId;
function registerLocationListener(onSuccess, onError, provider, minTime, minDistance) {
    if (cordova.platformId !== "android") {
        onSuccess();
        return;
    }
    cordova.exec(onSuccess, onError, PluginService, "registerLocationListener", [provider !== null && provider !== void 0 ? provider : null, minTime !== null && minTime !== void 0 ? minTime : null, minDistance !== null && minDistance !== void 0 ? minDistance : null]);
}
exports.registerLocationListener = registerLocationListener;
