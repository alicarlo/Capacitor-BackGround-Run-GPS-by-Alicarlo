import { WebPlugin } from '@capacitor/core';
import { App } from '@capacitor/app';
import { LocalNotifications } from '@capacitor/local-notifications';
// const { App, LocalNotifications } = Plugins;
import type { backgroundrunPlugin } from './definitions';

export class backgroundrunWeb extends WebPlugin implements backgroundrunPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

	async showNotificationOnAppClose() {
    App.addListener('appStateChange', async (state: any) => {
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
