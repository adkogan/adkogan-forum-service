package telran.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.forum.domain.Post;
import telran.forum.dto.DatePeriodDto;
import telran.forum.dto.NewCommentDto;
import telran.forum.dto.NewPostDto;
import telran.forum.dto.PostUpdateDto;
import telran.forum.service.ForumService;

@RestController
public class ForumController {

	@Autowired
	ForumService forumService;

	@PostMapping("/posts")
	public Post addNewPost(@RequestBody NewPostDto newPost) {
		return forumService.addNewPost(newPost);
	}

	@DeleteMapping("/posts/{id}")
	public Post removePost(@PathVariable String id) {
		return forumService.removePost(id);
	}

	@GetMapping("/posts/{id}")
	public Post getPost(@PathVariable String id) {
		return forumService.getPost(id);
	}

	@PutMapping("/posts")
	public Post updatePost(@RequestBody PostUpdateDto updatePost) {
		return forumService.updatePost(updatePost);
	}

	@PutMapping("/posts/{id}/comments")
	public Post addNewComment(@PathVariable String id, @RequestBody NewCommentDto newComment) {
		return forumService.addComment(id, newComment);
	}

	@PutMapping("/posts/{id}/likes")
	public boolean addLikes(@PathVariable String id) {
		return forumService.addLike(id);
	}

	@PostMapping("/posts/tags")
	public Iterable<Post> getPostByTags(@RequestBody List<String> tags) {
		return forumService.findByTags(tags);
	}

	@GetMapping("/posts/author/{author}")
	public Iterable<Post> getPostByAuthor(String author) {
		return forumService.findByAuthor(author);

	}

	@PostMapping("/posts/period")
	public Iterable<Post> getPostByPeriod(@RequestBody DatePeriodDto period) {
		return forumService.findByDate(period);
	}

}
