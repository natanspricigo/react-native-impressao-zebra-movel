# react-native-impressao-zebra-movel

Impressao mobile para impressoras Zebra

## Installation

```sh
npm install react-native-impressao-zebra-movel
```

## Usage

 Retornar todos os dispositivos pareados no celular

```js
import { getPairedDevices } from 'react-native-impressao-zebra-movel';
// ...
const devices = await getPairedDevices();
```

 Conecta a uma impressora Zebra através de um endereço, que pode s er obtido na função getPairedDevices

```js
import { connect } from 'react-native-impressao-zebra-movel';
// ...
const status = await connect('enredeço');

```


 Ativa o Bluetooth do celular

```js
import { enableBluetooth } from 'react-native-impressao-zebra-movel';
// ...
const status = await enableBluetooth();

```

 Verifica se o bluetooth do celular esta ativo

```js
import { isEnabledBluetooth } from 'react-native-impressao-zebra-movel';
// ...
const status = await isEnabledBluetooth();

```


 Imprime um texto no formato ZPL na impressora conectada.

```js
import { printZebraZpl } from 'react-native-impressao-zebra-movel';
// ...
const status = await printZebraZpl(ZPL);

```

 executa algumas configurações para que a impressora tenha uma pre-configuração: 
#### device.languages", "zpl"
#### media.type", "label"
#### media.sense_mode", "bar"

```js
import { configurePrinter } from 'react-native-impressao-zebra-movel';
// ...
const status = await configurePrinter();

```


## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
