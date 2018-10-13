package telran.forum.domain;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

//@JsonTypeInfo(use = Id.CLASS)
@Document(collection="Forum")
public class Post {
	@org.springframework.data.annotation.Id
	String id;
	String title;
	String content;
	String author;
	@JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
	LocalDateTime dateCreated;
	Set<String> tags;
	int likes;
	Set<Comment> comments;

	public Post() {
	}

	public Post(String title, String content, String author, Set<String> tags) {
		
		this.title = title;
		this.content = content;
		this.author = author;
		this.tags = tags;
		comments = new HashSet<Comment>();
		dateCreated = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public Set<String> getTags() {
		return tags;
	}

	public int getLikes() {
		return likes;
	}

	public void addLikes() {
		likes++;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public boolean addComments(Comment comment) {
		return comments.add(comment);

	}

	public boolean addTag(String tag) {
		return tags.add(tag);
	}

	public boolean removeTag(String tag) {
		return tags.remove(tag);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((id == null) ? 0 : id.hashCode());
	
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + ", author=" + author + ", dateCreated="
				+ dateCreated + ", tags=" + tags + ", likes=" + likes + ", comments=" + comments + "]";
	}

}
