import { registerPlugin } from '@capacitor/core';
const backgroundrun = registerPlugin('backgroundrun', {
    web: () => import('./web').then(m => new m.backgroundrunWeb()),
});
export * from './definitions';
export { backgroundrun };
//# sourceMappingURL=index.js.map