import { createRouter, createWebHistory } from 'vue-router'
import Index from '@/views/Index.vue'
import User from "@/views/User.vue";
import Message from "@/components/Message.vue";


const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path:"/",
      name:"index",
      component: Index
    },
    {
      path:"/user",
      name: "user",
      component:User
    }
  ],
})


export default router