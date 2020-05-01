package com.ankit.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "POSTS")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String postName;

	@Column(name = "YEAR_OF_POST")
	private int yearOfPost;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//	@JoinColumn(name = "user_id", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
//	private Set<Comment> comments = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "COMMENTS", joinColumns = @JoinColumn(name = "post_id"))
	private Set<String> comments = new HashSet<>();

	public Post() {
		super();
	}

	public Post(Long id, @NotNull String postName, int yearOfPost, User user, Set<String> comments) {
		super();
		this.id = id;
		this.postName = postName;
		this.yearOfPost = yearOfPost;
		this.user = user;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public int getYearOfPost() {
		return yearOfPost;
	}

	public void setYearOfPost(int yearOfPost) {
		this.yearOfPost = yearOfPost;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<String> getComments() {
		return comments;
	}

	public void setComments(Set<String> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", postName=" + postName + ", yearOfPost=" + yearOfPost + ", user=" + user
				+ ", comments=" + comments + "]";
	}

}
