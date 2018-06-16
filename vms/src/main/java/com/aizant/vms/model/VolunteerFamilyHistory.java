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
@Table(name="v_family_history")
public class VolunteerFamilyHistory extends Auditable<String>{

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(name="coronary_artery_disease")
	private String coronaryArteryDisease;
	
	@Column(name="coronary_artery_disease_relation_with_volunteer")
	private String coronaryArteryDiseaseVolunteer;
	
	@Column(name="coronary_artery_disease_comments")
	private String coronaryArteryDiseaseComments;
	
	@Column(name="hypertension")
	private String hypertension;
	@Column(name="hypertension_relation_with_volunteer")
	private String hypertensionRelationWithVolunteer;
	
	@Column(name="hypertension_comments")
	private String hypertensionComments;
	
	@Column(name="asthma")
	private String asthma;
	
	@Column(name="asthma_relation_with_volunteer")
	private String asthmaRelationWithVolunteer;
	
	@Column(name="asthma_comments")
	private String asthmaComments;
	
	@Column(name="malignancy")
	private String malignancy;
	
	@Column(name="malignancy_relation_with_volunteer")
	private String malignancyRelationWithVolunteer;
	
	@Column(name="malignancy_comments")
	private String malignancyComments;
	
	@Column(name="epilepsy")
	private String epilepsy;
	
	@Column(name="epilepsy_comments")
	private String epilepsyComments;
	
	@Column(name="epilepsy_relation_with_volunteer")
	private String epilepsyRelationWithVolunteer;
	
	@Column(name="renal_disorder")
	private String renalDisorder;
	
	@Column(name="renal_disorder_relation_with_volunteer")
	private String renalDisorderRelationWithVolunteer;
	
	@Column(name="renal_disorder_comments")
	private String renalDisorderComments;
	
	@Column(name="diabetes_mellitus")
	private String diabetesMellitus;
	
	@Column(name="diabetes_mellitus_relation_with_volunteer")
	private String diabetesMellitusRelationWithVolunteer;
	
	@Column(name="diabetes_mellitus_comments")
	private String diabetesMellitusComments;
	
	@Column(name="tuberculosis")
	private String tuberculosis;
	
	@Column(name="tuberculosis_relation_with_volunteer")
	private String tuberculosisRelationWithVolunteer;
	
	@Column(name="tuberculosis_comments")
	private String tuberculosisComments;
	
	@Column(name="neurological")
	private String neurological;
	
	@Column(name="neurological_relation_with_volunteer")
	private String neurologicalRelationWithVolunteer;
	
	@Column(name="neurological_comments")
	private String neurologicalComments;
	
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
	private Long updatedBy;
*/
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
	private String formNumber;
	@Transient
	private String sopNumber;
	
	
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

	public String getCoronaryArteryDisease() {
		return coronaryArteryDisease;
	}

	public void setCoronaryArteryDisease(String coronaryArteryDisease) {
		this.coronaryArteryDisease = coronaryArteryDisease;
	}

	public String getCoronaryArteryDiseaseVolunteer() {
		return coronaryArteryDiseaseVolunteer;
	}

	public void setCoronaryArteryDiseaseVolunteer(String coronaryArteryDiseaseVolunteer) {
		this.coronaryArteryDiseaseVolunteer = coronaryArteryDiseaseVolunteer;
	}

	public String getCoronaryArteryDiseaseComments() {
		return coronaryArteryDiseaseComments;
	}

	public void setCoronaryArteryDiseaseComments(String coronaryArteryDiseaseComments) {
		this.coronaryArteryDiseaseComments = coronaryArteryDiseaseComments;
	}

	public String getHypertension() {
		return hypertension;
	}

	public void setHypertension(String hypertension) {
		this.hypertension = hypertension;
	}

	public String getHypertensionRelationWithVolunteer() {
		return hypertensionRelationWithVolunteer;
	}

	public void setHypertensionRelationWithVolunteer(String hypertensionRelationWithVolunteer) {
		this.hypertensionRelationWithVolunteer = hypertensionRelationWithVolunteer;
	}

	public String getHypertensionComments() {
		return hypertensionComments;
	}

	public void setHypertensionComments(String hypertensionComments) {
		this.hypertensionComments = hypertensionComments;
	}

	public String getAsthma() {
		return asthma;
	}

	public void setAsthma(String asthma) {
		this.asthma = asthma;
	}

	public String getAsthmaRelationWithVolunteer() {
		return asthmaRelationWithVolunteer;
	}

	public void setAsthmaRelationWithVolunteer(String asthmaRelationWithVolunteer) {
		this.asthmaRelationWithVolunteer = asthmaRelationWithVolunteer;
	}

	public String getAsthmaComments() {
		return asthmaComments;
	}

	public void setAsthmaComments(String asthmaComments) {
		this.asthmaComments = asthmaComments;
	}

	public String getMalignancy() {
		return malignancy;
	}

	public void setMalignancy(String malignancy) {
		this.malignancy = malignancy;
	}

	public String getMalignancyRelationWithVolunteer() {
		return malignancyRelationWithVolunteer;
	}

	public void setMalignancyRelationWithVolunteer(String malignancyRelationWithVolunteer) {
		this.malignancyRelationWithVolunteer = malignancyRelationWithVolunteer;
	}

	public String getMalignancyComments() {
		return malignancyComments;
	}

	public void setMalignancyComments(String malignancyComments) {
		this.malignancyComments = malignancyComments;
	}

	public String getEpilepsy() {
		return epilepsy;
	}

	public void setEpilepsy(String epilepsy) {
		this.epilepsy = epilepsy;
	}

	public String getEpilepsyComments() {
		return epilepsyComments;
	}

	public void setEpilepsyComments(String epilepsyComments) {
		this.epilepsyComments = epilepsyComments;
	}

	public String getEpilepsyRelationWithVolunteer() {
		return epilepsyRelationWithVolunteer;
	}

	public void setEpilepsyRelationWithVolunteer(String epilepsyRelationWithVolunteer) {
		this.epilepsyRelationWithVolunteer = epilepsyRelationWithVolunteer;
	}

	public String getRenalDisorder() {
		return renalDisorder;
	}

	public void setRenalDisorder(String renalDisorder) {
		this.renalDisorder = renalDisorder;
	}

	public String getRenalDisorderRelationWithVolunteer() {
		return renalDisorderRelationWithVolunteer;
	}

	public void setRenalDisorderRelationWithVolunteer(String renalDisorderRelationWithVolunteer) {
		this.renalDisorderRelationWithVolunteer = renalDisorderRelationWithVolunteer;
	}

	public String getRenalDisorderComments() {
		return renalDisorderComments;
	}

	public void setRenalDisorderComments(String renalDisorderComments) {
		this.renalDisorderComments = renalDisorderComments;
	}

	public String getDiabetesMellitus() {
		return diabetesMellitus;
	}

	public void setDiabetesMellitus(String diabetesMellitus) {
		this.diabetesMellitus = diabetesMellitus;
	}

	public String getDiabetesMellitusRelationWithVolunteer() {
		return diabetesMellitusRelationWithVolunteer;
	}

	public void setDiabetesMellitusRelationWithVolunteer(String diabetesMellitusRelationWithVolunteer) {
		this.diabetesMellitusRelationWithVolunteer = diabetesMellitusRelationWithVolunteer;
	}

	public String getDiabetesMellitusComments() {
		return diabetesMellitusComments;
	}

	public void setDiabetesMellitusComments(String diabetesMellitusComments) {
		this.diabetesMellitusComments = diabetesMellitusComments;
	}

	public String getTuberculosis() {
		return tuberculosis;
	}

	public void setTuberculosis(String tuberculosis) {
		this.tuberculosis = tuberculosis;
	}

	public String getTuberculosisRelationWithVolunteer() {
		return tuberculosisRelationWithVolunteer;
	}

	public void setTuberculosisRelationWithVolunteer(String tuberculosisRelationWithVolunteer) {
		this.tuberculosisRelationWithVolunteer = tuberculosisRelationWithVolunteer;
	}

	public String getTuberculosisComments() {
		return tuberculosisComments;
	}

	public void setTuberculosisComments(String tuberculosisComments) {
		this.tuberculosisComments = tuberculosisComments;
	}

	public String getNeurological() {
		return neurological;
	}

	public void setNeurological(String neurological) {
		this.neurological = neurological;
	}

	public String getNeurologicalRelationWithVolunteer() {
		return neurologicalRelationWithVolunteer;
	}

	public void setNeurologicalRelationWithVolunteer(String neurologicalRelationWithVolunteer) {
		this.neurologicalRelationWithVolunteer = neurologicalRelationWithVolunteer;
	}

	public String getNeurologicalComments() {
		return neurologicalComments;
	}

	public void setNeurologicalComments(String neurologicalComments) {
		this.neurologicalComments = neurologicalComments;
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

	/*public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}*/

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
