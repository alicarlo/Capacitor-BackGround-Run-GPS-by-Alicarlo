var capacitorbackgroundrun = (function (exports, core) {
    'use strict';

    const backgroundrun = core.registerPlugin('backgroundrun', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.backgroundrunWeb()),
    });

    class backgroundrunWeb extends core.WebPlugin {
        async echo(options) {
            console.log('ECHO', options);
            return options;
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        backgroundrunWeb: backgroundrunWeb
    });

    exports.backgroundrun = backgroundrun;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
