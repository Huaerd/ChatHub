<script>
export default {
  name: "User"
}
</script>

<template>
  <body class="d-flex flex-column vh-100 overflow-hidden" style="background-color: #f3f4f6; font-family: 'Inter', system-ui, sans-serif;">
  <!-- 顶部导航栏 -->
  <nav class="navbar shadow-sm position-sticky" style="z-index: 10">
    <div class="container-fluid">
      <!-- 菜单栏显示 -->
      <div class="position-relative">
        <img src="/favicon.ico"/>
      </div>
      <!-- 搜索框 -->
      <div class="flex-grow-1 position-relative">
        <input type="text" class="form-control rounded-pill ps-4"
               style="background-color: rgba(0,0,0,0.05); border: none;max-width: 200px;margin-left: 3rem"
               placeholder="🔎Search...">
      </div>

      <!-- 右侧按钮组 -->
      <div class="d-flex gap-3">
        <div class="dropdown">
          <button class="btn btn-link text-muted p-0 hover-scale"
                  id="settings-btn" @click="handleSetting">
            <i class="fas fa-cog"></i>
            <a class="dropdown-header" @click.stop="showSetting = true">设置</a>
          </button>
        </div>
      </div>
    </div>
  </nav>
  <!--加入频道模态框-->
  <Transition name="modal">
    <div v-if="showCreateForm" class="modal-overlay position-absolute"
         @keyup.esc="showCreateForm = false" @click="showCreateForm = false"
         tabindex="1"
    >
      <div class="modal-content joinForm p-3 pt-3" @click.stop v-if="showCreateForm">
        <h5 class="text-center mb-3">填写创建频道的名称</h5>
        <input v-model="createChannelName" class="form-control mb-3" placeholder="输入频道名称">
        <button @click="createChannel" class="btn btn-primary w-100">创建</button>
      </div>
    </div>
  </Transition>
  <!--加入频道模态框-->
  <Transition name="modal">
      <div v-if="showJoinForm" class="modal-overlay position-absolute"
         @keyup.esc="showJoinForm = false" @click="showJoinForm = false"
         tabindex="1"
    >
      <div class="modal-content joinForm p-3 pt-3" @click.stop v-if="showJoinForm">
        <h5 class="text-center mb-3">填写想要加入的频道ID</h5>
        <input v-model="joinChannelId" class="form-control mb-3" placeholder="输入频道ID">
        <button @click="JoinChannel" class="btn btn-primary w-100">加入</button>
      </div>
    </div>
  </Transition>
  <!--设置模态框-->
  <Transition name="modal">
    <div v-if="showSetting" class="modal-overlay position-absolute"
         @keyup.esc="showSetting = false" @click="showSetting = false"
         tabindex="1"
    >
      <div class="modal-content" @click.stop v-if="showSetting">
        <SettingForm @close="showSetting = false"/>
      </div>
    </div>
  </Transition>
  <!-- 主内容区域 -->
  <main class="d-flex flex-grow-1 overflow-hidden position-relative" >

    <!-- 左侧边栏 -->
    <nav class="d-flex flex-column border-end border-light position-absolute h-100"
         style="width: 96px;transition: width 0.3s; z-index: 1;background-color: #f3f4f6"
         onmouseenter="this.style.width='250px'"
         onmouseleave="this.style.width='96px'">
      <div class="flex-grow-1 overflow-auto">
        <div class="container">
          <ul class="nav flex-column pt-2 scallbar" style="user-select: none">
            <li v-for="channel in channel_list" :key="channel.id" class="mb-3">
              <div class="hover-scale icon-border rounded-4 p-2 d-flex align-items-center"
                   @click="selectChannel(channel.id)"
                   style="min-height: 64px">
                <!-- 头像容器 -->
                <div class="flex-shrink-0 me-3">
                  <img :src="channel.avatar || 'defaultAvatar' "
                       class="rounded object-fit-cover"
                       style="width: 48px; height: 48px">
                </div>

                <!-- 文字内容容器 -->
                <div class="flex-grow-1 position-relative sp"
                     style="min-height: 48px">
                  <!-- 频道名称（左上角） -->
                  <div class="text-truncate fw-semibold">{{ channel.name }}</div>

                  <!-- 频道ID（左下角） -->
                  <small class="text-muted position-absolute"
                         style="bottom: 0">ID: {{ channel.id }}</small>

                  <!-- 星标（右下角） -->
                  <i v-if="channel.owner === currentUser.uid"
                     class="bi bi-star-fill position-absolute"
                     style="bottom: 0; right: 0; color: gold; font-size: 12px"></i>
                </div>
              </div>
            </li>
            <!-- 加入其他频道 -->
            <li class="mb-3">
              <div class="hover-scale icon-border rounded-4 p-2 d-flex align-items-center"
                   @click="showJoinForm = true"
                   style="min-height: 64px">
                <!-- 头像容器 -->
                <div class="flex-shrink-0 me-3">
                  <img src="/favicon.ico"
                       class="rounded object-fit-cover"
                       style="width: 48px; height: 48px"/>
                </div>

                <!-- 文字内容容器 -->
                <div class="flex-grow-1 position-relative sp"
                     style="min-height: 48px">
                  <!-- 频道名称（左上角） -->
                  <div class="text-truncate fw-semibold">加入频道</div>
                </div>
              </div>
            </li>
            <!-- 创建频道 -->
            <li class="mb-3">
              <div class="hover-scale icon-border rounded-4 p-2 d-flex align-items-center"
                   @click="showCreateForm = true"
                   style="min-height: 64px">
                <!-- 头像容器 -->
                <div class="flex-shrink-0 me-3">
                  <img src="/favicon.ico"
                       class="rounded object-fit-cover"
                       style="width: 48px; height: 48px"/>
                </div>

                <!-- 文字内容容器 -->
                <div class="flex-grow-1 position-relative sp"
                     style="min-height: 48px">
                  <!-- 频道名称（左上角） -->
                  <div class="text-truncate fw-semibold">创建频道</div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <!-- 用户 -->
      <div class="container-fluid pb-2">
        <!-- 分割线 -->
        <div class="border-bottom mx-2 border-5 m-4"
             style="transition: all 0.3s; width: calc(100% - 1rem);border-color: #4c4c4c;border-radius: 1rem"></div>
        <div class="container hover-scale icon-border">
          <img src=""
               class="rounded object-fit-cover"
               style="width: 48px;height: 48px" alt="">
          <div class="sp position-absolute">
            <label class="align-items-center">123</label>
          </div>
        </div>
      </div>
    </nav>

    <!-- 聊天区域 -->
    <section class="d-flex flex-column flex-grow-1 bg-light position-relative"
             id="chat-panel" style="margin-left: 96px">

      <!-- 聊天消息区域 -->
      <div class="flex-grow-1 overflow-auto p-0" id="chat-messages" style="display: flex; flex-direction: column; gap: 1rem;">
        <Message v-if="currentChannelId" :channelId="currentChannelId" :messageres="messageRes" />
      </div>


      <!-- 输入区域 -->
      <div class="bg-white border-top border-light p-3">

        <!-- 信息输入框 -->
        <div class="d-flex" id="massage-send-form"
             style="gap: 40px"
        >
          <form @submit.prevent="sendMessage"  id="message-form" class="d-flex w-100" onsubmit="return false;" style="gap: 1rem;">
                    <textarea
                        placeholder="输入消息..."
                        class="form-control flex-grow-1 border border-light rounded-3 "
                        id="message-input"
                        style="resize: none; overflow-y: hidden;min-height: 38px;align-self: stretch"
                        rows="1"
                        oninput="this.style.height = 'auto'; this.style.height = (this.scrollHeight) + 'px';"
                    ></textarea>
            <div class="d-flex align-items-end">
              <button class="btn btn-primary rounded-3 hover-scale"
                      type="submit"
                      id="send-button">
                <a>发送</a>
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>
  </main>
  </body>
</template>
<script setup>
import {ref,onMounted} from "vue";
import axios from "axios";
import Message from "../components/Message.vue";
import SettingForm from "@/components/SettingForm.vue";

const channel_list = ref([]);
const currentChannelId = ref(null);
//
const showSetting = ref(false);
const showJoinForm = ref(false);
const showCreateForm = ref(false);

const messageRes = ref();
const joinChannelId = ref('')
const createChannelName = ref('')
// 获取登录信息
const currentUser = ref(null);

// 加载频道
const loadChannelList = async (uid) => {
  const res = await axios.get(`http://localhost:8080/api/chat/channels/${uid}`)
  channel_list.value = res.data
  if (res.data.length > 0) {
    currentChannelId.value = res.data[0].id;
    connectWebSocket(res.data[0].id)
  } // 默认选中第一个频道
}
// 频道切换
const selectChannel = (id) => {
  currentChannelId.value = id;
  connectWebSocket(id);
};
onMounted(() => {
  // 获取存储的用户信息
  const userData = localStorage.getItem('user');
  if(userData) {
    const { uid, username, email ,avatar} = JSON.parse(userData);
    currentUser.value = { uid, username, email ,avatar};
    // 加载用户加入的频道列表
    loadChannelList(currentUser.value.uid);
  }
});


// websocket连接
let socket = null;

const connectWebSocket = (channelId) => {
  if (socket) {
    socket.close(); // 关闭旧连接
  }
  socket = new WebSocket(`ws://localhost:8080/ws/chat/${channelId}?sender=${currentUser.value.uid}`);
  socket.onopen = () => {
    console.log(`WebSocket connected to channel ${channelId}`);
  };
  socket.onmessage = (event) => {
    messageRes.value = JSON.parse(event.data)
    console.log("Received message:", messageRes.value)
  };
  socket.onclose = () => {
    console.log("WebSocket closed");
  };
  socket.onerror = (error) => {
    console.error("WebSocket error:", error);
  };
};
const sendMessage = () => {
  const messageInput = document.getElementById('message-input');
  const message = messageInput.value.trim();

  if (message && socket && socket.readyState === WebSocket.OPEN) {
    socket.send(JSON.stringify({
      content: message,
      channelId: currentChannelId.value,
      sender: currentUser.value.uid
    }));
    messageInput.value = ''; // Clear input after sending
    messageInput.style.height = 'auto'; // Reset textarea height
  }
}


// 加入频道
const JoinChannel = async () =>{
  try {
    const res = await axios.post(`http://localhost:8080/api/chat/join`, null, {
      params: {
        channel_id: joinChannelId.value,
        user_id: currentUser.value.uid
      }
    })
    channel_list.value.push(res.data)
    showJoinForm.value = false
    joinChannelId.value = ''
  } catch (error) {
    console.error("加入频道失败:", error)
    alert("加入频道失败: " + error.response?.data?.message || error.message)
  }
}
const tojoin = ref({});
// 创建频道
const createChannel = async () =>{
  try {
    const res = await axios.post(`http://localhost:8080/api/chat/create`, null, {
      params: {
        name: createChannelName.value,
        user_id: currentUser.value.uid
      }
    })
    tojoin.value = res.data;
    channel_list.value.push(tojoin.value);
  } catch (error) {
    console.error("创建频道失败:", error)
    alert("创建频道失败: " + error.response?.data?.message || error.message)
  }
}
</script>
<style scoped>
.joinForm{
  max-height: 200px;
  max-width: 300px;
  background: rgba(236, 240, 243, 0.80);
  backdrop-filter: blur(10px);
  box-shadow: 10px 10px 10px #d1d9e6,-10px -10px 10px #f9f9f9;
  border-radius: 12px;
  overflow: hidden;
}
.hover-scale {
  transition: transform 0.2s;
}
.hover-scale:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}
.text-muted {
  color: #909399;
}
.bg-light {
  background-color: #F5F6F8;
}
.border-light {
  border-color: #E5E6EB;
}
.icon-border{
  border-radius: 1rem;
}
.btn{
  min-width: 64px;
  min-height: 48px;
}
/* 隐藏文字标签 */
nav .nav-link .sp,
nav .icon-border > .sp {
  display: none;
}

/* 展开时显示文字 */
nav:hover .nav-link .sp,
nav:hover .icon-border > .sp{
  display: inline;
  animation: fadeIn 0.5s;
}
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

/*模态框*/
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
  transform: scale(0);
  opacity: 0;
}



.message-content ul{
    list-style-type: none;
    background-color: #FFF;
    margin: 0;
    overflow: auto;
    overflow-y: scroll;
    height: calc(100% - 150px);
}

/*隐藏scallbar*/
.scallbar{
  scrollbar-width: none;
  -ms-overflow-style: none;
  overflow-y: scroll;
}
.scallbar::-webkit-scrollbar {
  display: none;
}
</style>