package com.impressaozebramovel;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.zebra.sdk.comm.BluetoothConnectionInsecure;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionBuilder;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.SGD;

import java.util.Set;

@ReactModule(name = RNImpressaoZebraMovelModule.NAME)
public class RNImpressaoZebraMovelModule extends ReactContextBaseJavaModule {
  public static final String NAME = "RNImpressaoZebraMovel";

  private final ReactApplicationContext reactContext;

  public BluetoothManager bluetoothManager;
  private BluetoothConnectionInsecure connection;
  public static Context context;
  private static final String PORT_NAME = "portName";
  private static final String MAC_ADDRESS = "macAddress";
  private static final String MODULE_NAME = "moduleName";

  public void getBluetoothManagerInstance(Context c) {
    this.bluetoothManager = (BluetoothManager) c.getSystemService(Context.BLUETOOTH_SERVICE);
  }

  public RNImpressaoZebraMovelModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    context = getReactApplicationContext();
    this.getBluetoothManagerInstance(context);
    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
  }


  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void enableBluetooth(final Promise promise) {
    try {
      this.reactContext.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1, null);
      promise.resolve(true);
    } catch (Exception e) {
      promise.reject(e);
    } // enable bluetooth
  }

  @ReactMethod
  public void isEnabledBluetooth(final Promise promise) { // check if the bluetooth is enabled or not
    if (this.bluetoothManager.getAdapter() == null || !this.bluetoothManager.getAdapter().isEnabled()) {
      promise.resolve(false);
    } else {
      promise.resolve(true);
    }
  }

  @ReactMethod
  public void getPairedDevices(final Promise promise) { // scan for unpaired devices
    WritableNativeMap port = new WritableNativeMap();
    WritableNativeArray result = new WritableNativeArray();
    try {
      ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_PRIVILEGED}, 10);
      Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();

      if (pairedDevices.size() > 0) {
        // There are paired devices. Get the name and address of each paired device.
        for (BluetoothDevice device : pairedDevices) {
          port = new WritableNativeMap();
          port.putString(PORT_NAME, device.getName());
          port.putString(MAC_ADDRESS, device.getAddress());
          port.putString(MODULE_NAME, device.getName());
          port.putInt("STATE", device.getBondState());
          port.putInt("TYPE", device.getType());
          result.pushMap(port);
        }
      }
      promise.resolve(result);
    } catch (Exception exception) {
      promise.reject("PORT_DISCOVERY_ERROR", exception.getMessage());
    }
  }
  @ReactMethod
  public void connect(String address, final Promise promise) {
    BluetoothAdapter adapter = this.bluetoothManager.getAdapter();
    if (adapter != null && adapter.isEnabled()) {
      BluetoothDevice device = adapter.getRemoteDevice(address);
      try {
        connection = new BluetoothConnectionInsecure(address);
        connection.open();
        promise.resolve(connection.isConnected());
      }catch(ConnectionException e){
        promise.reject(e);
      }
    } else {
      promise.reject("error","BT NOT ENABLED");
    }
  }
  @ReactMethod
  public void printZebraZpl(String commands, final Promise promise) {
    BluetoothAdapter adapter = this.bluetoothManager.getAdapter();
    try {
      if (adapter != null && adapter.isEnabled() && connection != null) {
        connection.open();
        byte[] snd = commands.getBytes();
        connection.write(snd);
        connection.write("\r\n".getBytes());
        promise.resolve(true);
      }else{
        promise.reject("ERRO", "Conexão fechada ou bluetooth desativado");
      }
    } catch(ConnectionException e){
      promise.reject(e);
    }
  }
  @ReactMethod
  public void configurePrinter(final Promise promise) {
    byte[] configLabel = null;
    try {
      SGD.SET("device.languages", "zpl", connection);
      SGD.SET("media.type", "label", connection);
      SGD.SET("media.sense_mode", "bar", connection);
      //SGD.SET("zpl.label_length", "1800", connection); // nao sei se é necessário esse
    } catch (ConnectionException e) {
      promise.reject(e);
    }
    promise.resolve(true);
  }

  @ReactMethod
  public void isConnected(final Promise promise) {
    if (connection == null) {
      promise.resolve(false);
    }
    promise.resolve(connection.isConnected());
  }
}
