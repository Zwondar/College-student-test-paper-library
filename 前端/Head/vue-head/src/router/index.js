import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginLog from '../views/LoginLog.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'log',
    component: LoginLog
  },
  {
    path: '/Log/On/view',
    name: 'view',
    component: () => import('../views/ViewText.vue')
  },
  {
    path: '/Log/On',
    name: 'OnLog',
    component: () => import('../views/OnWork.vue')
  },
  {
    path: '/Text',
    name: 'Text',
    component: () => import('../views/TextF.vue')
  },
  {
    path: '/Sign',
    name: 'sign',
    component: () => import('../views/SignPassword.vue')
  },
  {
    path: '/Log/back',
    name: 'back',
    component: () => import('../views/BackStage.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
