export interface backgroundrunPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    showNotificationOnAppClose(): Promise<void>;
}
