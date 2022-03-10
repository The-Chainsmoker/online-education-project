<template>

  <div>
    <!-- 幻灯片 开始 -->
    <div v-swiper:mySwiper="swiperOption">
      <div class="swiper-wrapper">
        <div v-for="banner in bannerList" :key="banner.id" class="swiper-slide" style="background: #040B1B;">
          <a target="_blank" :href="banner.linkUrl">
            <img :src="banner.imageUrl" :alt="banner.title">
          </a>
        </div>

      </div>
      <div class="swiper-pagination swiper-pagination-white" slot="pagination"></div>

      <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
      <div class="swiper-button-next swiper-button-white" slot="button-next"></div>

    </div>
    <!-- 幻灯片 结束 -->

    <div id="aCoursesList">
      <!-- 网校课程 开始 -->
      <div>

        <section class="container">

          <header class="comm-title">

            <h2 class="tac">

              <span class="c-333">热门课程</span>

            </h2>

          </header>

          <div>

            <article class="comm-course-list">

              <ul class="of" id="bna">

                <li v-for="course in eduList" :key="course.id">

                  <div class="cc-l-wrap">

                    <section class="course-img">

                      <img :src="course.cover" class="img-responsive" alt="course.title">

                      <div class="cc-mask">

                        <a :href="'/course/'+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>

                      </div>

                    </section>

                    <h3 class="hLh30 txtOf mt10">

                      <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>

                    </h3>

                    <section class="mt10 hLh20 of">

                      <span class="fr jgTag bg-green" v-if="Number(course.price)===0">

                        <i class="c-fff fsize12 f-fA">免费</i>

                      </span>

                      <span class="fr jgTag bg-green" v-else>

                        <i class="c-fff fsize12 f-fA">付费</i>

                      </span>

                      <span class="fl jgAttr c-ccc f-fA">

                        <i class="c-999 f-fA">{{course.viewCount}}人学习</i>

                        |

                        <i class="c-999 f-fA">{{course.buyCount}}评论</i>

                      </span>

                    </section>

                  </div>

                </li>

              </ul>

              <div class="clear"></div>

            </article>

            <section class="tac pt20">

              <a href="#" title="全部课程" class="comm-btn c-btn-2">全部课程</a>

            </section>

          </div>

        </section>

      </div>

      <!-- /网校课程 结束 -->

      <!-- 网校名师 开始 -->

      <div>

        <section class="container">

          <header class="comm-title">

            <h2 class="tac">

              <span class="c-333">名师大咖</span>

            </h2>

          </header>

          <div>

            <article class="i-teacher-list">

              <ul class="of">

                <li v-for="teacher in teacherList" :key="teacher.id">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a :href="'/teacher/'+teacher.id" :title="teacher.name">
                        <img :alt="teacher.name" :src="teacher.avatar">
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a :href="'/teacher/'+teacher.id" :title="teacher.name" class="fsize18 c-666">{{teacher.name}}</a>
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999">{{teacher.career}}</span>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p class="c-999 f-fA">{{teacher.intro}}</p>
                    </div>
                  </section>
                </li>

              </ul>

              <div class="clear"></div>

            </article>

            <section class="tac pt20">

              <a href="#" title="全部讲师" class="comm-btn c-btn-2">全部讲师</a>

            </section>

          </div>

        </section>

      </div>

      <!-- /网校名师 结束 -->

    </div>

  </div>

</template>


<script>
import banner from '@/api/banner'
import index from '@/api/index'
export default {
  //layout: 'default'默认使用layout/default.vue
  data() {
    return {
      swiperOption: {
        autoplay: {
          delay: 2500,
          //当用户滑动图片后继续自动轮播
          disableOnInteraction: false,
        },
        //配置分页
        pagination: {
          el: '.swiper-pagination', //分页的dom节点
          clickable: false, //允许分页点击跳转
        },
        //开启循环模式(不会跳转路由)
        loop: false,

        //配置导航
        navigation: {
          nextEl: '.swiper-button-next', //下一页dom节点

          prevEl: '.swiper-button-prev', //前一页dom节点
        },
      },
      bannerList: [], //轮播图数据
      eduList: [], //课程数据
      teacherList: [], //教师数据
    }
  },

  created() {
    this.getBannerList()
    this.getHotCourse()
    this.getHotTeacher()
  },
  methods: {
    //查询前8条热门课程
    getHotCourse() {
      index.getIndexCourse().then((response) => {
        this.eduList = response.data.eduList
      })
    },
    //查询前4条名师
    getHotTeacher() {
      index.getIndexTeacher().then((response) => {
        this.teacherList = response.data.teacherList
      })
    },
    //查询banner数据
    getBannerList() {
      banner.getList().then((response) => {
        this.bannerList = response.data.list
      })
    },
  },
}
</script>
<style scoped>
.swiper-slide img {
  width: 100%;
  height: 480px;
}
</style>