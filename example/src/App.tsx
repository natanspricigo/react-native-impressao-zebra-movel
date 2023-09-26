import * as React from 'react';

import { StyleSheet, View, Text, Alert, Button, TouchableOpacity } from 'react-native';
import { connect, enableBluetooth, isEnabledBluetooth, getPairedDevices, printZebraZpl, configurePrinter } from 'react-native-impressao-zebra-movel';

export default function App() {

  const [devices, setDevices] = React.useState<Array<object>>([]);
  const [bluetoothenable, setBluetoothenable] = React.useState<boolean | undefined>();

  let load = async () => {
    let r: boolean = await isEnabledBluetooth();
    setBluetoothenable(r);
    if (!r) {
      r = await enableBluetooth();
      setBluetoothenable(r);
    }
    getPairedDevices().then(d => {
      setDevices(d);
      console.log(d);
    }).catch(e => {
      console.log(e);
      Alert.alert(e.message);
    });
  }

  React.useEffect(() => {
    load()
  }, []);

  let handleClick = () => {
    getPairedDevices().then(d => {
      setDevices(d);
      console.log(d);
    }).catch(e => {
      console.log(e);
      Alert.alert(e.message);
    });
  }

  let handleConnect = (dev:any)=>{
    connect(dev.macAddress).then(r=>{
      console.log(r);
      configurePrinter();
    }).catch(e=>{
      console.log(e);
    });
  }

  let printExemple = ()=>{
    printZebraZpl(`^XA
    ^FO50,60^A0,40^FDWorld's Best Griddle^FS
    ^FO60,120^BY3^BCN,60,,,,A^FD1234ABC^FS
    ^FO25,25^GB380,200,2^FS
    ^XZ`)
  }

  return (
    <View style={styles.container}>

      <Button
        title="atualizar"
        onPress={handleClick}
      />
      <Text>Bluetooth ativo ? {bluetoothenable ? "ATIVO" : "DESATIVADO"}</Text>

      <View>
        {
          devices.map((dev:any, i) => {
            return <View style={{ marginVertical: 3 }} key={i}>
              <TouchableOpacity style={{ backgroundColor: 'blue', padding: 10, minHeight: 10}} onPress={()=>handleConnect(dev)}>
                <Text style={{color: "#fff"}}>{dev.portName || ""}</Text>
              </TouchableOpacity>
            </View>
          })
        }
      </View>

      <View>

      <TouchableOpacity style={{ backgroundColor: 'yellow', padding: 10, minHeight: 10}} onPress={()=>printExemple()}>
        <Text style={{color: "#fff"}}>Print</Text>
      </TouchableOpacity>
      
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20
  }
});
