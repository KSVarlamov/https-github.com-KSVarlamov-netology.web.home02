package ru.netology.utils;

import org.springframework.stereotype.Service;
import ru.netology.model.Post;
import ru.netology.model.PostDTO;

@Service
public class MapUtils {
    public PostDTO postToPostDTO(Post post) {
        return new PostDTO(post.getId(), post.getContent());
    }

    public Post postDtoToPost(PostDTO postDTO) {
        return new Post(postDTO.getId(), postDTO.getContent());
    }
}
