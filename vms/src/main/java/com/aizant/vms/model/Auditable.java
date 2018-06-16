package com.aizant.vms.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass

@EntityListeners(AuditingEntityListener.class)

public abstract class Auditable<U> {

	@CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,updatable=false)
    private Date created = new Date();

    @CreatedBy
    @Basic(optional = false)
    @Column(name = "CREATED_BY", nullable = false, length = 24,updatable=false)
    private String createdBy = "";

    @LastModifiedDate
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated = new Date();

    @LastModifiedBy
    @Basic(optional = false)
    @Column(name = "UPDATED_BY", nullable = false, length = 24)
    private String updatedBy = "";
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Kolkata")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdated() {
		return updated;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Kolkata")
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}



}