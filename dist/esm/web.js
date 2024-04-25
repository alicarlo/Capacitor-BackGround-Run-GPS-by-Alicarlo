import { WebPlugin } from '@capacitor/core';
import { App } from '@capacitor/app';
import { LocalNotifications } from '@capacitor/local-notifications';
export class backgroundrunWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async showNotificationOnAppClose() {
        App.addListener('appStateChange', async (state) => {
            if (!state.isActive) {
                await LocalNotifications.schedule({
                    notifications: [{
                            title: '¡Hasta luego!',
                            body: 'La aplicación se ha cerrado.',
                            id: 1,
                            schedule: { at: new Date(Date.now() + 1000) },
                            actionTypeId: '',
                            extra: null
                        }]
                });
            }
        });
    }
}
//# sourceMappingURL=web.js.map