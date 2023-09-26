# react-native-impressao-zebra-movel

Impressao mobile para impressoras Zebra

## Installation

```sh
npm install react-native-impressao-zebra-movel
```

## Usage

### Retornar todos os dispositivos pareados no celular

```js
import { getPairedDevices } from 'react-native-impressao-zebra-movel';
// ...
const devices = await getPairedDevices();
```

### Conecta a uma impressora Zebra através de um endereço, que pode s er obtido na função getPairedDevices

```js
import { connect } from 'react-native-impressao-zebra-movel';
// ...
const status = await connect('enredeço');
```

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
