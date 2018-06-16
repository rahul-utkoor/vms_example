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
@Table(name="v_physical_examination")
public class VolunteerPhysicalExamination extends Auditable<String>{

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	
	@Column(name="gen_app_status")
	private String genAppStatus;
	
	@Column(name="gen_app_comments")
	private String genAppComments;
	
	@Column(name="head_status")
	private String headStatus;
	
	@Column(name="head_comments")
	private String headComments;
	
	@Column(name="eyes_status")
	private String eyesStatus;
	
	@Column(name="eyes_comments")
	private String eyesComments;
	
	@Column(name="ears_status")
	private String earsStatus;
	
	@Column(name="ears_comments")
	private String earsComments;
	
	@Column(name="nose_status")
	private String noseStatus;
	
	@Column(name="nose_comments")
	private String noseComments;
	
	@Column(name="oropharynx_status")
	private String oropharynxStatus;
	
	@Column(name="oropharynx_comments")
	private String oropharynxComments;
	
	@Column(name="neck_status")
	private String neckStatus;
	
	@Column(name="neck_comments")
	private String neckComments;
	
	@Column(name="musculoskeletal_status")
	private String musculoskeletalStatus;
	
	@Column(name="musculoskeletal_comments")
	private String musculoskeletalComments;
	
	@Column(name="extremities_status")
	private String extremitiesStatus;
	
	@Column(name="extremities_comments")
	private String extremitiesComments;
	
	@Column(name="skin_status")
	private String skinStatus;
	
	@Column(name="skin_comments")
	private String skinComments;
	
	@Column(name="overall_comments")
	private String overAllComments;
	
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

	
	/*@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	@JoinColumn(name="volunteer_registration_number",referencedColumnName="registration_number",unique=true)
	private Volunteer volunteer;*/
	
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getGenAppStatus() {
		return genAppStatus;
	}


	public void setGenAppStatus(String genAppStatus) {
		this.genAppStatus = genAppStatus;
	}


	public String getGenAppComments() {
		return genAppComments;
	}


	public void setGenAppComments(String genAppComments) {
		this.genAppComments = genAppComments;
	}


	public String getHeadStatus() {
		return headStatus;
	}


	public void setHeadStatus(String headStatus) {
		this.headStatus = headStatus;
	}


	public String getHeadComments() {
		return headComments;
	}


	public void setHeadComments(String headComments) {
		this.headComments = headComments;
	}


	public String getEyesStatus() {
		return eyesStatus;
	}


	public void setEyesStatus(String eyesStatus) {
		this.eyesStatus = eyesStatus;
	}


	public String getEyesComments() {
		return eyesComments;
	}


	public void setEyesComments(String eyesComments) {
		this.eyesComments = eyesComments;
	}


	public String getEarsStatus() {
		return earsStatus;
	}


	public void setEarsStatus(String earsStatus) {
		this.earsStatus = earsStatus;
	}


	public String getEarsComments() {
		return earsComments;
	}


	public void setEarsComments(String earsComments) {
		this.earsComments = earsComments;
	}


	public String getNoseStatus() {
		return noseStatus;
	}


	public void setNoseStatus(String noseStatus) {
		this.noseStatus = noseStatus;
	}


	public String getNoseComments() {
		return noseComments;
	}


	public void setNoseComments(String noseComments) {
		this.noseComments = noseComments;
	}


	public String getOropharynxStatus() {
		return oropharynxStatus;
	}


	public void setOropharynxStatus(String oropharynxStatus) {
		this.oropharynxStatus = oropharynxStatus;
	}


	public String getOropharynxComments() {
		return oropharynxComments;
	}


	public void setOropharynxComments(String oropharynxComments) {
		this.oropharynxComments = oropharynxComments;
	}


	public String getNeckStatus() {
		return neckStatus;
	}


	public void setNeckStatus(String neckStatus) {
		this.neckStatus = neckStatus;
	}


	public String getNeckComments() {
		return neckComments;
	}


	public void setNeckComments(String neckComments) {
		this.neckComments = neckComments;
	}


	public String getMusculoskeletalStatus() {
		return musculoskeletalStatus;
	}


	public void setMusculoskeletalStatus(String musculoskeletalStatus) {
		this.musculoskeletalStatus = musculoskeletalStatus;
	}


	public String getMusculoskeletalComments() {
		return musculoskeletalComments;
	}


	public void setMusculoskeletalComments(String musculoskeletalComments) {
		this.musculoskeletalComments = musculoskeletalComments;
	}


	public String getExtremitiesStatus() {
		return extremitiesStatus;
	}


	public void setExtremitiesStatus(String extremitiesStatus) {
		this.extremitiesStatus = extremitiesStatus;
	}


	public String getExtremitiesComments() {
		return extremitiesComments;
	}


	public void setExtremitiesComments(String extremitiesComments) {
		this.extremitiesComments = extremitiesComments;
	}


	public String getSkinStatus() {
		return skinStatus;
	}


	public void setSkinStatus(String skinStatus) {
		this.skinStatus = skinStatus;
	}


	public String getSkinComments() {
		return skinComments;
	}


	public void setSkinComments(String skinComments) {
		this.skinComments = skinComments;
	}


	public String getOverAllComments() {
		return overAllComments;
	}


	public void setOverAllComments(String overAllComments) {
		this.overAllComments = overAllComments;
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
	}*/


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
