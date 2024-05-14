

export interface backgroundrunPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
	showNotificationOnAppClose(options: GpsOptions) : Promise<GpsOptions>;
	stopNotificationService() : Promise<void>;
	checkPermissionsService(options: {value: boolean}) : Promise<{value: boolean}>;
	ignoringBatteryOptimizationsService() : Promise<void>;
	requestBatteryOptimizations() : Promise<void>;
	requestNotificationPermission() : Promise<void>;
	checkUsageStatsNotificationPausePermission(options: {value: boolean}) : Promise<{value: boolean}>;
	checkManageAppPermissionsPermission(options: {value: boolean}) : Promise<{value: boolean}>;
	checkNotificationPermission() : Promise<void>;
	openNotificationSettings() : Promise<void>;
	acquireWakeLock() : Promise<void>;
	releaseWakeLock() : Promise<void>;
	openLocationSettings() : Promise<void>;
}

export interface GpsOptions {
	url: String;
	id1: String;
	id2: String;
	id3: String;
	id4: String;
	title: String;
	timerGps: Number;
	coordinatesShow: boolean;
	timeShow: boolean;
}
