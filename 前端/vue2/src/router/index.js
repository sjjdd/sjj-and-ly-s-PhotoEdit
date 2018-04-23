import Vue from 'vue';
import Router from 'vue-router';
Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/readme',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            children:[
                {
                    path: '/',
                    component: resolve => require(['../components/page/Readme.vue'], resolve)
                },
                {
                    path: '/addpt',
                    component: resolve => require(['../components/page/Addpt.vue'], resolve)
                },
                {
                    path: '/mdlist',
                    component: resolve => require(['../components/page/Mdlist.vue'], resolve)
                },
                {
                    path: '/dtlist',
                    component: resolve => require(['../components/page/Dtlist.vue'], resolve)
                },
                {
                    path: '/edit',
                    component: resolve => require(['../components/page/Edit.vue'], resolve)
                }
            ],
             meta: {
                requiresAuth: true
            }
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
    ]
})
