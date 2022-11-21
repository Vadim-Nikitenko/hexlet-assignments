package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;

import java.util.List;


@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // BEGIN
    @GetMapping(path = "/{postId}/comments/{commentId}")
    public Comment getCommentsByPostId(@PathVariable long postId, @PathVariable long commentId) {
        return commentRepository.findByPostIdAndCommentId(postId, commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
    }

    @GetMapping(path = "/{postId}/comments")
    public List<Comment> postCommentToPost(@PathVariable long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        return post.getComments();
    }

    @PostMapping(path = "/{postId}/comments")
    public void postCommentToPost(@PathVariable long postId, @RequestBody Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        post.getComments().add(comment);
        postRepository.save(post);
    }

    @PatchMapping(path = "/{postId}/comments/{commentId}")
    public void patchCommentToPost(@PathVariable long postId, @PathVariable long commentId, @RequestBody Comment comment) {
        Comment comment1 = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
        comment1.setContent(comment.getContent());
        commentRepository.save(comment1);
    }

    @DeleteMapping(path = "/{postId}/comments/{commentId}")
    public void deleteCommentToPost(@PathVariable long postId, @PathVariable long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        Comment comment1 = post.getComments().stream().filter(c -> c.getId() == commentId)
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        post.getComments().remove(comment1);
        commentRepository.delete(comment1);
    }
    // END
}
