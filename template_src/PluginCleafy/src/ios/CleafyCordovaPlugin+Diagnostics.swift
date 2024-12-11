import Cleafy

extension CleafyCordovaPlugin {
  @objc(getDiagnostics:)
  func getDiagnostics(command: CDVInvokedUrlCommand) {
    let diagnostics = Cleafy.getDiagnostics()
    var traces: [[String: Any]] = []
    
    for trace in diagnostics.probeTraces {
      let traceDict: [String: Any] = [
        "eventId": trace.eventId,
        "sessionId": trace.sessionId,
        "statusCode": trace.statusCode,
        "timestamp": trace.timestamp,
      ]
      traces.append(traceDict)
    }
    
    let diagnosticsDict: [String: Any] = [
      "deviceId": diagnostics.deviceId,
      "probeTraces": traces,
      "errors": diagnostics.errors,
      "successfulProbeSubmissions": diagnostics.successfulProbeSubmissions,
      "failedProbeSubmissions": diagnostics.failedProbeSubmissions,
      "lastConfigurationUpdateTimestamp": diagnostics.lastConfigurationUpdateTimestamp,
    ]
    
    success(command, with: diagnosticsDict)
  }
  
  private struct CordovaPluginAgentDiagnosticsListener: AgentDiagnosticsListener {
    private weak var plugin: CleafyCordovaPlugin?
    private let callbackId: String
    
    init(plugin: CleafyCordovaPlugin, callbackId: String) {
      self.plugin = plugin
      self.callbackId = callbackId
    }
    
    func onDiagnosticsWithErrorAvailable(diagnosticsProvider: any Cleafy.AgentDiagnosticsProvider, error: String) {
      let pluginResult: CDVPluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: [error])
      pluginResult.keepCallback = true
      plugin?.commandDelegate?.send(pluginResult, callbackId: callbackId)
    }
  }
  
  @objc(registerDiagnosticsListener:)
  func registerDiagnosticsListener(command: CDVInvokedUrlCommand) {
    let diagnosticsListener: any AgentDiagnosticsListener = CordovaPluginAgentDiagnosticsListener(plugin: self, callbackId: command.callbackId)
    Cleafy.registerDiagnosticsListener(listener: diagnosticsListener)
  }
}
