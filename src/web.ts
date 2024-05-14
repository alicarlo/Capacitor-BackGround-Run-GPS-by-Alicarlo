import { WebPlugin} from '@capacitor/core';
// import { App } from '@capacitor/app';
// import { LocalNotifications, LocalNotificationSchema } from '@capacitor/local-notifications';
// const { App, LocalNotifications } = Plugins;
import type { backgroundrunPlugin, GpsOptions } from './definitions';


export class backgroundrunWeb extends WebPlugin implements backgroundrunPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

	async showNotificationOnAppClose(options: GpsOptions): Promise<GpsOptions> {
    return options;
  }

	async stopNotificationService(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }

	async checkNotificationPermission(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }

	async openNotificationSettings(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }

	async checkPermissionsService(options: { value: boolean }): Promise<{ value: boolean }> {
		return options;
  }

	async ignoringBatteryOptimizationsService(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }

	async openLocationSettings(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }


	/*async addAppResumedListener(callback: () => void): Promise<void> {
		callback();
	}*/

	async addAppResumedListener(): Promise<void> {
    window.addEventListener('appResumed', () => {
      this.notifyListeners('appResumed', {});
    });
    return Promise.resolve();
  }

	async requestBatteryOptimizations(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }
	
	async requestNotificationPermission(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }

	async checkUsageStatsNotificationPausePermission(options: { value: boolean }): Promise<{ value: boolean }> {
		return options;
  }

	async checkManageAppPermissionsPermission(options: { value: boolean }): Promise<{ value: boolean }> {
    return options;
  }

	async acquireWakeLock(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }

	async releaseWakeLock(): Promise<void> {
    throw new Error('Method not implemented.');
    // Implementa aquí la lógica para mostrar la notificación en la web
  }


}
