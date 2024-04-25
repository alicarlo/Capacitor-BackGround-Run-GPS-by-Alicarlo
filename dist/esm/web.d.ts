import { WebPlugin } from '@capacitor/core';
import type { backgroundrunPlugin } from './definitions';
export declare class backgroundrunWeb extends WebPlugin implements backgroundrunPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    showNotificationOnAppClose(context: any): Promise<void>;
}
