package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {

    private static final ConcurrentHashMap<Long, Post> data = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1L);

    public List<Post> all() {
        return data.values().parallelStream().filter(Post::isActual).collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        Optional<Post> result = Optional.ofNullable(data.get(id));
        if (result.isPresent() && result.get().isActual()) {
            return result;
        } else {
            return Optional.empty();
        }
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idCounter.getAndIncrement());
            data.put(post.getId(), post);
        } else {
            var tmp = data.get(post.getId());
            if (tmp != null && tmp.isActual()) {
                data.put(post.getId(), post);
            } else {
                throw new NotFoundException();
            }
        }
        return post;
    }

    public void removeById(long id) {
        var tmp = data.get(id);
        if (tmp == null) {
            throw new NotFoundException(id + " not found");
        } else {
            tmp.setActual(false);
        }
    }
}
