<script>
export default {
  name: "SettingFrom"
}
</script>

<template>
  <div class="container-fluid w-100 h-100 shell">
    <div class="row mb-3 p-5 form">
      <div class="row d-flex align-items-center pt-2">
        <h3 class="text-center">设置</h3>
      </div>
      <div class="col-12 d-flex justify-content-between align-items-center">
        <h5 class="px-4">功能施工中</h5>
      </div>
      <div class="col-12 d-flex justify-content-between align-items-center">
        <img class="rounded align-content-center" src="./icons/building.PNG" alt=""
             width="64" height="64">
      </div>
    </div>
  </div>

</template>

<style scoped>
.shell{
  background: rgba(236, 240, 243, 0.80);
  backdrop-filter: blur(10px);
  box-shadow: 10px 10px 10px #d1d9e6,-10px -10px 10px #f9f9f9;
  border-radius: 12px;
  overflow: hidden;
}
.form{
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3.5rem; /* 控制子元素间距 */
  width: 100%;
}
</style>