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
@Table(name="v_personal_history")
public class VolunteerPersonalHistory extends Auditable<String>{
	
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	
	@Column(name="marital_status")
	private String maritalStatus;
	
	@Column(name="children_count")
	private Integer childrenCount;
	
	/*@Column(name="current_smoker")
	private Boolean currentSmoker;
	
	@Column(name="ex_smoker")
	private Boolean exSmoker;*/
	
	@Column(name="smoker_details")
	private String smokerDetails;
	
	@Column(name="no_of_cigar_per_day")
	private Double noOfCigarPerDay;
	
	@Column(name="smoke_stopped_duration")
	private String smokeStoppedDuration;
	
	@Column(name="date_of_stopping")
	private String dateOfStopping;
	
	/*@Column(name="current_tobacco_chewer")
	private Boolean currentTobaccoChewer;
	
	@Column(name="ex_tobacco_chewer")
	private Boolean exTobaccoChewer;*/
	
	@Column(name="chewer_details")
	private String chewerDetails;
	
	@Column(name="tobacco_type")
	private String tobaccoType;
	
	@Column(name="tobacco_stopped_duration")
	private String tobaccoStoppedDuration;
	
	@Column(name="tobacco_stopped_date")
	private String tobaccoStoppedDate;
	
	/*@Column(name="current_alcohol_consumer")
	private Boolean currentAlcoholConsumer;
	
	@Column(name="ex_alcohol_consumer")
	private Boolean exAlcoholConsumer;*/
	@Column(name="alcoholconsumer_details")
	private String alcoholConsumerDetails;
	
	@Column(name="alcohol_consumer_type")
	private String alcoholConsumerType;
	
	@Column(name="no_of_units_per_day")
	private Integer noOfUnitsPerDay;
	
	@Column(name="type_of_drink")
	private String typeOfDrink;
	
	@Column(name="date_of_last_consumption")
	private String dateOfLastConsumption;
	
/*	@Column(name="current_drug_consumer")
	private Boolean currentDrugConsumer;
	
	@Column(name="ex_drug_consumer")
	private Boolean exDrugConsumer;*/
	
	@Column(name="drug_consumer_details")
	private String drugAbuserDetails;
	
	@Column(name="substance_of_abuse")
	private String substaanceOfAbuse;
	
	@Column(name="drug_duration")
	private String drugDuration;
	
	@Column(name="drug_stopped_date")
	private String drugStoppedDate;
	
	@Column(name="appetite")
	private String appetite;
	
	@Column(name="appetite_details")
	private String appetiteDetails;
	
	@Column(name="on_special_diet")
	private Boolean onSpecialDiet;
	
	@Column(name="special_diet_details")
	private String specailDietDetails;
	
	@Column(name="allergies")
	private Boolean allergies;
	
	@Column(name="allergy_type")
	private String allergyType;
	
	@Column(name="allergy_symtoms")
	private String allergySymtoms;
	
	@Column(name="allergy_date")
	private Date allergyDate;
	
	@Transient
	private String allergyDetails;
	
	@Column(name="study_specific_allergy")
	private String studySpecificAllergy;
	
	@Column(name="allergic_to_drug")
	private String allergicToDrug;
	
	
	@Column(name="diff_faced_during_drug_admin")
	private Boolean diffFacedDuringDrugAdmin;
	
	@Column(name="diff_faced_during_drug_admin_details")
	private String diffFacedDuringDrugAdminDetails;
	
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

	
/*	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMaritalStatus() {
		return maritalStatus;
	}


	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}


	public Integer getChildrenCount() {
		return childrenCount;
	}


	public void setChildrenCount(Integer childrenCount) {
		this.childrenCount = childrenCount;
	}



	public String getSmokerDetails() {
		return smokerDetails;
	}


	public void setSmokerDetails(String smokerDetails) {
		this.smokerDetails = smokerDetails;
	}


	public Double getNoOfCigarPerDay() {
		return noOfCigarPerDay;
	}


	public void setNoOfCigarPerDay(Double noOfCigarPerDay) {
		this.noOfCigarPerDay = noOfCigarPerDay;
	}


	public String getSmokeStoppedDuration() {
		return smokeStoppedDuration;
	}


	public void setSmokeStoppedDuration(String smokeStoppedDuration) {
		this.smokeStoppedDuration = smokeStoppedDuration;
	}


	public String getDateOfStopping() {
		return dateOfStopping;
	}


	public void setDateOfStopping(String dateOfStopping) {
		this.dateOfStopping = dateOfStopping;
	}




	public String getTobaccoType() {
		return tobaccoType;
	}


	public void setTobaccoType(String tobaccoType) {
		this.tobaccoType = tobaccoType;
	}


	public String getTobaccoStoppedDuration() {
		return tobaccoStoppedDuration;
	}


	public void setTobaccoStoppedDuration(String tobaccoStoppedDuration) {
		this.tobaccoStoppedDuration = tobaccoStoppedDuration;
	}


	public String getTobaccoStoppedDate() {
		return tobaccoStoppedDate;
	}


	public void setTobaccoStoppedDate(String tobaccoStoppedDate) {
		this.tobaccoStoppedDate = tobaccoStoppedDate;
	}




	public String getAlcoholConsumerType() {
		return alcoholConsumerType;
	}


	public void setAlcoholConsumerType(String alcoholConsumerType) {
		this.alcoholConsumerType = alcoholConsumerType;
	}


	public Integer getNoOfUnitsPerDay() {
		return noOfUnitsPerDay;
	}


	public void setNoOfUnitsPerDay(Integer noOfUnitsPerDay) {
		this.noOfUnitsPerDay = noOfUnitsPerDay;
	}


	public String getTypeOfDrink() {
		return typeOfDrink;
	}


	public void setTypeOfDrink(String typeOfDrink) {
		this.typeOfDrink = typeOfDrink;
	}


	public String getDateOfLastConsumption() {
		return dateOfLastConsumption;
	}


	public void setDateOfLastConsumption(String dateOfLastConsumption) {
		this.dateOfLastConsumption = dateOfLastConsumption;
	}


	public String getSubstaanceOfAbuse() {
		return substaanceOfAbuse;
	}


	public void setSubstaanceOfAbuse(String substaanceOfAbuse) {
		this.substaanceOfAbuse = substaanceOfAbuse;
	}


	public String getDrugDuration() {
		return drugDuration;
	}


	public void setDrugDuration(String drugDuration) {
		this.drugDuration = drugDuration;
	}


	public String getDrugStoppedDate() {
		return drugStoppedDate;
	}


	public void setDrugStoppedDate(String drugStoppedDate) {
		this.drugStoppedDate = drugStoppedDate;
	}


	public String getAppetite() {
		return appetite;
	}


	public void setAppetite(String appetite) {
		this.appetite = appetite;
	}


	public String getAppetiteDetails() {
		return appetiteDetails;
	}


	public void setAppetiteDetails(String appetiteDetails) {
		this.appetiteDetails = appetiteDetails;
	}


	public Boolean getOnSpecialDiet() {
		return onSpecialDiet;
	}


	public void setOnSpecialDiet(Boolean onSpecialDiet) {
		this.onSpecialDiet = onSpecialDiet;
	}


	public String getSpecailDietDetails() {
		return specailDietDetails;
	}


	public void setSpecailDietDetails(String specailDietDetails) {
		this.specailDietDetails = specailDietDetails;
	}


	public Boolean getAllergies() {
		return allergies;
	}


	public void setAllergies(Boolean allergies) {
		this.allergies = allergies;
	}


	public String getAllergyType() {
		return allergyType;
	}


	public void setAllergyType(String allergyType) {
		this.allergyType = allergyType;
	}


	public String getAllergySymtoms() {
		return allergySymtoms;
	}


	public void setAllergySymtoms(String allergySymtoms) {
		this.allergySymtoms = allergySymtoms;
	}


	public Date getAllergyDate() {
		return allergyDate;
	}


	public void setAllergyDate(Date allergyDate) {
		this.allergyDate = allergyDate;
	}


	public String getAllergyDetails() {
		return allergyDetails;
	}


	public void setAllergyDetails(String allergyDetails) {
		this.allergyDetails = allergyDetails;
	}


	public String getAllergicToDrug() {
		return allergicToDrug;
	}


	public void setAllergicToDrug(String allergicToDrug) {
		this.allergicToDrug = allergicToDrug;
	}


	public Boolean getDiffFacedDuringDrugAdmin() {
		return diffFacedDuringDrugAdmin;
	}


	public void setDiffFacedDuringDrugAdmin(Boolean diffFacedDuringDrugAdmin) {
		this.diffFacedDuringDrugAdmin = diffFacedDuringDrugAdmin;
	}


	public String getDiffFacedDuringDrugAdminDetails() {
		return diffFacedDuringDrugAdminDetails;
	}


	public void setDiffFacedDuringDrugAdminDetails(String diffFacedDuringDrugAdminDetails) {
		this.diffFacedDuringDrugAdminDetails = diffFacedDuringDrugAdminDetails;
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

	/*public Volunteer getVolunteer() {
		return volunteer;
	}


	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}*/


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


	public String getChewerDetails() {
		return chewerDetails;
	}


	public void setChewerDetails(String chewerDetails) {
		this.chewerDetails = chewerDetails;
	}


	public String getAlcoholConsumerDetails() {
		return alcoholConsumerDetails;
	}


	public void setAlcoholConsumerDetails(String alcoholConsumerDetails) {
		this.alcoholConsumerDetails = alcoholConsumerDetails;
	}


	public String getDrugAbuserDetails() {
		return drugAbuserDetails;
	}


	public void setDrugAbuserDetails(String drugAbuserDetails) {
		this.drugAbuserDetails = drugAbuserDetails;
	}


	public String getStudySpecificAllergy() {
		return studySpecificAllergy;
	}


	public void setStudySpecificAllergy(String studySpecificAllergy) {
		this.studySpecificAllergy = studySpecificAllergy;
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
