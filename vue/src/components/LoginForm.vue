<script>
import {ref} from "vue";
export default {
  name: "LoginForm"
}
</script>

<template>
  <div class="body w-100 h-100">
    <div class="shell w-100 h-100">
      <div class="main w-100 h-100 p-0">
<!--        l1一号表单-->
        <div class="main w-50 h-100 p-0">
          <!--        登录框-->
          <Transition name="l1">
            <div class="form form-1" id="login-form" v-if="!show" >
              <form action="" method="post" id="" @submit.prevent="handleSubmit">
                <h2>登入</h2>
                <input v-model="loginData.email" type="email" class="input-form" placeholder="Email">
                <input v-model="loginData.password" type="password" class="input-form" placeholder="Password">
                <button class="form-btn" type="submit">LogIn</button>
              </form>
            </div>
          </Transition>
          <!--        切换到登录-->
          <Transition name="r2">
            <div class="switch" id="switch-2" v-if="show">
              <div class="switch_circle"></div>
              <div class="switch_circle switch_circle-t"></div>
              <div class="switch-container" id="switch-c2">
                <h2>Hello</h2>
                <a>创建账号,欢迎加入</a>
                <a>已经有帐号了？快去登录吧👇</a>
                <button class="form-btn" @click="show=false">TO LOGIN</button>
              </div>
            </div>
          </Transition>
        </div>
<!--        注册表单-->
        <div class="main w-50 h-100 p-0">
          <!--        注册框-->
          <Transition name="r1">
            <div class="form form-2" v-if="show">
              <form action="" id="register-form" @submit.prevent="handleSubmit">
                <h2>注册</h2>
                <input type="text" class="input-form" placeholder="name">
                <input type="text" class="input-form" placeholder="Email">
                <input type="text" class="input-form" placeholder="Password">
                <button class="form-btn">Sign Up</button>
              </form>
            </div>
          </Transition>
          <!--        切换到注册-->
          <Transition name="l2">
            <div class="switch" id="switch-1" v-if="!show">
              <div class="switch_circle"></div>
              <div class="switch_circle switch_circle-t"></div>
              <div class="switch-container" id="switch-c1">
                <h2>Welcome Back</h2>
                <a>还没有账号吗?</a>
                <a>点击注册!成为我们的一员👇</a>
                <button class="form-btn" id="switch" @click.stop="show=true">TO SIGN UP</button>
              </div>
            </div>
          </Transition>

        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import {ref} from 'vue';
import axios from "axios";
const show = ref(false);
const emit = defineEmits(['login-success']);

const loginData = ref({
  email:'',
  password:''
});

const handleSubmit = async () => {
  const res = await axios.post('http://localhost:8080/api/user/login', loginData.value);
  let user = res.data;
  if(res.data && res.data.uid){
    localStorage.setItem('user', JSON.stringify(res.data));
    window.location.reload();
  }else {
    alert('登录失败')
  }
}

</script>
<style scoped>
*{
  user-select: none;
  text-decoration: none;
  color: #343434;
}
.body{
  display: flex;
  justify-content: center;
}
.shell{
  background: rgba(236, 240, 243, 0.80);
  backdrop-filter: blur(10px);
  box-shadow: 10px 10px 10px #d1d9e6,-10px -10px 10px #f9f9f9;
  border-radius: 12px;
  overflow: hidden;
}
.main{
  display: flex;
  align-items: center;
}
.form{
  display: flex;
  justify-content: center;
  align-items: center;
}
.switch{
  background: rgba(0, 54, 171, 0.5);
}
.form,.switch{
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
/* form表单居中对齐 */
.form{
  background-color: rgba(206, 224, 255, 0.2);
}
.form form{
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3.5rem; /* 控制子元素间距 */
  width: 100%;
}
.form-2 form{
  gap:2rem;
}
.input-form, .form-btn {
  width: 80%;
  max-width: 300px;
}
/* 右侧转换页对齐 */
.switch{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}
.switch-container{
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4rem;
}
/*按钮组件样式*/
.form-btn{
  all:unset;
  cursor: pointer;
  padding: 0.5rem 1rem;
  border-radius: 1rem;
  background: #9CC4E4;
  backdrop-filter: blur(10px);
}
.form-btn:hover{
  transform: scale(1.1);
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);
}
/*输入框样式*/
.input-form{
  all:unset;
  padding: 0.5rem 3rem;
  border-bottom: 1px solid #ccc;
  border-radius: 1rem;
}


/*动画*/
.form,.switch{
  position: absolute;
  top: 0;
  width: 50%;
  height: 100%;
}
/* 登录表单动画 */
.l1-enter-active,
.l1-leave-active,
.r1-enter-active,
.r1-leave-active,
.l2-enter-active,
.l2-leave-active,
.r2-enter-active,
.r2-leave-active{
  transition: all 0.5s ease;
}
.l1-enter-from,
.l1-leave-to,
.r1-enter-from,
.r1-leave-to{
  opacity: 0;
  transform: translateX(50%);
}
.l2-enter-from,
.l2-leave-to,
.r2-enter-from,
.r2-leave-to{
  opacity: 0;
  transform: translateX(-50%);
}
</style>