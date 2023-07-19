<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";

const title = ref("")
const contents = ref("")

const write = function() {
  axios.post('http://localhost:8080/posts', {
    title : title.value,
    contents : contents.value
  })

}
</script>

<template>
  <div>
    <el-input v-model="title" placeholder="제목을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="contents" type="textarea"></el-input>
  </div>
  <div class="mt-2">
    <el-button type="primary" @click="write()">글 작성완료</el-button>
  </div>
</template>
<style>

</style>