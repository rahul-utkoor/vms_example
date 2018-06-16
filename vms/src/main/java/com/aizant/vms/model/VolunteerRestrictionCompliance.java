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
@Table(name="v_restriction_compliance")
public class VolunteerRestrictionCompliance extends Auditable<String>{

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
/*	id,rc_v_id, rc_v_intials, rc_beverages_48, rc_alcohol_48, rc_tobacco_48, 
	rc_volunteer_restriction_compliance, rc_bev_tobacco_alcohol_21,
	rc_recorded_by, rc_rcd_ts,rc_reviewed_by, rc_rvd_ts,rc_dt*/
	
	@Column(name="bevarage_consumption_48")
	private Boolean bevarageConsumption;
	
	@Column(name="alcohol_consumption_48")
	private Boolean alcoholConsumption;
	
	@Column(name="tobacco_consumption_48")
	private Boolean tobaccoConsumption;
	
	@Column(name="comply_with_restriction_compliance")
	private Boolean complyWithCompliance;
	
	@Column(name="instructed_not_to_consume_in_next_21days")
	private Boolean instructedNotToConsume;
	
	@Column(name="recorded_by",nullable=false)
	private String recordedBy;
	
	@Column(name="recorded_on")
	private Timestamp recordedOn;
	
	@Column(name="reviewd_by")
	private Long reviewedBy;
	
	@Column(name="reviewd_on")
	private Timestamp reviewedOn;
	
	@Transient
	private String reviewedOnDate;
	
	@Transient
	private String recordedOnDate;
	
	@Transient
	private String volunteerIntials;
	
	@Transient
	private String volunteerRegNum;
	@Transient
	private String formNumber;
	@Transient
	private String sopNumber;
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
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	@JoinColumn(name="volunteer_center_id",referencedColumnName="id")
	private StudyCenter center;
	
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

	public Boolean getBevarageConsumption() {
		return bevarageConsumption;
	}

	public void setBevarageConsumption(Boolean bevarageConsumption) {
		this.bevarageConsumption = bevarageConsumption;
	}

	public Boolean getAlcoholConsumption() {
		return alcoholConsumption;
	}

	public void setAlcoholConsumption(Boolean alcoholConsumption) {
		this.alcoholConsumption = alcoholConsumption;
	}

	public Boolean getTobaccoConsumption() {
		return tobaccoConsumption;
	}

	public void setTobaccoConsumption(Boolean tobaccoConsumption) {
		this.tobaccoConsumption = tobaccoConsumption;
	}

	public Boolean getComplyWithCompliance() {
		return complyWithCompliance;
	}

	public void setComplyWithCompliance(Boolean complyWithCompliance) {
		this.complyWithCompliance = complyWithCompliance;
	}

	public Boolean getInstructedNotToConsume() {
		return instructedNotToConsume;
	}

	public void setInstructedNotToConsume(Boolean instructedNotToConsume) {
		this.instructedNotToConsume = instructedNotToConsume;
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

	public String getVolunteerIntials() {
		return volunteerIntials;
	}

	public void setVolunteerIntials(String volunteerIntials) {
		this.volunteerIntials = volunteerIntials;
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
