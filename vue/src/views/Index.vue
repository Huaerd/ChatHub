<script>
export default {
  name: "Index"
}
</script>

<template>
  <div class="container">
    <nav class="navbar shadow-lg position-sticky rounded-bottom-3">
      <div class="container">
        <label class="navbar-brand" href="#head">ChatHub</label>

        <div class="navbar">
          <ul class="navbar-nav me-auto">
          </ul>
          <div v-if="currentUser" class="user-avatar hover-scale rounded-circle">
            <img :src="currentUser.avatar" :alt="currentUser.username" />
          </div>
          <div v-else class="btn hover-scale" @click="showLogin = true" >登录/注册</div>
        </div>
      </div>
    </nav>

    <main class="main-content">
      <section class="py-5 text-center">
        <div>
          <h1 class="display-4">欢迎来到ChatHub在线聊天室</h1>
          <p class="lead">C属性大爆发,聊天室就等你!</p>
          <a class="btn btn-primary hover-scale px-4" id="start-chat" @click="goToUserPage">Go!GO!GO👉</a>
        </div>
      </section>
      <section class="py-5 text-center">

      </section>
    </main>
    <!--  登录模态框-->
    <Transition name="modal">
      <div v-if="showLogin" class="modal-overlay position-absolute"
           @keyup.esc="showLogin = false" @click="showLogin = false"
           tabindex="0"
      >
        <div class="modal-content" @click.stop v-if="showLogin">
          <loginForm @close="showLogin = false"/>
        </div>
      </div>
    </Transition>
  </div>
</template>
<script setup>
import {ref,onMounted,onUnmounted} from "vue";
import loginForm from "@/components/LoginForm.vue";
import router from "@/router/index.js";

//跳转用户界面如果没有用户信息则显示登录
const goToUserPage = ()=>{
  let a = localStorage.getItem("user");
  if(currentUser.value != null){
    router.push("/user");
  }
  else {
    showLogin.value = true;
  }
}


const showLogin = ref(false);

const handleLogin = (e) => {
  if (e.key === 'Escape') showLogin.value = false;
};
onMounted(() => {
  window.addEventListener('keydown', handleLogin);
});
onUnmounted(()=>{
  window.removeEventListener('keydown', handleLogin);
});
//登录处理
// 从localStorage获取信息
const currentUser = ref(null);
onMounted(() => {
  const userData = localStorage.getItem('user');
  if(userData) {
    const { uid, username, email ,avatar} = JSON.parse(userData);
    currentUser.value = { uid, username, email ,avatar};
  }
});

</script>
<style scoped>
/*模态框渐变动画*/
.modal-enter-active, .modal-leave-active {
  transition: opacity 0.5s ease;
}
.modal-enter-from, .modal-leave-to {
  opacity: 0;
}
.modal-enter-active .modal-content,
.modal-leave-active .modal-content{
  transition: all 0.5s ease;
}
.modal-enter-from .modal-content,
.modal-leave-to .modal-content{
  transform: translateY(20px);
  opacity: 0;
}


.hover-scale:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
}

.modal-content {
  display: flex;
  width: 900px;
  height: 500px;
}

@media (max-width: 900px) {
  .modal-content{
    transform: scale(0.7);
  }
}

.user-avatar img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}
</style>