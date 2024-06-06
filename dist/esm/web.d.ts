import { PluginListenerHandle, WebPlugin } from '@capacitor/core';
import type { backgroundrunPlugin, GpsOptions } from './definitions';
export declare class backgroundrunWeb extends WebPlugin implements backgroundrunPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    showNotificationOnAppClose(options: GpsOptions): Promise<GpsOptions>;
    stopNotificationService(): Promise<void>;
    checkNotificationPermission(): Promise<void>;
    openNotificationSettings(): Promise<void>;
    checkPermissionsService(options: {
        value: boolean;
    }): Promise<{
        value: boolean;
    }>;
    ignoringBatteryOptimizationsService(): Promise<void>;
    openLocationSettings(): Promise<void>;
    addAppResumedListener(): Promise<void>;
    addListener(eventName: string, listenerFunc: (...args: any[]) => void): Promise<PluginListenerHandle>;
    clearAppResumedListener(): Promise<void>;
    requestBatteryOptimizations(): Promise<void>;
    requestNotificationPermission(): Promise<void>;
    checkUsageStatsNotificationPausePermission(options: {
        value: boolean;
    }): Promise<{
        value: boolean;
    }>;
    checkManageAppPermissionsPermission(options: {
        value: boolean;
    }): Promise<{
        value: boolean;
    }>;
    acquireWakeLock(): Promise<void>;
    releaseWakeLock(): Promise<void>;
    checkPermissionCamera(options: {
        value: boolean;
    }): Promise<{
        value: boolean;
    }>;
    checkPermissionMicroPhone(options: {
        value: boolean;
    }): Promise<{
        value: boolean;
    }>;
    checkPermissionAudio(options: {
        value: boolean;
    }): Promise<{
        value: boolean;
    }>;
}
