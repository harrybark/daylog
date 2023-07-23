<script setup lang="ts">
import {useRouter} from "vue-router";
import {defineProps, ref} from "vue";
import axios from "axios";


const router = useRouter();

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

axios.get(`/api/posts/${props.postId}`).then((response) => {
  post.value = response.data.data;
});

const edit = () => {
  axios.patch(`/api/posts/${props.postId}`, post.value)
      .then(() => {
        router.replace({name: "home"})
      })
}

</script>

<template>
  <div>
    <el-input v-model="post.title" />
  </div>

  <div class="mt-2">
    <el-input v-model="post.contents" type="textarea"></el-input>
  </div>
  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>
</template>
<style>

</style>