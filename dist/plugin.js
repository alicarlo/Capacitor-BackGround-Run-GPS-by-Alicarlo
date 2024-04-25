var capacitorbackgroundrun = (function (exports, core, app, localNotifications) {
    'use strict';

    const backgroundrun = core.registerPlugin('backgroundrun', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.backgroundrunWeb()),
    });

    class backgroundrunWeb extends core.WebPlugin {
        async echo(options) {
            console.log('ECHO', options);
            return options;
        }
        async showNotificationOnAppClose(context) {
            app.App.addListener('appStateChange', async (state) => {
                if (!state.isActive) {
                    const packageName = context.getPackageName();
                    const iconId = context.getResources().getIdentifier("ic_notification", "drawable", packageName);
                    /*await LocalNotifications.schedule({
                      notifications: [{
                        title: '¡Hasta luego!',
                        body: 'La aplicación se ha cerrado.',
                        id: 1,
                        schedule: { at: new Date(Date.now() + 1000) },
                        actionTypeId: '',
                        extra: null,
                                    iconId: iconId
                      }]
                    });*/
                    const notification = {
                        title: '¡Hasta luego!',
                        body: 'La aplicación se ha cerrado.',
                        id: 1,
                        schedule: { at: new Date(Date.now() + 1000) },
                        actionTypeId: '',
                        extra: null,
                        iconId: iconId
                    };
                    await localNotifications.LocalNotifications.schedule({ notifications: [notification] });
                }
            });
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        backgroundrunWeb: backgroundrunWeb
    });

    exports.backgroundrun = backgroundrun;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports, app, localNotifications);
//# sourceMappingURL=plugin.js.map
