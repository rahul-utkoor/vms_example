package com.aizant.vms.model;

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
@Table(name = "v_menstrual_history")
public class VolunteerMenstrualHistory extends Auditable<String>{

	@Id
	@GeneratedValue
	@Column
	private Long id;


	@Column(name = "lmp_details")
	private String lmpDetails;

	@Column(name = "lmp_comments")
	private String lmpComments;


	@Column(name = "duration_of_flow_details")
	private String durtionOfFlowDetails;

	@Column(name = "duration_of_flow_comments")
	private String durtionOfFlowComments;


	@Column(name = "regular_irreguular_details")
	private String regularDetails;

	@Column(name = "regular_irreguular_comments")
	private String regularComments;
	
	@Column(name="length_of_cycle_details")
	private String lengthOfCycleDetails;
	
	@Column(name="length_of_cycle_comments")
	private String lengthOfCycleComments;


	@Column(name = "ho_dysmenorrhoea_details")
	private String hoDysmenorrhoeaDetails;

	@Column(name = "ho_dysmenorrhoea_comments")
	private String hoDysmenorrhoeaComments;

	@Column(name = "ho_amenorrhoea_details")
	private String hoAmenorrhoeaDetails;
	
	@Column(name = "ho_amenorrhoea_comments")
	private String hoAmenorrhoeaComments;
	
	@Column(name = "ho_leucorrhoea_details")
	private String hoLeucorrhoeaDetails;
	
	@Column(name = "ho_leucorrhoea_comments")
	private String hoLeucorrhoeaComments;

	@Column(name = "obestric_age")
	private String obestricAge;

	@Column(name = "obestric_method_of_delivery")
	private String obestricMethodOfDelivery;

	@Column(name = "obestric_gender")
	private String obestricGender;

	@Column(name = "obestric_health_status")
	private String obestricHealthStatus;

	@Column(name = "obestric_comments")
	private String obestricComments;
	
	@Column(name="abortion_history_month_year")
	private String abortionHistoryMonthYear;
	
	@Column(name="abortion_corresponding_age")
	private String abortionCorrespondingAge;

	@Column(name = "breast_feeding_currently")
	private Boolean breastFeedingCurrently;

	@Column(name = "hormonal_treatment")
	private Boolean hormonalTreatment;

	@Column(name = "hormonal_treatment_comments")
	private String hormonalTreatmentComments;

	@Column(name = "gynaecological_exam")
	private String gynaecologicalExam;

	@Column(name = "gynaecological_exam_comments")
	private String gynaecologicalExamComments;
	
	@Column(name = "recorded_by",nullable=false)
	private String recordedBy;

	@Column(name = "recorded_on")
	private Timestamp recordedOn;

	/*@Column(name = "created_on")
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
	private String reviewedOnDate;
	
	@Transient
	private String recordedOnDate;
	
	@Transient
	private String volunteerRegNum;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getLmpDetails() {
		return lmpDetails;
	}

	public void setLmpDetails(String lmpDetails) {
		this.lmpDetails = lmpDetails;
	}

	public String getLmpComments() {
		return lmpComments;
	}

	public void setLmpComments(String lmpComments) {
		this.lmpComments = lmpComments;
	}


	public String getDurtionOfFlowDetails() {
		return durtionOfFlowDetails;
	}

	public void setDurtionOfFlowDetails(String durtionOfFlowDetails) {
		this.durtionOfFlowDetails = durtionOfFlowDetails;
	}

	public String getDurtionOfFlowComments() {
		return durtionOfFlowComments;
	}

	public void setDurtionOfFlowComments(String durtionOfFlowComments) {
		this.durtionOfFlowComments = durtionOfFlowComments;
	}


	public String getRegularDetails() {
		return regularDetails;
	}

	public void setRegularDetails(String regularDetails) {
		this.regularDetails = regularDetails;
	}

	public String getRegularComments() {
		return regularComments;
	}

	public void setRegularComments(String regularComments) {
		this.regularComments = regularComments;
	}


	public String getHoDysmenorrhoeaDetails() {
		return hoDysmenorrhoeaDetails;
	}

	public void setHoDysmenorrhoeaDetails(String hoDysmenorrhoeaDetails) {
		this.hoDysmenorrhoeaDetails = hoDysmenorrhoeaDetails;
	}

	public String getHoDysmenorrhoeaComments() {
		return hoDysmenorrhoeaComments;
	}

	public void setHoDysmenorrhoeaComments(String hoDysmenorrhoeaComments) {
		this.hoDysmenorrhoeaComments = hoDysmenorrhoeaComments;
	}


	public String getObestricAge() {
		return obestricAge;
	}

	public void setObestricAge(String obestricAge) {
		this.obestricAge = obestricAge;
	}

	public String getObestricMethodOfDelivery() {
		return obestricMethodOfDelivery;
	}

	public void setObestricMethodOfDelivery(String obestricMethodOfDelivery) {
		this.obestricMethodOfDelivery = obestricMethodOfDelivery;
	}

	public String getObestricGender() {
		return obestricGender;
	}

	public void setObestricGender(String obestricGender) {
		this.obestricGender = obestricGender;
	}

	public String getObestricHealthStatus() {
		return obestricHealthStatus;
	}

	public void setObestricHealthStatus(String obestricHealthStatus) {
		this.obestricHealthStatus = obestricHealthStatus;
	}


	public Boolean getBreastFeedingCurrently() {
		return breastFeedingCurrently;
	}

	public void setBreastFeedingCurrently(Boolean breastFeedingCurrently) {
		this.breastFeedingCurrently = breastFeedingCurrently;
	}

	public Boolean getHormonalTreatment() {
		return hormonalTreatment;
	}

	public void setHormonalTreatment(Boolean hormonalTreatment) {
		this.hormonalTreatment = hormonalTreatment;
	}

	public String getAbortionHistoryMonthYear() {
		return abortionHistoryMonthYear;
	}

	public void setAbortionHistoryMonthYear(String abortionHistoryMonthYear) {
		this.abortionHistoryMonthYear = abortionHistoryMonthYear;
	}

	public String getAbortionCorrespondingAge() {
		return abortionCorrespondingAge;
	}

	public void setAbortionCorrespondingAge(String abortionCorrespondingAge) {
		this.abortionCorrespondingAge = abortionCorrespondingAge;
	}

	public String getHormonalTreatmentComments() {
		return hormonalTreatmentComments;
	}

	public void setHormonalTreatmentComments(String hormonalTreatmentComments) {
		this.hormonalTreatmentComments = hormonalTreatmentComments;
	}

	public String getGynaecologicalExam() {
		return gynaecologicalExam;
	}

	public void setGynaecologicalExam(String gynaecologicalExam) {
		this.gynaecologicalExam = gynaecologicalExam;
	}

	public String getGynaecologicalExamComments() {
		return gynaecologicalExamComments;
	}

	public void setGynaecologicalExamComments(String gynaecologicalExamComments) {
		this.gynaecologicalExamComments = gynaecologicalExamComments;
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

	public String getLengthOfCycleDetails() {
		return lengthOfCycleDetails;
	}

	public void setLengthOfCycleDetails(String lengthOfCycleDetails) {
		this.lengthOfCycleDetails = lengthOfCycleDetails;
	}

	public String getLengthOfCycleComments() {
		return lengthOfCycleComments;
	}

	public void setLengthOfCycleComments(String lengthOfCycleComments) {
		this.lengthOfCycleComments = lengthOfCycleComments;
	}

	public String getHoAmenorrhoeaDetails() {
		return hoAmenorrhoeaDetails;
	}

	public void setHoAmenorrhoeaDetails(String hoAmenorrhoeaDetails) {
		this.hoAmenorrhoeaDetails = hoAmenorrhoeaDetails;
	}

	public String getHoAmenorrhoeaComments() {
		return hoAmenorrhoeaComments;
	}

	public void setHoAmenorrhoeaComments(String hoAmenorrhoeaComments) {
		this.hoAmenorrhoeaComments = hoAmenorrhoeaComments;
	}

	public String getHoLeucorrhoeaDetails() {
		return hoLeucorrhoeaDetails;
	}

	public void setHoLeucorrhoeaDetails(String hoLeucorrhoeaDetails) {
		this.hoLeucorrhoeaDetails = hoLeucorrhoeaDetails;
	}

	public String getHoLeucorrhoeaComments() {
		return hoLeucorrhoeaComments;
	}

	public void setHoLeucorrhoeaComments(String hoLeucorrhoeaComments) {
		this.hoLeucorrhoeaComments = hoLeucorrhoeaComments;
	}

	public String getObestricComments() {
		return obestricComments;
	}

	public void setObestricComments(String obestricComments) {
		this.obestricComments = obestricComments;
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
