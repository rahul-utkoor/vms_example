package com.aizant.vms.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_login_logout_audit")
public class UserLoginLogoutAudit {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="last_login")
	private Timestamp lastLogin;
	
	@Column(name="last_logout")
	private Timestamp lastLogout;
	
	@Column(name="center_id")
	private Long centerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Timestamp getLastLogout() {
		return lastLogout;
	}

	public void setLastLogout(Timestamp lastLogout) {
		this.lastLogout = lastLogout;
	}

	public Long getCenterId() {
		return centerId;
	}

	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}
	
	
	
}
