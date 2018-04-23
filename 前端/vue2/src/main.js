import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-default/index.css';    // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import "babel-polyfill";
Vue.prototype.$http = axios;
import VueDirectiveImagePreviewer from 'vue-directive-image-previewer'
import 'vue-directive-image-previewer/dist/assets/style.css'
Vue.use(VueDirectiveImagePreviewer)


Vue.use(ElementUI);
Vue.prototype.$axios = axios;

// router.beforeEach((to, from, next) => {
//   let token = window.localStorage.getItem('token')
//   if (to.matched.some(record => record.meta.requiresAuth) && (!token || token == null)) {
//     console.log("无权限！");
//     next({
//       path: '/login',
//       query: { redirect: to.fullPath }
//     })
//   } else {
//     next()
//   }
// });
new Vue({
    router,
    render: h => h(App),
    http: { headers: {'Content-Type': 'application/x-www-form-urlencoded'} }
}).$mount('#app');
