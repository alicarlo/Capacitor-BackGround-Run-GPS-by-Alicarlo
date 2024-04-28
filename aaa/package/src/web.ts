import { WebPlugin } from '@capacitor/core';
// import { App } from '@capacitor/app';
// import { LocalNotifications, LocalNotificationSchema } from '@capacitor/local-notifications';
// const { App, LocalNotifications } = Plugins;
import type { backgroundrunPlugin } from './definitions';


export class backgroundrunWeb extends WebPlugin implements backgroundrunPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

	async showNotificationOnAppClose(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }

	async stopNotificationService(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }
	

}
