const path = require('path')

const resolve = p => path.resolve(__dirname, p)

export default {
    alias: {
        images: resolve('../src/assets/images/'),
        icons: resolve('../src/assets/icons/'),
        utils: resolve('../src/utils/'),
        api: resolve('../src/services/'),
        models: resolve('../src/models/'),
        themes: resolve('../src/themes/')
    },
    urlLoaderExcludes: [/\.svg$/],
    chainWebpack(config, { webpack }) {
        config.module
            .rule('svg')
            .test(/\.svg(\?v=\d+\.\d+\.\d+)?$/)
            .use([
                {
                    loader: 'babel-loader'
                },
                {
                    loader: '@svgr/webpack',
                    options: {
                        babel: false,
                        icon: true
                    }
                }
            ])
            .loader(require.resolve('@svgr/webpack'))

        config.plugin('ignore').use(webpack.IgnorePlugin, [/^\.\/locale$/, /moment$/])
    },
    autoprefixer: {
        flexbox: true
    }
}
