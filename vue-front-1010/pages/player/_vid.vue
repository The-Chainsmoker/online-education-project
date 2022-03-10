<template>
  <div>
    <!-- 阿里云视频播放器样式 -->
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css">
    <!-- 阿里云视频播放器脚本 -->
    <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js" />

    <!-- 定义播放器dom -->
    <div id="J_prismPlayer" class="prism-player" />
  </div>
</template>

<script>
import vod from '@/api/vod'
export default {
  layout: 'video',
  asyncData({ params, error }) {
    return vod.getPlayAuth(params.vid).then((response) => {
      return {
        playAuth: response.data.playAuth, //视频凭证
        vid: params.vid, //阿里视频id
      }
    })
  },
  //id: 'J_prismPlayer'挂在节点只能在mounted()操作
  mounted() {
    new Aliplayer(
      {
        id: 'J_prismPlayer',
        vid: this.vid, // 视频id
        playauth: this.playAuth, // 播放凭证
        encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
        width: '100%',
        height: '500px',
        // 以下可选设置
        cover:
          'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp9.itc.cn%2Fimages01%2F20200520%2F785eef4886074ef9a5f596b199fa19bd.jpeg&refer=http%3A%2F%2Fp9.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648135460&t=41480d8bd70d3a67e51c23c787bd6a20', // 封面
        qualitySort: 'asc', // 清晰度排序

        mediaType: 'video', // 返回音频还是视频
        autoplay: false, // 自动播放
        isLive: false, // 直播
        rePlay: false, // 循环播放
        preload: true,
        controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
        useH5Prism: true, // 播放器类型：html5
      },
      function (player) {
        console.log('播放器创建成功')
      }
    )
  },
}
</script>

<style scoped>
.prism-player {
  margin-top: 20px;
}
</style>