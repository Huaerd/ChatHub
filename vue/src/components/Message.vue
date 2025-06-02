<template>
  <div class="w-100 h-100 p-0 pt-5">
    <div v-if="messages.length === 0" class="d-flex align-items-center justify-content-center h-100">
      <h1 class="text-center m-0">暂无聊天记录</h1>
    </div>
    <ul v-else class="">
      <li v-for="msg in messages" :key="msg.id" class="d-flex mb-3">
        <!-- 头像位置 -->
        <div class="me-3">
          <img id="avatar" :src="getAvatar(msg.sender) || 'defaultAvatar' " class="rounded-circle" width="40" height="40">
        </div>

        <!-- 消息容器 -->
        <div
            class="content rounded-4 position-relative p-2">
          <p class="mb-0" ><strong>{{ getUsername(msg.sender) || '加载中'}}</strong></p>
          <p class="mb-0">{{ msg.content }}</p>
          <small class="date-text" style="font-size:0.75rem">
            {{ formatTime(msg.sendTime) }}
          </small>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup>
import {onMounted, ref, watch} from 'vue'
import axios from 'axios'

const props = defineProps({
  channelId: { type: Number, required: true },
  messageres: { type: Array, default: () => []}
});
const messages = ref([])
const userCache = ref({}) // 缓存为对象

// 加载历史消息
const loadHistory = async (id) => {
  const res = await axios.get(`http://localhost:8080/api/chat/history/${id}`)
  messages.value = res.data
  for (const msg of messages.value) {
    await cacheUser(msg.sender)
  }
}
// 缓存用户信息
const cacheUser = async (uid) => {
  if (!userCache.value[uid]) {
    try {
      const res = await axios.get(`http://localhost:8080/api/user/info/${uid}`)
      userCache.value[uid] = res.data
    } catch (e) {
      userCache.value[uid] = {
        username: '未知用户', avatar: ''
      }
    }
  }
}
const getUsername = (uid) => {
  return userCache.value[uid]?.username || '加载中...' }
const getAvatar = (uid) => {
  return userCache.value[uid]?.avatar || 'https://example.com/default-avatar.png'
}
// 时间格式化
const formatTime = (time) => {
  const date = new Date(time)
  return date.toLocaleString()
}

watch(() => props.channelId, (newId) => {
  console.log('检测到新频道 ID:', newId)
  if (newId) loadHistory(newId)
}, { immediate: true })

// 监听父组件传入的新消息
watch(() => props.messageres, (newMessages) => {
  if (newMessages) {
    messages.value.push(newMessages)
  }
}, { immediate: true })
</script>

<style scoped>
.content{
  background-color: rgba(126, 126, 126, 0.5);
  backdrop-filter: blur(10px);
  max-width: 70%;
  margin-bottom: 0.5rem;
}
.date-text{
  user-select: none;
}
</style>
