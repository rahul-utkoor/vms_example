package com.aizant.vms.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="v_systemic_examination")
public class VolunteerSystemicExamination extends Auditable<String>{

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(name="cardiovascular_system_status")
	private String cardiovascularSystemStatus;
	
	@Column(name="cardiovascular_system_comments")
	private String cardiovascularSystemComments;
	
	@Column(name="respiratory_system_status")
	private String respiratorySystemStatus;
	
	@Column(name="respiratory_system_comments")
	private String respiratorySystemComments;
	
	@Column(name="abdomen_status")
	private String abdomenStatus;
	
	@Column(name="abdomen_comments")
	private String abdomenComments;
	
	@Column(name="central_nervous_system_status")
	private String centralNervousSystemStatus;
	
	@Column(name="central_nervous_system_comments")
	private String centralNervousSystemComments;
	
	@Column(name="overall_comments")
	private String overallComments;
	
	@Column(name="comments_on_physical_examination")
	private String commentsOnPhysicalExamination;
	
	@Column(name="examination_end_Date")
	private Date examinationEndDate;
	
	@Column(name="examination_end_time")
	private String examinationEndTime;
	
	@Column(name = "recorded_by",nullable=false)
	private String recordedBy;

	@Column(name = "recorded_on")
	private Timestamp recordedOn;

/*	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@Column(name = "updated_by")
	private Long updatedBy;*/

	@Column(name = "reviewed_by")
	private Long reviewedBy;

	@Column(name = "reviewed_on")
	private Timestamp reviewedOn;
	
	@Transient
	private String updateComments;
	@Transient
	private String formNumber;
	@Transient
	private String sopNumber;
	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}

	
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	@JoinColumn(name="volunteer_center_id",referencedColumnName="id")
	private StudyCenter center;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "volunteerScreeningHistory_id", nullable = false)
	private VolunteerScreeningHistory volunteerScreeningHistory;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="formNumbers_id",referencedColumnName="id")
	SopFormNumbers formNumbers;
	
	@JsonIgnore
	public VolunteerScreeningHistory getVolunteerScreeningHistory() {
		return volunteerScreeningHistory;
	}

	public void setVolunteerScreeningHistory(VolunteerScreeningHistory volunteerScreeningHistory) {
		this.volunteerScreeningHistory = volunteerScreeningHistory;
	}
	
	
	@Transient
	private String reviewedOnDate;
	
	@Transient
	private String recordedOnDate;

	@Transient
	private String volunteerRegNum;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCardiovascularSystemStatus() {
		return cardiovascularSystemStatus;
	}


	public void setCardiovascularSystemStatus(String cardiovascularSystemStatus) {
		this.cardiovascularSystemStatus = cardiovascularSystemStatus;
	}


	public String getCardiovascularSystemComments() {
		return cardiovascularSystemComments;
	}


	public void setCardiovascularSystemComments(String cardiovascularSystemComments) {
		this.cardiovascularSystemComments = cardiovascularSystemComments;
	}


	public String getRespiratorySystemStatus() {
		return respiratorySystemStatus;
	}


	public void setRespiratorySystemStatus(String respiratorySystemStatus) {
		this.respiratorySystemStatus = respiratorySystemStatus;
	}


	public String getRespiratorySystemComments() {
		return respiratorySystemComments;
	}


	public void setRespiratorySystemComments(String respiratorySystemComments) {
		this.respiratorySystemComments = respiratorySystemComments;
	}


	public String getAbdomenStatus() {
		return abdomenStatus;
	}


	public void setAbdomenStatus(String abdomenStatus) {
		this.abdomenStatus = abdomenStatus;
	}


	public String getAbdomenComments() {
		return abdomenComments;
	}


	public void setAbdomenComments(String abdomenComments) {
		this.abdomenComments = abdomenComments;
	}


	public String getCentralNervousSystemStatus() {
		return centralNervousSystemStatus;
	}


	public void setCentralNervousSystemStatus(String centralNervousSystemStatus) {
		this.centralNervousSystemStatus = centralNervousSystemStatus;
	}


	public String getCentralNervousSystemComments() {
		return centralNervousSystemComments;
	}


	public void setCentralNervousSystemComments(String centralNervousSystemComments) {
		this.centralNervousSystemComments = centralNervousSystemComments;
	}


	public String getOverallComments() {
		return overallComments;
	}


	public void setOverallComments(String overallComments) {
		this.overallComments = overallComments;
	}


	public String getCommentsOnPhysicalExamination() {
		return commentsOnPhysicalExamination;
	}


	public void setCommentsOnPhysicalExamination(String commentsOnPhysicalExamination) {
		this.commentsOnPhysicalExamination = commentsOnPhysicalExamination;
	}



	public Date getExaminationEndDate() {
		return examinationEndDate;
	}


	public void setExaminationEndDate(Date examinationEndDate) {
		this.examinationEndDate = examinationEndDate;
	}


	public String getExaminationEndTime() {
		return examinationEndTime;
	}


	public void setExaminationEndTime(String examinationEndTime) {
		this.examinationEndTime = examinationEndTime;
	}


	public String getRecordedBy() {
		return recordedBy;
	}


	public void setRecordedBy(String recordedBy) {
		this.recordedBy = recordedBy;
	}


	public Timestamp getRecordedOn() {
		return recordedOn;
	}


	public void setRecordedOn(Timestamp recordedOn) {
		this.recordedOn = recordedOn;
	}


	/*public Timestamp getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}


	public Long getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}


	public Timestamp getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}


	public Long getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

*/
	public Long getReviewedBy() {
		return reviewedBy;
	}


	public void setReviewedBy(Long reviewedBy) {
		this.reviewedBy = reviewedBy;
	}


	public Timestamp getReviewedOn() {
		return reviewedOn;
	}


	public void setReviewedOn(Timestamp reviewedOn) {
		this.reviewedOn = reviewedOn;
	}


	public StudyCenter getCenter() {
		return center;
	}


	public void setCenter(StudyCenter center) {
		this.center = center;
	}


	public String getReviewedOnDate() {
		return reviewedOnDate;
	}


	public void setReviewedOnDate(String reviewedOnDate) {
		this.reviewedOnDate = reviewedOnDate;
	}


	public String getRecordedOnDate() {
		return recordedOnDate;
	}


	public void setRecordedOnDate(String recordedOnDate) {
		this.recordedOnDate = recordedOnDate;
	}


	public String getVolunteerRegNum() {
		return volunteerRegNum;
	}


	public void setVolunteerRegNum(String volunteerRegNum) {
		this.volunteerRegNum = volunteerRegNum;
	}

	public SopFormNumbers getFormNumbers() {
		return formNumbers;
	}

	public void setFormNumbers(SopFormNumbers formNumbers) {
		this.formNumbers = formNumbers;
	}

	public String getFormNumber() {
		return formNumber;
	}

	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}

	public String getSopNumber() {
		return sopNumber;
	}

	public void setSopNumber(String sopNumber) {
		this.sopNumber = sopNumber;
	}
	
	
	
	
}
