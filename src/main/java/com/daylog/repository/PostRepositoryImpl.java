package com.daylog.repository;

import com.daylog.domain.Post;
import com.daylog.domain.QPost;
import com.daylog.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.daylog.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements  PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();

        return posts;
    }
}
