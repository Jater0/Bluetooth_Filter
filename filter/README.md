# Tymphany-Bluetooth-Filter:1.0.0-stable
### How to use
- #### Step 1:
    put the 'aar' into Directory 'libs' and the copy this code

    ```
    <build.gradle(:app)>
    
    repositories{
        flatDir{
            dirs "libs"
        }
    }
    dependencies {
        implementation fileTree(includes: ['*.jar'], dir: 'libs')
        implementation(name: "filter:{version}", ext: 'aar')
        implementation 'com.google.code.gson:gson:2.8.6' // and this      
            ....
    }
    ```

- #### Step 2:
    We have 2 options to use this Filter
    ```
    Option 1:
    create Directory 'asserts' and File 'your_file_name.json'
    <your_file_name.json>
    
    {
        "version": "your json file version",
        "useful": 1,
        "mac_host": "your_host",
        "mac_address_list": ["00:00:01", ......]           
    }
  
  
    <Java File>  
    
    List<BluetoothDevice> devices = ... // this devices what you scan  
    Filter filter = new Filter();
    List<BluetoothDevice> devices_filter = filter.doFilter(devices, context, "your_file_name.json");
    
  
    
    Option 2:
    implement a List<String>
    <Java File>
      
    List<BluetoothDevice> devices = ... // this devices what you scan 
    List<String> macs = new ArrayList<>();
    macs.add("00:00:01");
    macs.add(........"");
    Filter filter = new Filter();
    List<BluetoothDevice> device_filter = filter.doFilter(devices, macs);               
    ```