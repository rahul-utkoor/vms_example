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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "volunteer_ecg")
public class VolunteerECG extends Auditable<String>{

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(name = "ecg_date")
	private Date ecgDate;

	@Column(name = "ecg_time")
	private String ecgTime;
	
	@Column(name = "ecg_file_location")
	private String ecgFileLocation;
	
	@Column(name="report_confimed_by")
	private String reportConfirmedBy;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Kolkata")
	@Column(name="report_confimed_time")
	private Timestamp reportConfirmedTime;
	
	@Column(name = "ecg_hr")
	private String ecgHr;

	@Column(name = "ecg_p")
	private String ecgP;

	@Column(name = "ecg_pr")
	private String ecgPR;

	@Column(name = "ecg_qrs")
	private String ecgQrs;

	@Column(name = "ecg_qt")
	private String ecgQT;

	@Column(name = "ecg_pqrs")
	private String ecgPqrs;

	@Column(name = "ecg_rv5")
	private String ecgRv5;

	@Column(name = "recorded_by",nullable=false)
	private String recordedBy;
	
	@Column(name = "recorded_comments")
	private String recordedComments;
	
	@Column(name = "recorded_on")
	private Timestamp recordedOn;

	@Column(name="test_date")
	private String testDate;
	
	/*@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@Column(name = "updated_by")
	private Long updatedBy;*/

	@Column(name = "reviewed_by")
	private String reviewedBy;
	
	@Column(name = "reviewed_comments")
	private String reviewedComments;
	
	@Column(name = "reviewed_on")
	private Timestamp reviewedOn;

	@Transient
	private String reviewedOnDate;

	@Transient
	private String recordedOnDate;
	@Transient
	private String imageName;

	/*@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "volunteer_registration_number", referencedColumnName = "registration_number")
	private Volunteer volunteer;*/

	
	@Column(name="volunteer_registration_number",nullable=false)
	private String regNum;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "volunteer_center_id", referencedColumnName = "id")
	private StudyCenter center;

	@Transient
	private String volunteerRegNum;
	
	@Transient
	private String volunteerAge;
	
	@Transient
	private String volunteerSex;
	
	@Transient
	private String updateComments;
	
	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEcgFileLocation() {
		return ecgFileLocation;
	}

	public void setEcgFileLocation(String ecgFileLocation) {
		this.ecgFileLocation = ecgFileLocation;
	}

	public String getReportConfirmedBy() {
		return reportConfirmedBy;
	}

	public void setReportConfirmedBy(String reportConfirmedBy) {
		this.reportConfirmedBy = reportConfirmedBy;
	}

	public String getEcgHr() {
		return ecgHr;
	}

	public void setEcgHr(String ecgHr) {
		this.ecgHr = ecgHr;
	}

	public String getEcgP() {
		return ecgP;
	}

	public void setEcgP(String ecgP) {
		this.ecgP = ecgP;
	}

	public String getEcgPR() {
		return ecgPR;
	}

	public void setEcgPR(String ecgPR) {
		this.ecgPR = ecgPR;
	}

	public String getEcgQrs() {
		return ecgQrs;
	}

	public void setEcgQrs(String ecgQrs) {
		this.ecgQrs = ecgQrs;
	}

	public String getEcgQT() {
		return ecgQT;
	}

	public void setEcgQT(String ecgQT) {
		this.ecgQT = ecgQT;
	}

	public String getEcgPqrs() {
		return ecgPqrs;
	}

	public void setEcgPqrs(String ecgPqrs) {
		this.ecgPqrs = ecgPqrs;
	}

	public String getEcgRv5() {
		return ecgRv5;
	}

	public void setEcgRv5(String ecgRv5) {
		this.ecgRv5 = ecgRv5;
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
	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public Timestamp getReviewedOn() {
		return reviewedOn;
	}

	public void setReviewedOn(Timestamp reviewedOn) {
		this.reviewedOn = reviewedOn;
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

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}


	public StudyCenter getCenter() {
		return center;
	}

	public void setCenter(StudyCenter center) {
		this.center = center;
	}

	public Date getEcgDate() {
		return ecgDate;
	}

	public void setEcgDate(Date ecgDate) {
		this.ecgDate = ecgDate;
	}

	public String getEcgTime() {
		return ecgTime;
	}

	public void setEcgTime(String ecgTime) {
		this.ecgTime = ecgTime;
	}

	public Timestamp getReportConfirmedTime() {
		return reportConfirmedTime;
	}

	public void setReportConfirmedTime(Timestamp reportConfirmedTime) {
		this.reportConfirmedTime = reportConfirmedTime;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getVolunteerRegNum() {
		return volunteerRegNum;
	}

	public void setVolunteerRegNum(String volunteerRegNum) {
		this.volunteerRegNum = volunteerRegNum;
	}

	public String getVolunteerAge() {
		return volunteerAge;
	}

	public void setVolunteerAge(String volunteerAge) {
		this.volunteerAge = volunteerAge;
	}

	public String getVolunteerSex() {
		return volunteerSex;
	}

	public void setVolunteerSex(String volunteerSex) {
		this.volunteerSex = volunteerSex;
	}
	
	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public String getRecordedComments() {
		return recordedComments;
	}

	public void setRecordedComments(String recordedComments) {
		this.recordedComments = recordedComments;
	}

	public String getReviewedComments() {
		return reviewedComments;
	}

	public void setReviewedComments(String reviewedComments) {
		this.reviewedComments = reviewedComments;
	}


}
