import Vue from 'vue';
import Router from 'vue-router';
import Element from 'element-ui';

import lineChart from '../components/page/lineChart.vue'
import bossView from '../components/page/bossView.vue'
import promoterOperate from '../components/page/promoterOperate.vue'

import '../assets/styles/test.css'
import 'element-ui/lib/theme-chalk/index.css'
import '../assets/fonts/iconfont.css'

Vue.use(Router);
Vue.use(Element)

export default new Router({
    routes: [
        {
            path: '/view/',
            redirect: '/view/bossView'
        },
        {
            path: '/handle/',
            redirect: '/handle/order'
        },
        {
            path: '/handle/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: {title: '自述文件'},
            children: [
                {
                    path: '/handle/order',
                    component: resolve => require(['../components/page/Order.vue'], resolve),
                    meta: {title: '报单处理'}
                },
                {
                    path: '/handle/chat',
                    component: resolve => require(['../components/page/chat.vue'], resolve),
                    meta: {title: '客服与反馈'}
                },
                {
                    name: 'orderDetail',
                    path: '/handle/orderDetail',
                    component: resolve => require(['../components/page/OrderDetail.vue'], resolve),
                    meta: {title: '报单详情'}
                }
            ]
        }, {
            path: '/view/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: {title: '自述文件'},
            children: [
                {
                    path: '/view/lineChart',
                    component: lineChart,
                    meta: {title: '推广统计中心'}
                }, {
                    path: '/view/bossView',
                    component: bossView,
                    meta: {title: '老板视图'},
                }, {
                    path: '/view/promoterOperate',
                    component: promoterOperate,
                    meta: {title: '普法宣传员管理'},
                }
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path: '/register',
            component: resolve => require(['../components/page/register.vue'], resolve)
        },
        {
            path: '/404',
            component: resolve => require(['../components/page/404.vue'], resolve)
        },
        {
            path: '/403',
            component: resolve => require(['../components/page/403.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
})
