import { WebPlugin } from '@capacitor/core';
import { App } from '@capacitor/app';
import { LocalNotifications } from '@capacitor/local-notifications';
export class backgroundrunWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async showNotificationOnAppClose(context) {
        App.addListener('appStateChange', async (state) => {
            if (!state.isActive) {
                const packageName = context.getPackageName();
                const iconId = context.getResources().getIdentifier("ic_notification", "drawable", packageName);
                /*await LocalNotifications.schedule({
                  notifications: [{
                    title: '¡Hasta luego!',
                    body: 'La aplicación se ha cerrado.',
                    id: 1,
                    schedule: { at: new Date(Date.now() + 1000) },
                    actionTypeId: '',
                    extra: null,
                                iconId: iconId
                  }]
                });*/
                const notification = {
                    title: '¡Hasta luego!',
                    body: 'La aplicación se ha cerrado.',
                    id: 1,
                    schedule: { at: new Date(Date.now() + 1000) },
                    actionTypeId: '',
                    extra: null,
                    iconId: iconId
                };
                await LocalNotifications.schedule({ notifications: [notification] });
            }
        });
    }
}
//# sourceMappingURL=web.js.map