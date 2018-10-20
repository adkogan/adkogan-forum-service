package telran.forum.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.forum.configuration.AccountUserCredential;
import telran.forum.dao.ForumRepository;
import telran.forum.domain.Comment;
import telran.forum.domain.Post;
import telran.forum.dto.DatePeriodDto;
import telran.forum.dto.NewCommentDto;
import telran.forum.dto.NewPostDto;
import telran.forum.dto.PostUpdateDto;

@Service
public class ForumServiceImpl implements ForumService {

	@Autowired
	ForumRepository forumRepository;
	
	@Autowired
	AccountService accountService;

	@Override
	public Post addNewPost(NewPostDto newPost) {
		Post post = new Post(newPost.getTitle(), newPost.getAuthor(), newPost.getContent(), newPost.getTags());
		return forumRepository.save(post); 
		
		//orPost post = convertToPost(newPost);
	//	private Post converterToPost(NewPostDto newPost){
		//return new PostnewPost.getTitle(), newPost.getAuthor(), newPost.getContent(), newPost.getTags());
	}

	@Override
	public Post getPost(String id) {
		
		return forumRepository.findById(id).orElse(null);
	}

	@Override
	public Post removePost(String id, String auth) {
		Post post = forumRepository.findById(id).orElse(null);
		if (post == null) {
			return null;
		}
		forumRepository.deleteById(id);
		return post; 
	}

	@Override
	public Post updatePost(PostUpdateDto updatepost, String auth) {
		AccountUserCredential credentials = accountService.getExistingUserCreditialsOrThrow(auth);		
		Post existing = forumRepository
			.findById(updatepost.getId())
			.orElse(null);
		if (existing == null) {
			return null;
		}
		if (!existing.getAuthor().equals(credentials.getLogin())) {
			throw new ForbidenException();
		}
		if (updatepost.getContent() != null) {
			existing.setContent(updatepost.getContent());
		}
		return forumRepository.save(existing);

	}

	@Override
	public boolean addLike(String id) {
		Post post = forumRepository.findById(id).orElse(null);
		if (post == null) {
			return false;
		}
		post.addLikes();
		forumRepository.save(post);
		return true;
	}

	@Override
	public Post addComment(String id, NewCommentDto newComment) {
		Post post = forumRepository.findById(id).orElse(null);
		if (post == null) {
			return null;
		}
		Comment comment = new Comment(newComment.getUser(), newComment.getMessage());
		//comment.setMessage(newComment.getMessage());
		//comment.setUser(newComment.getUser());
		post.addComments(comment);
		return forumRepository.save(post);
	}

	@Override
	public Iterable<Post> findByTags(List<String> tags) {
		return forumRepository.findByTagsIn(tags);
	}

	@Override
	public Iterable<Post> findByAuthor(String author) {
		return forumRepository.findByAuthor(author);
	}

	@Override
	public Iterable<Post> findByDate(DatePeriodDto period) {
		
		return forumRepository.findByDateCreatedBetween(LocalDate.parse(period.getFrom()),
				LocalDate.parse(period.getTo()));
	}

}
