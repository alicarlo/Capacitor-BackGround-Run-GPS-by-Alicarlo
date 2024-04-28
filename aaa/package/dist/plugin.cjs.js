'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const backgroundrun = core.registerPlugin('backgroundrun', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.backgroundrunWeb()),
});

class backgroundrunWeb extends core.WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async showNotificationOnAppClose() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
    async stopNotificationService() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    backgroundrunWeb: backgroundrunWeb
});

exports.backgroundrun = backgroundrun;
//# sourceMappingURL=plugin.cjs.js.map
