<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";

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

</script>

<template>
  <h2>{{ post.title }}</h2>
  <div>{{ post.contents }}</div>
</template>