import { WebPlugin } from '@capacitor/core';
import { App } from '@capacitor/app';
import { LocalNotifications, LocalNotificationSchema } from '@capacitor/local-notifications';
// const { App, LocalNotifications } = Plugins;
import type { backgroundrunPlugin } from './definitions';

interface ExtendedLocalNotificationSchema extends LocalNotificationSchema {
  iconId: number;
}

export class backgroundrunWeb extends WebPlugin implements backgroundrunPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

	async showNotificationOnAppClose(context: any) {
    App.addListener('appStateChange', async (state: any) => {
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

				const notification: ExtendedLocalNotificationSchema = {
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
