import routes from './routes.config'
import webpack from './webpack.config'

// const targetAddr = 'http://120.79.172.32:8866/'

export default {
    treeShaking: true,
    outputPath: process.env.UMI_ENV === 'deploy' ? './tempDist' : './dist',
    plugins: [
        [
            'umi-plugin-react',
            {
                antd: true,
                dva: true,
                dynamicImport: { webpackChunkName: true },
                title: 'blog',
                polyfills: ['ie10'],
                dll: true
            }
        ]
    ],
    extraBabelPlugins: [
        [
            'import',
            {
                libraryName: 'antd',
                style: 'css'
            }
        ]
    ],
    // proxy: {
    //     '/v2': {
    //         target: `${targetAddr}/v2`,
    //         changeOrigin: true,
    //         pathRewrite: {
    //             '^/v2': ''
    //         }
    //     }
    // },
    targets: {
        ie: 10
    },
    routes,
    ...webpack
}
