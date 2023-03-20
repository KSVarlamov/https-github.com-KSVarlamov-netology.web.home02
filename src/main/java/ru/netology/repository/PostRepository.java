package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PostRepository {

    private static final ConcurrentHashMap<Long, Post> data = new ConcurrentHashMap<>();
    private static volatile long idCounter = 1;

    public List<Post> all() {
        return data.values().parallelStream().toList();
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(data.get(id));
    }

    public synchronized Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idCounter);
            idCounter++;
            data.put(post.getId(), post);
        } else {
            Post tmp = data.get(post.getId());
            if (tmp != null) {
                data.put(post.getId(), post);
            } else {
                throw new NotFoundException();
            }
        }
        return post;
    }

    public void removeById(long id) {
        var tmp = data.remove(id);
        if (tmp == null) {
            throw new NotFoundException(id + " not found");
        }
    }
}
