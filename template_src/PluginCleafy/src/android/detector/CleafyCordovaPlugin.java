package com.cleafy.mobile.cordova;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import com.cleafy.mobile.detection.agent.AgentDiagnosticsListener;
import com.cleafy.mobile.detection.agent.AgentDiagnosticsProvider;
import com.cleafy.mobile.detection.agent.Cleafy;
import com.cleafy.mobile.detection.agent.configuration.CleafyConfiguration;
import okhttp3.Headers;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;


public class CleafyCordovaPlugin extends CordovaPlugin {
    public static final String TAG = "CleafyCordovaPlugin";
    private static final Map<String, BiConsumer<JSONArray, CallbackContext>> actions = new HashMap<>();

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        actions.put("initWithConfiguration", this::initWithConfiguration);

        actions.put("invalidateSession", this::invalidateSession);
        actions.put("updateDetection", this::updateDetection);

        actions.put("getDiagnostics", this::getDiagnostics);
        actions.put("registerDiagnosticsListener", this::registerDiagnosticsListener);

        actions.put("getSessionId", this::getSessionId);
        actions.put("getBrowserId", this::getBrowserId);
        actions.put("getParentId", this::getParentId);
        actions.put("getApplicationHostname", this::getApplicationHostname);
        actions.put("getVersion", this::getVersion);
        actions.put("getDeviceId", this::getDeviceId)

        actions.put("setLocation", this::setLocation);
        actions.put("setAppSessionId", this::setAppSessionId);
        actions.put("setAppDeviceId", this::setAppDeviceId);
        actions.put("setUserId", this::setUserId);

        actions.put("registerLocationListener", this::registerLocationListener);
    }

    @Override
    public boolean execute(String action, final JSONArray data, final CallbackContext callbackContext) {
        final BiConsumer<JSONArray, CallbackContext> runMe = actions.get(action);

        if (runMe == null) {
            callbackContext.error("No such action: " + action);
            return false;
        }

        cordova.getActivity().runOnUiThread(() -> {
            try {
                runMe.accept(data, callbackContext);
            } catch (Exception e) {
                callbackContext.error(e.getMessage());
            }
        });
        return true;
    }

    private void initWithConfiguration(JSONArray data, CallbackContext callbackContext) {
        try {
            CleafyConfiguration configuration = JSONUtils.parseCleafyConfiguration(data.getJSONObject(0));
            Cleafy.initWithConfiguration(cordova.getActivity().getApplication(), configuration);
            callbackContext.success("Agent initialized");
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }


    private void invalidateSession(JSONArray data, CallbackContext callbackContext) {
        Cleafy.invalidateSession();
        callbackContext.success();
    }

    private void updateDetection(JSONArray data, CallbackContext callbackContext) {
        Cleafy.updateDetection();
        callbackContext.success();
    }


    private void getDiagnostics(JSONArray jsonArray, CallbackContext callbackContext) {
        try {
            JSONObject diagnosticsJson = JSONUtils.serializeAgentDiagnostics(Cleafy.getDiagnostics());
            callbackContext.success(diagnosticsJson);
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }

    private void registerDiagnosticsListener(JSONArray jsonArray, CallbackContext callbackContext) {
        Cleafy.registerDiagnosticsListener(
                (agentDiagnosticsProvider, error) -> {
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, error);
                    pluginResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(pluginResult);
                }
        );
    }


    private void getSessionId(JSONArray data, CallbackContext callbackContext) {
        callbackContext.success(Cleafy.getSessionId());
    }

    private void getBrowserId(JSONArray data, CallbackContext callbackContext) {
        callbackContext.success(Cleafy.getBrowserId());
    }

    private void getParentId(JSONArray data, CallbackContext callbackContext) {
        callbackContext.success(Cleafy.getParentId());
    }

    private void getApplicationHostname(JSONArray data, CallbackContext callbackContext) {
        callbackContext.success(Cleafy.getApplicationHostname());
    }

    private void getVersion(JSONArray data, CallbackContext callbackContext) {
        callbackContext.success(Cleafy.getVersion());
    }

    private void getDeviceId(JSONArray data, CallbackContext callbackContext) {
        callbackContext.success(Cleafy.getDeviceId());
    }

    private void setLocation(JSONArray data, CallbackContext callbackContext) {
        try {
            Cleafy.setLocation(data.getString(0));
            callbackContext.success();
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }

    private void setAppSessionId(JSONArray data, CallbackContext callbackContext) {
        try {
            Cleafy.setAppSessionId(data.getString(0));
            callbackContext.success();
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }

    private void setAppDeviceId(JSONArray data, CallbackContext callbackContext) {
        try {
            Cleafy.setAppDeviceId(data.getString(0));
            callbackContext.success();
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }

    private void setUserId(JSONArray data, CallbackContext callbackContext) {
        try {
            Cleafy.setUserId(data.getString(0));
            callbackContext.success();
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }

    private JSONArray savedRegisterLocationParams = null;
    private CallbackContext savedRegisterLocationCallbackContext = null;

    private void registerLocationListener(JSONArray data, CallbackContext callbackContext) {
        if (!cordova.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            this.savedRegisterLocationParams = data;
            this.savedRegisterLocationCallbackContext = callbackContext;
            cordova.requestPermission(this, PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_COARSE_LOCATION);
            return;
        }
        try {
            String requestedProvider = null;
            if (!data.isNull(0)) {
                requestedProvider = data.getString(0);
            }
            long minTime = 2500;
            if (!data.isNull(1)) {
                minTime = data.getLong(1);
            }
            double minDistance = 500f;
            if (!data.isNull(2)) {
                minDistance = data.getDouble(3);
            }
            LocationListenerUtils.registerLocationListener(
                    cordova.getContext(),
                    requestedProvider,
                    minTime,
                    (float) minDistance,
                    callbackContext
            );
            callbackContext.success();
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }

    private static final int PERMISSION_REQUEST_CODE = 1562490;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) return;
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registerLocationListener(savedRegisterLocationParams, savedRegisterLocationCallbackContext);
        } else {
            savedRegisterLocationCallbackContext.error("Location permission not granted.");
        }
        savedRegisterLocationParams = null;
        savedRegisterLocationCallbackContext = null;
    }
}
