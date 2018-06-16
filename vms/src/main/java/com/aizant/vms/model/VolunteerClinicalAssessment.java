package com.aizant.vms.model;

import java.sql.Timestamp;

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
@Table(name="v_clinical_assessment")
public class VolunteerClinicalAssessment extends Auditable<String>{

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(name="hematology_status")
	private String hematologyStatus;
	
	@Column(name="hematology_comments")
	private String hematologyComments;
	
	@Column(name="biochemistry_status")
	private String biochemistryStatus;
	
	@Column(name="biochemistry_comments")
	private String biochemistryComments;
	
	@Column(name="serology_status")
	private String serologyStatus;
	
	@Column(name="serology_comments")
	private String serologyComments;
	
	@Column(name="urine_analysis_status")
	private String urineAnalysisStatus;
	
	@Column(name="urine_analysis_comments")
	private String urineAnalysisComments;
	
	@Column(name="ecg_status")
	private String ecgStatus;
	
	@Column(name="ecg_comments")
	private String ecgComments;
	
	@Column(name="chest_x_ray_status")
	private String chestXRayStatus;
	
	@Column(name="chest_x_ray_comments")
	private String chestXRayComments;
	
	@Column(name="others_status")
	private String othersStatus;
	
	@Column(name="others_comments")
	private String othersComments;
	
	@Column(name="bmi_details")
	private String bmiDetails;
	
	@Column(name="bmi_comments")
	private String bmiComments;
	
	@Column(name="additional_comments")
	private String additionalComments;
	
	@Column(name="volunteer_healthy")
	private Boolean volunteerHealthy;
	
	@Column(name = "recorded_by",nullable=false)
	private String recordedBy;

	@Column(name = "recorded_on")
	private Timestamp recordedOn;

	@Column(name = "reviewed_by")
	private String reviewedBy;

	@Column(name = "reviewed_on")
	private Timestamp reviewedOn;
	
	@Transient
	private Boolean isRejectedPermanantly;
	
	@Transient
	private Long rejectedNoOfDays;
	
	@Transient
	private String reasonForRejection;
	
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

	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "volunteerScreeningHistory_id", nullable = false)
	private VolunteerScreeningHistory volunteerScreeningHistory;
	
	@JsonIgnore
	public VolunteerScreeningHistory getVolunteerScreeningHistory() {
		return volunteerScreeningHistory;
	}

	public void setVolunteerScreeningHistory(VolunteerScreeningHistory volunteerScreeningHistory) {
		this.volunteerScreeningHistory = volunteerScreeningHistory;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="formNumbers_id",referencedColumnName="id")
	SopFormNumbers formNumbers;
	
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="volunteer_center_id",referencedColumnName="id")
	private StudyCenter center;
	
	@Transient
	private String reviewedOnDate;
	
	@Transient
	private String recordedOnDate;

	@Transient
	private String volunteerRegNum;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHematologyStatus() {
		return hematologyStatus;
	}

	public void setHematologyStatus(String hematologyStatus) {
		this.hematologyStatus = hematologyStatus;
	}

	public String getHematologyComments() {
		return hematologyComments;
	}

	public void setHematologyComments(String hematologyComments) {
		this.hematologyComments = hematologyComments;
	}

	public String getBiochemistryStatus() {
		return biochemistryStatus;
	}

	public void setBiochemistryStatus(String biochemistryStatus) {
		this.biochemistryStatus = biochemistryStatus;
	}

	public String getBiochemistryComments() {
		return biochemistryComments;
	}

	public void setBiochemistryComments(String biochemistryComments) {
		this.biochemistryComments = biochemistryComments;
	}

	public String getSerologyStatus() {
		return serologyStatus;
	}

	public void setSerologyStatus(String serologyStatus) {
		this.serologyStatus = serologyStatus;
	}

	public String getSerologyComments() {
		return serologyComments;
	}

	public void setSerologyComments(String serologyComments) {
		this.serologyComments = serologyComments;
	}

	public String getUrineAnalysisStatus() {
		return urineAnalysisStatus;
	}

	public void setUrineAnalysisStatus(String urineAnalysisStatus) {
		this.urineAnalysisStatus = urineAnalysisStatus;
	}

	public String getUrineAnalysisComments() {
		return urineAnalysisComments;
	}

	public void setUrineAnalysisComments(String urineAnalysisComments) {
		this.urineAnalysisComments = urineAnalysisComments;
	}

	public String getEcgStatus() {
		return ecgStatus;
	}

	public void setEcgStatus(String ecgStatus) {
		this.ecgStatus = ecgStatus;
	}

	public String getEcgComments() {
		return ecgComments;
	}

	public void setEcgComments(String ecgComments) {
		this.ecgComments = ecgComments;
	}


	public String getChestXRayStatus() {
		return chestXRayStatus;
	}

	public void setChestXRayStatus(String chestXRayStatus) {
		this.chestXRayStatus = chestXRayStatus;
	}

	public String getChestXRayComments() {
		return chestXRayComments;
	}

	public void setChestXRayComments(String chestXRayComments) {
		this.chestXRayComments = chestXRayComments;
	}

	public String getOthersStatus() {
		return othersStatus;
	}

	public void setOthersStatus(String othersStatus) {
		this.othersStatus = othersStatus;
	}

	public String getOthersComments() {
		return othersComments;
	}

	public void setOthersComments(String othersComments) {
		this.othersComments = othersComments;
	}

	public String getAdditionalComments() {
		return additionalComments;
	}

	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public Boolean getVolunteerHealthy() {
		return volunteerHealthy;
	}

	public void setVolunteerHealthy(Boolean volunteerHealthy) {
		this.volunteerHealthy = volunteerHealthy;
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


	public StudyCenter getCenter() {
		return center;
	}

	public void setCenter(StudyCenter center) {
		this.center = center;
	}

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

	public String getBmiDetails() {
		return bmiDetails;
	}

	public void setBmiDetails(String bmiDetails) {
		this.bmiDetails = bmiDetails;
	}

	public String getBmiComments() {
		return bmiComments;
	}

	public void setBmiComments(String bmiComments) {
		this.bmiComments = bmiComments;
	}

	public String getVolunteerRegNum() {
		return volunteerRegNum;
	}

	public void setVolunteerRegNum(String volunteerRegNum) {
		this.volunteerRegNum = volunteerRegNum;
	}

	public Boolean getIsRejectedPermanantly() {
		return isRejectedPermanantly;
	}

	public void setIsRejectedPermanantly(Boolean isRejectedPermanantly) {
		this.isRejectedPermanantly = isRejectedPermanantly;
	}

	public Long getRejectedNoOfDays() {
		return rejectedNoOfDays;
	}

	public void setRejectedNoOfDays(Long rejectedNoOfDays) {
		this.rejectedNoOfDays = rejectedNoOfDays;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
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
