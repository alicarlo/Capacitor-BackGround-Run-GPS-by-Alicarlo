import { WebPlugin } from '@capacitor/core';
export class backgroundrunWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async showNotificationOnAppClose() {
        throw new Error('Method not implemented.');
        // Implementa aquí la lógica para mostrar la notificación en la web
    }
}
//# sourceMappingURL=web.js.map