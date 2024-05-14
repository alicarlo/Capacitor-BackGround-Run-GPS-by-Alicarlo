import { WebPlugin } from '@capacitor/core';
export class backgroundrunWeb extends WebPlugin {
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
    async addAppResumedListener() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
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
//# sourceMappingURL=web.js.map