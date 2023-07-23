<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
const posts = ref([]);

axios.get("/api/posts?page=1&size=5")
    .then(response => {
      response.data.data.forEach((r: any) => {
        posts.value.push(r)
      });
    });

const moveToRead = () => {
  router.push({name: "read"})
}
</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title">
        <router-link :to="{name: 'read', params: {postId: post.id}}">{{ post.title }}</router-link>
      </div>
      <div class="content">
        {{ post.contents }}
      </div>

      <div class="sub d-flex">
        <div class="category">개발1</div>
        <div class="regDate">2023-07-23</div>
      </div>

    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  li {
    margin-bottom: 1.8rem;

    &:last-child {
      margin-bottom: 0;
    }

    .title {
      font-size: 1.1rem;
      color: #303030;

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      color : #7e7e7e;
      margin-top: 8px;
    }

    .sub {
      margin-top: 9px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color : #6b6b6b;
      }
    }

  }
}

a {
  text-decoration: none;
  color : #303030;
}


</style>