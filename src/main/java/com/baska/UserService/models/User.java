package com.baska.UserService.models;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(	name = "users",
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email")
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@Size(max = 30)
	private String salt;

	private Long statusId;

	private Instant lastVisit;

	private Instant blockedTime;

	private Integer countWrongPassword;

	public User(Long id, @NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(max = 120) String password, @Size(max = 30) String salt, Long statusId, Instant lastVisit, Instant blockedTime, Integer countWrongPassword) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.statusId = statusId;
		this.lastVisit = lastVisit;
		this.blockedTime = blockedTime;
		this.countWrongPassword = countWrongPassword;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public Instant getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Instant lastVisit) {
		this.lastVisit = lastVisit;
	}

	public Instant getBlockedTime() {
		return blockedTime;
	}

	public void setBlockedTime(Instant blockedTime) {
		this.blockedTime = blockedTime;
	}

	public Integer getCountWrongPassword() {
		return countWrongPassword;
	}

	public void setCountWrongPassword(Integer countWrongPassword) {
		this.countWrongPassword = countWrongPassword;
	}
}
