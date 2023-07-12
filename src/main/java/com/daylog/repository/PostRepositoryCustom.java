package com.daylog.repository;

import com.daylog.domain.Post;
import com.daylog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
