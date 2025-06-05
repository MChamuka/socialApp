package com.social.socialapp.controller;

import com.social.socialapp.model.Post;
import com.social.socialapp.model.User;
import com.social.socialapp.repository.PostRepository;
import com.social.socialapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addPost(@RequestParam Long userId, @RequestParam String content) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "User not found!";
        }

        Post post = new Post();
        post.setContent(content);
        post.setUser(user);
        postRepository.save(post);

        return "Post added successfully!";
    }

    @DeleteMapping("/remove")
    public String removePost(@RequestParam Long userId, @RequestParam Long postId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "User not found";
        }

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return "Post not found";
        }

        if (!post.getUser().getId().equals(userId)) {
            return "Post does not belong to this user";
        }

        postRepository.delete(post);
        return "Post deleted successfully!";
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUser(@PathVariable Long userId) {
        return postRepository.findByUserId(userId);
    }

}
