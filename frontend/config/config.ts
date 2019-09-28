import routes from './routes.config'
import webpack from './webpack.config'

const targetAddr = 'http://120.79.172.32:8866/'

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
    proxy: {
        '/api': {
            target: `${targetAddr}`,
            changeOrigin: true,
            pathRewrite: {
                '^/api': ''
            }
        }
    },
    targets: {
        ie: 10
    },
    hash: true,
    routes,
    ...webpack
}
