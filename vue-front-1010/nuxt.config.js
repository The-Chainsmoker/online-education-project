module.exports = {
  plugins: [
    { src: '~/plugins/nuxt-swiper-plugin.js', ssr: false }
  ],

  css: [
    'swiper/dist/css/swiper.css'
  ],
  /*
  ** Headers of the page
  */
  head: {
    // title: '{{ name }}',
    title: '千锋在线教育 ',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '{{escape description }}' }
    ],
    //页面头部图标
    link: [
      { rel: 'icon', type: 'image/x-icon', href: 'http://www.mobiletrain.org/favicon.ico' }
    ]
  },
  /*
  ** Customize the progress bar color
  */
  loading: { color: '#3B8070' },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** Run ESLint on save
    */
    extend(config, { isDev, isClient }) {
      if (isDev && isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/
        })
      }
    }
  }
}

