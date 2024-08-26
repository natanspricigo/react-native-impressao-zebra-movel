import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-impressao-zebra-movel' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const RNImpressaoZebraMovel = NativeModules.RNImpressaoZebraMovel
  ? NativeModules.RNImpressaoZebraMovel
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function connect(address: string): Promise<object> {
  return RNImpressaoZebraMovel.connect(address);
}

export function disconnect(): Promise<object> {
  return RNImpressaoZebraMovel.disconnect();
}

export function enableBluetooth(): Promise<boolean> {
  return RNImpressaoZebraMovel.enableBluetooth();
}

export function isEnabledBluetooth(): Promise<boolean> {
  return RNImpressaoZebraMovel.isEnabledBluetooth();
}

export function getPairedDevices(): Promise<Array<any>> {
  return RNImpressaoZebraMovel.getPairedDevices();
}

export function printZebraZpl(command: string): Promise<boolean> {
  return RNImpressaoZebraMovel.printZebraZpl(command);
}

export function isConnectedPrinterZebra(): Promise<boolean> {
  return RNImpressaoZebraMovel.isConnectedPrinterZebra();
}

export function getZebraParameter(param: string): Promise<boolean> {
  return RNImpressaoZebraMovel.getZebraParameter(param);
}

export function setZebraParameter(
  param: string,
  value: string
): Promise<boolean> {
  return RNImpressaoZebraMovel.setZebraParameter(param, value);
}

export function configurePrinter(): Promise<boolean> {
  return RNImpressaoZebraMovel.configurePrinter();
}
