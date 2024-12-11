import Cleafy

extension CleafyCordovaPlugin {
  @objc(getSessionId:)
  func getSessionId(command: CDVInvokedUrlCommand) {
    success(command, with: Cleafy.sessionId)
  }
  
  @objc(getBrowserId:)
  func getBrowserId(command: CDVInvokedUrlCommand) {
    success(command, with: Cleafy.browserId)
  }
  
  @objc(getParentId:)
  func getParentId(command: CDVInvokedUrlCommand) {
    success(command, with: Cleafy.parentId)
  }
  
  @objc(getApplicationHostname:)
  func getApplicationHostname(command: CDVInvokedUrlCommand) {
    success(command, with: Cleafy.applicationHostname)
  }
  
  @objc(getVersion:)
  func getVersion(command: CDVInvokedUrlCommand) {
    success(command, with: Cleafy.version)
  }

  @objc(getDeviceId:)
  func getDeviceId(command: CDVInvokedUrlCommand) {
    success(command, with: Cleafy.deviceId)
  }
}
