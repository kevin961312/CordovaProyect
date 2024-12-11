import Cleafy

extension CleafyCordovaPlugin {
  @objc(setLocation:)
  func setLocation(command: CDVInvokedUrlCommand) {
    guard let location = command.arguments[0] as? String else {
      failure(command, with: "Invalid Location")
      return
    }
    Cleafy.setLocation(location)
    success(command, with: "Location set!")
  }
  
  @objc(setAppSessionId:)
  func setAppSessionId(command: CDVInvokedUrlCommand) {
    guard let appSessionID = command.arguments[0] as? String else {
      failure(command, with: "Invalid app session")
      return
    }
    Cleafy.setAppSessionID(appSessionID)
    success(command, with: "New app session ID set!")
  }
  
  @objc(setUserId:)
  func setUserId(command: CDVInvokedUrlCommand) {
    guard let userID = command.arguments[0] as? String else {
      failure(command, with: "Invalid user ID")
      return
    }
    Cleafy.setUserID(userID)
    success(command, with: "New user ID set!")
  }
  
  @objc(setAppDeviceId:)
  func setAppDeviceId(command: CDVInvokedUrlCommand) {
    guard let appDeviceID = command.arguments[0] as? String else {
      failure(command, with: "Invalid app device ID")
      return
    }
    Cleafy.setAppDeviceID(appDeviceID)
    success(command, with: "New app device ID")
  }
}
