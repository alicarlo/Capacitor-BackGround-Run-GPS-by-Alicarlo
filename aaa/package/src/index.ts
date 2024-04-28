import { registerPlugin } from '@capacitor/core';

import type { backgroundrunPlugin } from './definitions';

const backgroundrun = registerPlugin<backgroundrunPlugin>('backgroundrun', {
  web: () => import('./web').then(m => new m.backgroundrunWeb()),
});

export * from './definitions';
export { backgroundrun };
