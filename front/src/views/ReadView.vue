<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

const props = defineProps({
  postId : {
    type : [Number, String],
    required: true
  }
});

const post = ref({
  id: 0,
  title: "",
  contents: ""
});

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data.data;
  });
})

const gotoEdit = () => {
  router.push({name : "edit", params: {postId : props.postId}})
}
</script>

<template>
  <el-row>
    <el-col>
      <h2 class="title">{{ post.title }}</h2>

      <div class="sub d-flex">
        <div class="category">개발1</div>
        <div class="regDate">2023-07-23</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ post.contents }}</div>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="gotoEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>

</template>

<style scoped lang="scss">

.title {
  font-size: 1.6rem;
  font-weight: 600;
  font-color : #383838;
  margin: 0;
}

.sub {
  margin-top: 10px;
  font-size: 0.78rem;

  .regDate {
    margin-left: 10px;
    color : #6b6b6b;
  }
}

.content {
  font-size: 0.95rem;
  color : #616161;
  margin-top: 12px;
  white-space: break-spaces;
  line-height: 1.5;
}
</style>