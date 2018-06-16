package com.aizant.vms.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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
@Table(name = "v_demographic_profile")
public class VolunteerDemographicProfile extends Auditable<String> {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Transient
	private String initials;

	@Column(name = "screening_icf")
	private Boolean screeningICF;

	@Column(name = "screening_icf_date")
	private Date screeningicfDate;

	@Column(name = "lang_read")
	private String langRead;

	@Transient
	private List<String> langReadList;

	@Column(name = "lang_write")
	private String langWrite;

	@Transient
	private List<String> langWriteList;

	@Column(name = "gender")
	private String gender;

	@Column(name = "height")
	private Float height;

	@Column(name = "weight")
	private Float weight;

	@Column(name = "diet_status")
	private String dietStatus;

	@Transient
	private List<String> dietList;

	@Column(name = "race")
	private String dmRace;

	@Column(name = "bmi")
	private String dmBmi;

	@Column(name = "history_of_blood_donation_date")
	private Date historyOfBloodDonationDate;

	@Column(name = "last_participation_date")
	private Date lastParticipationDate;

	@Column(name = "recorded_by",nullable=false)
	private String recordedBy;

	@Column(name = "recorded_on")
	private Timestamp recordedOn;

	/*
	 * @Column(name = "created_on") private Timestamp createdOn;
	 * 
	 * @Column(name = "created_by") private Long createdBy;
	 * 
	 * @Column(name = "updated_on") private Timestamp updatedOn;
	 * 
	 * @Column(name = "updated_by") private Long updatedBy;
	 */

	@Column(name = "reviewed_by")
	private Long reviewedBy;

	@Column(name = "reviewed_on")
	private Timestamp reviewedOn;

	@Transient
	private Boolean proceedForScreening;

	@Transient
	private Long noOfDaysCompletedFromLastPartcipation;

	@Transient
	private String reviewedOnDate;

	@Transient
	private String recordedOnDate;

	@Transient
	private String volunteerIntials;

	@Transient
	private String volunteerRead;

	@Transient
	private String volunteerRegNum;

	@Transient
	private String volunteerWrite;

	@Transient
	private String volunteerGender;

	@Transient
	private String volunteerAge;
	@Transient
	private String volunteerHeight;
	@Transient
	private String volunteerWeight;
	@Transient
	private String volunteerRace;
	@Transient
	private String volunteerDiet;
	@Transient
	private String volunteerBmi;
	@Transient
	private String formNumber;
	@Transient
	private String sopNumber;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="formNumbers_id",referencedColumnName="id")
	SopFormNumbers formNumbers; 
	
	@Transient
	private String updateComments;
	
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

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "volunteer_center_id", referencedColumnName = "id")
	private StudyCenter center;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Boolean getScreeningICF() {
		return screeningICF;
	}

	public void setScreeningICF(Boolean screeningICF) {
		this.screeningICF = screeningICF;
	}

	public Date getScreeningicfDate() {
		return screeningicfDate;
	}

	public void setScreeningicfDate(Date screeningicfDate) {
		this.screeningicfDate = screeningicfDate;
	}

	public String getDmRace() {
		return dmRace;
	}

	public void setDmRace(String dmRace) {
		this.dmRace = dmRace;
	}

	public String getDmBmi() {
		return dmBmi;
	}

	public void setDmBmi(String dmBmi) {
		this.dmBmi = dmBmi;
	}

	public Date getHistoryOfBloodDonationDate() {
		return historyOfBloodDonationDate;
	}

	public void setHistoryOfBloodDonationDate(Date historyOfBloodDonationDate) {
		this.historyOfBloodDonationDate = historyOfBloodDonationDate;
	}

	public Date getLastParticipationDate() {
		return lastParticipationDate;
	}

	public void setLastParticipationDate(Date lastParticipationDate) {
		this.lastParticipationDate = lastParticipationDate;
	}

	public String getLangRead() {
		return langRead;
	}

	public void setLangRead(String langRead) {
		this.langRead = langRead;
	}

	public String getLangWrite() {
		return langWrite;
	}

	public void setLangWrite(String langWrite) {
		this.langWrite = langWrite;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	public SopFormNumbers getFormNumbers() {
		return formNumbers;
	}

	public void setFormNumbers(SopFormNumbers formNumbers) {
		this.formNumbers = formNumbers;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getDietStatus() {
		return dietStatus;
	}

	public void setDietStatus(String dietStatus) {
		this.dietStatus = dietStatus;
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

	public Boolean getProceedForScreening() {
		return proceedForScreening;
	}

	public void setProceedForScreening(Boolean proceedForScreening) {
		this.proceedForScreening = proceedForScreening;
	}

	public Long getNoOfDaysCompletedFromLastPartcipation() {
		return noOfDaysCompletedFromLastPartcipation;
	}

	public void setNoOfDaysCompletedFromLastPartcipation(Long noOfDaysCompletedFromLastPartcipation) {
		this.noOfDaysCompletedFromLastPartcipation = noOfDaysCompletedFromLastPartcipation;
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

	public List<String> getLangReadList() {
		return langReadList;
	}

	public void setLangReadList(List<String> langReadList) {
		this.langReadList = langReadList;
	}

	public List<String> getLangWriteList() {
		return langWriteList;
	}

	public void setLangWriteList(List<String> langWriteList) {
		this.langWriteList = langWriteList;
	}

	public List<String> getDietList() {
		return dietList;
	}

	public void setDietList(List<String> dietList) {
		this.dietList = dietList;
	}

	public String getVolunteerIntials() {
		return volunteerIntials;
	}

	public void setVolunteerIntials(String volunteerIntials) {
		this.volunteerIntials = volunteerIntials;
	}

	public String getVolunteerRead() {
		return volunteerRead;
	}

	public void setVolunteerRead(String volunteerRead) {
		this.volunteerRead = volunteerRead;
	}

	public String getVolunteerRegNum() {
		return volunteerRegNum;
	}

	public void setVolunteerRegNum(String volunteerRegNum) {
		this.volunteerRegNum = volunteerRegNum;
	}

	public String getVolunteerWrite() {
		return volunteerWrite;
	}

	public void setVolunteerWrite(String volunteerWrite) {
		this.volunteerWrite = volunteerWrite;
	}

	public String getVolunteerGender() {
		return volunteerGender;
	}

	public void setVolunteerGender(String volunteerGender) {
		this.volunteerGender = volunteerGender;
	}

	public String getVolunteerAge() {
		return volunteerAge;
	}

	public void setVolunteerAge(String volunteerAge) {
		this.volunteerAge = volunteerAge;
	}

	public String getVolunteerHeight() {
		return volunteerHeight;
	}

	public void setVolunteerHeight(String volunteerHeight) {
		this.volunteerHeight = volunteerHeight;
	}

	public String getVolunteerWeight() {
		return volunteerWeight;
	}

	public void setVolunteerWeight(String volunteerWeight) {
		this.volunteerWeight = volunteerWeight;
	}

	public String getVolunteerRace() {
		return volunteerRace;
	}

	public void setVolunteerRace(String volunteerRace) {
		this.volunteerRace = volunteerRace;
	}

	public String getVolunteerDiet() {
		return volunteerDiet;
	}

	public void setVolunteerDiet(String volunteerDiet) {
		this.volunteerDiet = volunteerDiet;
	}

	public String getVolunteerBmi() {
		return volunteerBmi;
	}

	public void setVolunteerBmi(String volunteerBmi) {
		this.volunteerBmi = volunteerBmi;
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

	/*
	 * public VolunteerScreeningHistory getVolunteerScreeningHistory() { return
	 * volunteerScreeningHistory; }
	 * 
	 * public void setVolunteerScreeningHistory(VolunteerScreeningHistory
	 * volunteerScreeningHistory) { this.volunteerScreeningHistory =
	 * volunteerScreeningHistory; }
	 */

}
