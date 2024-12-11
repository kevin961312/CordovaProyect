package com.cleafy.mobile.cordova;

import com.cleafy.mobile.detection.agent.AgentDiagnostics;
import com.cleafy.mobile.detection.agent.ProbeTrace;
import com.cleafy.mobile.detection.agent.configuration.AutomaticUpdateAnalysis;
import com.cleafy.mobile.detection.agent.configuration.CleafyConfiguration;
import com.cleafy.mobile.detection.agent.configuration.CleafyDetectorsConfiguration;
import com.cleafy.mobile.detection.agent.configuration.EmulatorDebugAnalysis;
import com.cleafy.mobile.detection.agent.configuration.builder.CleafyConfigurationBuilder;
import com.cleafy.mobile.detection.agent.configuration.builder.CleafyDetectorsConfigurationBuilder;
import org.apache.cordova.PluginResult;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

class JSONUtils {
    public static JSONObject serializeAgentDiagnostics(AgentDiagnostics agentDiagnostics) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("deviceId", agentDiagnostics.getDeviceId());
        List<JSONObject> probeTraces = serializeProbeTraces(agentDiagnostics);
        result.put("probeTraces", probeTraces);
        result.put("errors", agentDiagnostics.getErrors());
        result.put("successfulProbeSubmissions", agentDiagnostics.getSuccessfulProbeSubmissions());
        result.put("failedProbeSubmissions", agentDiagnostics.getFailedProbeSubmissions());
        result.put("lastConfigurationUpdateTimestamp", agentDiagnostics.getLastConfigurationUpdateTimestamp());
        return result;
    }

    @SuppressWarnings("unchecked")
    private static List<JSONObject> serializeProbeTraces(AgentDiagnostics agentDiagnostics) throws JSONException {
        List<JSONObject> probeTraces = new ArrayList<>();
        for (ProbeTrace probeTrace : (List<ProbeTrace>) agentDiagnostics.getProbeTraces()) {
            JSONObject probeTraceJson = new JSONObject();
            probeTraceJson.put("eventId", probeTrace.getEventId());
            probeTraceJson.put("probeType", probeTrace.getProbeType());
            probeTraceJson.put("sessionId", probeTrace.getSessionId());
            probeTraceJson.put("statusCode", probeTrace.getStatusCode());
            probeTraceJson.put("timestamp", probeTrace.getTimestamp());
            probeTraces.add(probeTraceJson);
        }
        return probeTraces;
    }

    public static CleafyConfiguration parseCleafyConfiguration(JSONObject jsonObject) throws JSONException {
        JSONObject detectorsJson = jsonObject.getJSONObject("detectorsConfiguration");

        CleafyDetectorsConfiguration detectorsConfiguration = new CleafyDetectorsConfigurationBuilder()
                .httpDetectorEnabled(detectorsJson.getBoolean("isHttpDetectorEnabled"))
                .certDetectorEnabled(detectorsJson.getBoolean("isCertDetectorEnabled"))
                .certDetectorEndpoint(detectorsJson.getString("certDetectorEndpoint"))
                .monitoredAppEnabled(detectorsJson.getBoolean("isMonitoredAppEnabled"))
                .activityDetectorEnabled(detectorsJson.getBoolean("isActivityDetectorEnabled"))
                .advancedHttpCertDetectorEnabled(detectorsJson.getBoolean("isAdvancedHttpCertDetectorEnabled"))
                .rootDetectorEnabled(detectorsJson.getBoolean("isRootDetectorEnabled"))
                .taskInjectionEnabled(detectorsJson.getBoolean("isTaskInjectionEnabled"))
                .mockLocationDetectorEnabled(detectorsJson.getBoolean("isMockLocationDetectorEnabled"))
                .advancedMockLocationDetectorEnabled(detectorsJson.getBoolean("isAdvancedMockLocationDetectorEnabled"))
                .onCallDetectorEnabled(detectorsJson.getBoolean("isOnCallDetectorEnabled"))
                .humanDetectorEnabled(detectorsJson.getBoolean("isHumanDetectorEnabled"))
                .build();

        return new CleafyConfigurationBuilder()
                .apiEndpoint(jsonObject.getString("apiEndpoint"))
                .applicationHostname(jsonObject.getString("applicationHostname"))
                .defaultEnabled(jsonObject.getBoolean("isDefaultEnabled"))
                .automaticUpdateAnalysis(AutomaticUpdateAnalysis.valueOf(jsonObject.getString("automaticUpdateAnalysis")))
                .emulatorDebugAnalysis(EmulatorDebugAnalysis.valueOf(jsonObject.getString("emulatorDebugAnalysis")))
                .packageAnalysisEnabled(jsonObject.getBoolean("isPackageAnalysisEnabled"))
                .extendedPackageInformation(jsonObject.getBoolean("isExtendedPackageInformation"))
                .collectSensitiveIdentifiers(jsonObject.getBoolean("isSensitiveIdentifierCollectionEnabled"))
                .hashSensitiveIdentifiers(jsonObject.getBoolean("isHashSensitiveIdentifiers"))
                .allowUntrustedCertificates(jsonObject.getBoolean("isAllowUntrustedCertificates"))
                .detectorsConfiguration(detectorsConfiguration)
                .build();
    }

    private JSONUtils() {
    }
}
