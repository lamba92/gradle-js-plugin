const TerserPlugin = require('terser-webpack-plugin');

const entryPoint = '%ENTRY_POINT%'
const outputDirPath = '%OUTPUT_DIR_PATH%';
const outputFileName = '%OUTPUT_FILE_NAME%';
const nodeModulesDir = '%NODE_MODULES_DIR%';

module.exports = [{
    mode: 'production',
    entry: entryPoint,
    target: 'node',
    output: {
        path: outputDirPath,
        filename: outputFileName,
        library: {
            name: 'handler',
            type: 'umd',
        }
    },
    resolve: {
        modules: [nodeModulesDir],
        mainFields: ['main', 'module']
    },
    optimization: {
        minimizer: [
            new TerserPlugin({
                parallel: true,
                terserOptions: {
                    mangle: true,
                    keep_classnames: new RegExp('AbortSignal'),
                    keep_fnames: new RegExp('AbortSignal')
                }
            })
        ]
    }
}];
