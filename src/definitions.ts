

export interface backgroundrunPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
	showNotificationOnAppClose(context: any) : Promise<void>;
}
