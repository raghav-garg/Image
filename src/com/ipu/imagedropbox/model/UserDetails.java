package com.ipu.imagedropbox.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/*
 *@author Raghav
 */
@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private String password;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<ImageDetails> imageDetails;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ImageDetails> getImageDetails() {
		return imageDetails;
	}

	public void setImageDetails(List<ImageDetails> imageDetails) {
		this.imageDetails = imageDetails;
	}

}
