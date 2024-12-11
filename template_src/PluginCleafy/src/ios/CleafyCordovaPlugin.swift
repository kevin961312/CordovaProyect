import Cleafy
import Foundation
import CoreMotion

@objc(CleafyCordovaPlugin)
class CleafyCordovaPlugin: CDVPlugin {
  @objc(initWithConfiguration:)
  func initWithConfiguration(command: CDVInvokedUrlCommand) {
    guard let configDict = command.arguments[0] as? [String: Any] else {
      success(command, with: "Invalid Dictionary [String: Any]")
      return
    }
    
    let detectorsDict = configDict["detectorsConfiguration"] as? [String: Any] ?? [:]
    
    let detectorsConfiguration = CleafyDetectorsConfiguration(
      monitoredAppEnabled: detectorsDict["isMonitoredAppEnabled"] as? Bool ?? DefaultConfiguration.monitoredAppEnabled,
      httpDetectorEnabled: detectorsDict["isHttpDetectorEnabled"] as? Bool ?? DefaultConfiguration.httpDetectorEnabled,
      certDetectorEnabled: detectorsDict["isCertDetectorEnabled"] as? Bool ?? DefaultConfiguration.certDetectorEnabled,
      certDetectorEndpoint: detectorsDict["certDetectorEndpoint"] as? String ?? "",
      advancedCertDetectorEnabled: detectorsDict["isAdvancedHttpCertDetectorEnabled"] as? Bool ?? DefaultConfiguration.advancedCertDetectorEnabled,
      rootDetectorEnabled: detectorsDict["isRootDetectorEnabled"] as? Bool ?? DefaultConfiguration.rootDetectorEnabled,
      mockLocationDetectorEnabled: detectorsDict["isMockLocationDetectorEnabled"] as? Bool ?? DefaultConfiguration.mockLocationDetectorEnabled,
      onCallDetectorEnabled: detectorsDict["isOnCallDetectorEnabled"] as? Bool ?? DefaultConfiguration.onCallDetectorEnabled,
      humanDetectorEnabled: detectorsDict["isHumanDetectorEnabled"] as? Bool ?? DefaultConfiguration.humanDetectorEnabled
    )
    
    let configuration = CleafyConfiguration(
      apiEndpoint: configDict["apiEndpoint"] as! String,
      applicationHostname: configDict["applicationHostname"] as! String,
      defaultEnabled: configDict["isDefaultEnabled"] as? Bool ?? DefaultConfiguration.defaultEnabled,
      automaticUpdateAnalysis: configDict["automaticUpdateAnalysis"] as? AutomaticUpdateAnalysis ?? DefaultConfiguration.automaticUpdateAnalysis,
      allowUntrustedCertificates: configDict["isAllowUntrustedCertificates"] as? Bool ?? DefaultConfiguration.allowUntrustedCertificates,
      detectors: detectorsConfiguration
    )
    
    let motionManager = detectorsConfiguration.isHumanDetectorEnabled ? CMMotionManager() : nil;
    
    Cleafy.initWith(configuration: configuration, motionManager: motionManager)
    success(command, with: "Agent initialized")
  }
  
  @objc(invalidateSession:)
  func invalidateSession(command: CDVInvokedUrlCommand) {
    Cleafy.invalidateSession()
    success(command)
  }
  
  @objc(updateDetection:)
  func updateDetection(command: CDVInvokedUrlCommand) {
    Cleafy.updateDetection()
    success(command)
  }
}

extension CleafyCordovaPlugin {
  func failure(_ command: CDVInvokedUrlCommand, with data: Any) {
    commandDelegate.run {
      self.commandDelegate.send(
        CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: [data]),
        callbackId: command.callbackId
      )
    }
  }
  
  func success(_ command: CDVInvokedUrlCommand) {
    commandDelegate.run {
      self.commandDelegate.send(
        CDVPluginResult(status: CDVCommandStatus_OK),
        callbackId: command.callbackId
      )
    }
  }
  
  func success(_ command: CDVInvokedUrlCommand, with data: Any) {
    commandDelegate.run {
      self.commandDelegate.send(
        CDVPluginResult(status: CDVCommandStatus_OK, messageAs: [data]),
        callbackId: command.callbackId
      )
    }
  }
}
