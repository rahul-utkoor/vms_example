package com.aizant.vms.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "volunteer_screening_history")
public class VolunteerScreeningHistory extends Auditable<String> implements Serializable {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(name = "screening_date")
	private Timestamp screeningDate;

	@Column(name = "reviewer_comments")
	private String reviewerComments;

	@Column(name = "crf_comments")
	private String crfComments;

	@Column(name = "sample_comments")
	private String sampleComments;

	@Column(name = "reviewd_date")
	private Timestamp reviewdDate;

	@Column(name = "reviewd_by")
	private String reviewedBy;

	@Transient
	private Date nextEligibleDate;

	@Column(name = "volunteer_id", nullable = false)
	private String volunteerRegNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Timestamp getScreeningDate() {
		return screeningDate;
	}

	public void setScreeningDate(Timestamp screeningDate) {
		this.screeningDate = screeningDate;
	}

	public String getReviewerComments() {
		return reviewerComments;
	}

	public void setReviewerComments(String reviewerComments) {
		this.reviewerComments = reviewerComments;
	}

	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public Date getNextEligibleDate() {
		return nextEligibleDate;
	}

	public void setNextEligibleDate(Date nextEligibleDate) {
		this.nextEligibleDate = nextEligibleDate;
	}

	public String getVolunteerRegNum() {
		return volunteerRegNum;
	}

	public void setVolunteerRegNum(String volunteerRegNum) {
		this.volunteerRegNum = volunteerRegNum;
	}

	public String getCrfComments() {
		return crfComments;
	}

	public void setCrfComments(String crfComments) {
		this.crfComments = crfComments;
	}

	public String getSampleComments() {
		return sampleComments;
	}

	public void setSampleComments(String sampleComments) {
		this.sampleComments = sampleComments;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Timestamp getReviewdDate() {
		return reviewdDate;
	}

	public void setReviewdDate(Timestamp reviewdDate) {
		this.reviewdDate = reviewdDate;
	}

}
