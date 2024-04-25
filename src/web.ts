import { WebPlugin } from '@capacitor/core';

import type { backgroundrunPlugin } from './definitions';

export class backgroundrunWeb extends WebPlugin implements backgroundrunPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

}
