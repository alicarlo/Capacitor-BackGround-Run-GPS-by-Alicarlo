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
    async showNotificationOnAppClose(options) {
        return options;
    }
    async stopNotificationService() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
    async checkNotificationPermission() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
    async openNotificationSettings() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
    async checkPermissionsService(options) {
        return options;
    }
    async ignoringBatteryOptimizationsService() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
    async openLocationSettings() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
    async addAppResumedListener(callback) {
        callback();
    }
    async requestBatteryOptimizations() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
    async requestNotificationPermission() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
    async checkUsageStatsNotificationPausePermission(options) {
        return options;
    }
    async checkManageAppPermissionsPermission(options) {
        return options;
    }
    async acquireWakeLock() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
    async releaseWakeLock() {
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
