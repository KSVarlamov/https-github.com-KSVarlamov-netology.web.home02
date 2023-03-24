package ru.netology.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.model.Post;
import ru.netology.model.PostDTO;
import ru.netology.service.PostService;
import ru.netology.utils.MapUtils;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;
private final MapUtils mapUtils;
    public PostController(PostService service, MapUtils mapUtils) {
        this.service = service;
        this.mapUtils = mapUtils;
    }

    @GetMapping
    public List<PostDTO> all() {
        return service.all().stream().map(mapUtils::postToPostDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostDTO getById(@PathVariable long id) {
        return mapUtils.postToPostDTO(service.getById(id));
    }

    @PostMapping
    public PostDTO save(@RequestBody PostDTO post) {
        Post result = service.save(mapUtils.postDtoToPost(post));
        return mapUtils.postToPostDTO(result);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}
