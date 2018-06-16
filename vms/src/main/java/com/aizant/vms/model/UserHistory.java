package com.aizant.vms.model;

import static javax.persistence.EnumType.STRING;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Naresh 
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserHistory extends Auditable<String>{
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_user_history"))
    private User user;

    private String fileContent;

    @Column(name="last_login_time")
    private Date lastLoginTime;
    
    @Column(name="last_logout_time")
    private Date lastLogoutTime;

    @Enumerated(STRING)
    private Action action;

    public UserHistory() {
    }

    public UserHistory(User user, Action action) {
        this.user = user;
        this.fileContent = user.toString();
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}


    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}