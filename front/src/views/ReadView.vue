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
  <h2>{{ post.title }}</h2>
  <div>{{ post.contents }}</div>
  <el-button type="warning" @click="gotoEdit()">수정</el-button>
</template>