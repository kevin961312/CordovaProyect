# Cleafy Mobile SDK Plugin for Cordova

### Build & Package instructions

- `npm run build`
- `npm pack`

### Install instructions

- Install node dependencies in both the plugin and example directories with `npm i`
- Install the plugin into the example project
  - For development: use `cordova plugin add .. --link` from the example directory
  - With NPM .tgz package:
    - Copy the .tgz archive somewhere inside the example project (e.g. example/libs/cleafy-cordova-plugin-VERSION.tgz)
    - Add the .tgz archive as an NPM package `npm add ./libs/cleafy-cordova-plugin-VERSION.tgz`
    - Install the plugin with `cordova plugin add cleafy-cordova-plugin`
- Prepare the example with `cordova prepare`
- Build the example with `cordova build`
- Run the example with `cordova run [ios|android]`

### Notes

- It is advisable to remove and reinstall the plugin after every change
- If example project natives break, remove and re-add them (`cordova platform [remove|add] [android|ios]`)
