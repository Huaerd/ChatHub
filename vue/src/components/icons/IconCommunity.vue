<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import Message from "../components/Message.vue";
const channel_list = ref([]);
const currentUser = ref(null);
const currentChannelId = ref(null);
// 当前选中频道 ID
const loadChannelList = async (uid) => {
  const res = await axios.get(`http://localhost:8080/api/chat/channels/${uid}`);
  channel_list.value = res.data;
  if (res.data.length > 0) {
    currentChannelId.value = res.data[0].id;
  } // 默认选中第一个频道
}

onMounted(() => {
  const userData = localStorage.getItem('user');
  if (userData) {
    const { uid, username, email, avatar } = JSON.parse(userData);
    currentUser.value = { uid, username, email, avatar };
    loadChannelList(uid);
  }
});
const showSetting = ref(false);
const selectChannel = (id) => {
  currentChannelId.value = id;
};
</script>