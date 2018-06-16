package com.aizant.vms.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AllMsrPdf {
	

	@Transient
	private String initials;

	@Transient
	private Boolean screeningICF;

	@Transient
	private Date screeningicfDate;

	@Transient
	private String langRead;

	@Transient
	private List<String> langReadList;

	@Transient
	private String langWrite;

	@Transient
	private List<String> langWriteList;

	@Transient
	private String gender;

	@Transient
	private Float height;

	@Transient
	private Float weight;

	@Transient
	private String dietStatus;

	@Transient
	private List<String> dietList;

	@Transient
	private String dmRace;

	@Transient
	private String dmBmi;

	@Transient
	private Date historyOfBloodDonationDate;

	@Transient
	private Date lastParticipationDate;

	@Transient
	private String dmrecordedBy;
	
	@Transient
	private Boolean proceedForScreening;

	@Transient
	private Long noOfDaysCompletedFromLastPartcipation;
	
	@Transient
	private String volunteerRegNum;
	
	@Transient
	private String formNumber;
	
	@Transient
	private String sopNumber;


	
	// Restriction Compliance
	
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

	public String getVolunteerRegNum() {
		return volunteerRegNum;
	}

	public void setVolunteerRegNum(String volunteerRegNum) {
		this.volunteerRegNum = volunteerRegNum;
	}

	@Transient
	private String rcvolunteerIntials;

	@Transient
	private String volunteerRead;

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
	private Boolean bevarageConsumption;
	
	@Transient
	private Boolean alcoholConsumption;
	
	@Transient
	private Boolean tobaccoConsumption;
	
	@Transient
	private Boolean complyWithCompliance;
	
	@Transient
	private Boolean instructedNotToConsume;
	
	@Transient
	private String rcrecordedBy;
	
	@Transient
	private String volunteerIntials;
	
	
	
	// Vital Signs
	
	@Transient
	private String medicalExamStartTime;
	
	@Transient
	private Date medicalExamStartDate;
	
	@Transient
	private String medicalStartTime;
	
	@Transient
	private String radialPulseRate;
	
	@Transient
	private String sittingBloodPressure;
		
	@Transient
	private String bodyTemparature;
	
	@Transient
	private String respiratoryRate;
	
	@Transient
	private String vsrecordedBy;
	
	
	
	// Personal History
	
	@Transient
	private String maritalStatus;
	
	@Transient
	private Integer childrenCount;
		
	@Transient
	private String smokerDetails;
	
	@Transient
	private Double noOfCigarPerDay;
	
	@Transient
	private String smokeStoppedDuration;
	
	@Transient
	private String dateOfStopping;
	
	@Transient
	private Boolean exTobaccoChewer;
	
	@Transient
	private String chewerDetails;
	
	@Transient
	private String tobaccoType;
	
	@Transient
	private String tobaccoStoppedDuration;
	
	@Transient
	private String tobaccoStoppedDate;
	
	@Transient
	private String alcoholConsumerDetails;
	
	@Transient
	private String alcoholConsumerType;
	
	@Transient
	private Integer noOfUnitsPerDay;
	
	@Transient
	private String typeOfDrink;
	
	@Transient
	private String dateOfLastConsumption;
	
	@Transient
	private String drugAbuserDetails;
	
	@Transient
	private String substaanceOfAbuse;
	
	@Transient
	private String drugDuration;
	
	@Transient
	private String drugStoppedDate;
	
	@Transient
	private String appetite;
	
	@Transient
	private String appetiteDetails;
	
	@Transient
	private Boolean onSpecialDiet;
	
	@Transient
	private String specailDietDetails;
	
	@Transient
	private Boolean allergies;
	
	@Transient
	private String allergyType;
	
	@Transient
	private String allergySymtoms;
	
	@Transient
	private Date allergyDate;
	
	@Transient
	private String allergyDetails;
	
	@Transient
	private String studySpecificAllergy;
	
	@Transient
	private String allergicToDrug;
	
	@Transient
	private Boolean diffFacedDuringDrugAdmin;
	
	@Transient
	private String diffFacedDuringDrugAdminDetails;
	
	@Transient
	private String phrecordedBy;

	@Transient
	private Timestamp phrecordedOn;

	
	
	// Past History
	
	@Transient
	private Boolean medicalHistory;
	
	@Transient
	private String typeOfIllness;
	
	@Transient
	private String dateOfDetection;
	
	@Transient
	private String hospitalized;
	
	@Transient
	private String treatmentTaken;
	
	@Transient
	private Boolean surgicalHistory;
	
	@Transient
	private String surgeryName;
	
	@Transient
	private String surgeryReason;
	
	@Transient
	private String surgeryDate;
	
	@Transient
	private Boolean familyPlanning;
	
	@Transient
	private Boolean familyPlanningSelf;
	
	@Transient
	private Boolean familyPlanningSpouse;
	
	@Transient
	private String familyPlannningSelfDate;
	
	@Transient
	private String familyPlanningSouseDate;
	
	@Transient
	private String familyPlanningSelfComments;
	
	@Transient
	private String familyPlanningSpouseComments;
	
	@Transient
	private Boolean treatmentHistory;
	
	@Transient
	private String treatmentDetails;
	
	@Transient
	private String ph1recordedBy;

	
	
	
	
	// Family History
	
	@Transient
	private String coronaryArteryDisease;
	
	@Transient
	private String coronaryArteryDiseaseVolunteer;
	
	@Transient
	private String coronaryArteryDiseaseComments;
	
	@Transient
	private String hypertension;
	
	@Transient
	private String hypertensionRelationWithVolunteer;
	
	@Transient
	private String hypertensionComments;
	
	@Transient
	private String asthma;
	
	@Transient
	private String asthmaRelationWithVolunteer;
	
	@Transient
	private String asthmaComments;
	
	@Transient
	private String malignancy;
	
	@Transient
	private String malignancyRelationWithVolunteer;
	
	@Transient
	private String malignancyComments;
	
	@Transient
	private String epilepsy;
	
	@Transient
	private String epilepsyComments;
	
	@Transient
	private String epilepsyRelationWithVolunteer;
	
	@Transient
	private String renalDisorder;
	
	@Transient
	private String renalDisorderRelationWithVolunteer;
	
	@Transient
	private String renalDisorderComments;
	
	@Transient
	private String diabetesMellitus;
	
	@Transient
	private String diabetesMellitusRelationWithVolunteer;
	
	@Transient
	private String diabetesMellitusComments;
	
	@Transient
	private String tuberculosis;
	
	@Transient
	private String tuberculosisRelationWithVolunteer;
	
	@Transient
	private String tuberculosisComments;
	
	@Transient
	private String neurological;
	
	@Transient
	private String neurologicalRelationWithVolunteer;
	
	@Transient
	private String neurologicalComments;
	
	@Transient
	private String fhrecordedBy;

	
	// Menstrual History
	
	
	@Transient
	private String lmpDetails;

	@Transient
	private String lmpComments;

	@Transient
	private String durtionOfFlowDetails;

	@Transient
	private String durtionOfFlowComments;

	@Transient
	private String regularDetails;

	@Transient
	private String regularComments;
	
	@Transient
	private String lengthOfCycleDetails;
	
	@Transient
	private String lengthOfCycleComments;

	@Transient
	private String hoDysmenorrhoeaDetails;

	@Transient
	private String hoDysmenorrhoeaComments;

	@Transient
	private String hoAmenorrhoeaDetails;
	
	@Transient
	private String hoAmenorrhoeaComments;
	
	@Transient
	private String hoLeucorrhoeaDetails;
	
	@Transient
	private String hoLeucorrhoeaComments;

	@Transient
	private String obestricAge;

	@Transient
	private String obestricMethodOfDelivery;

	@Transient
	private String obestricGender;

	@Transient
	private String obestricHealthStatus;

	@Transient
	private String obestricComments;
	
	@Transient
	private String abortionHistoryMonthYear;
	
	@Transient
	private String abortionCorrespondingAge;

	@Transient
	private Boolean breastFeedingCurrently;

	@Transient
	private Boolean hormonalTreatment;

	@Transient
	private String hormonalTreatmentComments;

	@Transient
	private String gynaecologicalExam;

	@Transient
	private String gynaecologicalExamComments;
	
	@Transient
	private String mhrecordedBy;

		

	// General Examination
	
	@Transient
	private String genAppStatus;
	
	@Transient
	private String genAppComments;
	
	@Transient
	private String headStatus;
	
	@Transient
	private String headComments;
	
	@Transient
	private String eyesStatus;
	
	@Transient
	private String eyesComments;
	
	@Transient
	private String earsStatus;
	
	@Transient
	private String earsComments;
	
	@Transient
	private String noseStatus;
	
	@Transient
	private String noseComments;
	
	@Transient
	private String oropharynxStatus;
	
	@Transient
	private String oropharynxComments;
	
	@Transient
	private String neckStatus;
	
	@Transient
	private String neckComments;
	
	@Transient
	private String musculoskeletalStatus;
	
	@Transient
	private String musculoskeletalComments;
	
	@Transient
	private String extremitiesStatus;
	
	@Transient
	private String extremitiesComments;
	
	@Transient
	private String skinStatus;
	
	@Transient
	private String skinComments;
	
	@Transient
	private String overAllComments;
	
	@Transient
	private String gerecordedBy;

	
	
	// Systemic Examination
	
	@Transient
	private String cardiovascularSystemStatus;
	
	@Transient
	private String cardiovascularSystemComments;
	
	@Transient
	private String respiratorySystemStatus;
	
	@Transient
	private String respiratorySystemComments;
	
	@Transient
	private String abdomenStatus;
	
	@Transient
	private String abdomenComments;
	
	@Transient
	private String centralNervousSystemStatus;
	
	@Transient
	private String centralNervousSystemComments;
	
	@Transient
	private String overallComments;
	
	@Transient
	private String commentsOnPhysicalExamination;
	
	@Transient
	private Date examinationEndDate;
	
	@Transient
	private String examinationEndTime;
	
	@Transient
	private String serecordedBy;


	
	
	// Clinical Assessment
	
	@Transient
	private String hematologyStatus;
	
	@Transient
	private String hematologyComments;
	
	@Transient
	private String biochemistryStatus;
	
	@Transient
	private String biochemistryComments;
	
	@Transient
	private String serologyStatus;
	
	@Transient
	private String serologyComments;
	
	@Transient
	private String urineAnalysisStatus;
	
	@Transient
	private String urineAnalysisComments;
	
	@Transient
	private String ecgStatus;
	
	@Transient
	private String ecgComments;
	
	@Transient
	private String chestXRayStatus;
	
	@Transient
	private String chestXRayComments;
	
	@Transient
	private String othersStatus;
	
	@Transient
	private String othersComments;
	
	@Transient
	private String bmiDetails;
	
	@Transient
	private String bmiComments;
	
	@Transient
	private String additionalComments;
	
	@Transient
	private Boolean volunteerHealthy;
	
	@Transient
	private String carecordedBy;
	
	@Transient
	private String careviewedBy;

	@Transient
	private Boolean isRejectedPermanantly;
	
	@Transient
	private Long rejectedNoOfDays;
	
	@Transient
	private String reasonForRejection;
	
	@Transient
	private Boolean temporaryorpermanent;
	
	@Transient
	private Long noofdays;
	
	@Transient
	private String checkedBy;
	


	

	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}

	public Boolean getTemporaryorpermanent() {
		return temporaryorpermanent;
	}

	public void setTemporaryorpermanent(Boolean temporaryorpermanent) {
		this.temporaryorpermanent = temporaryorpermanent;
	}

	public Long getNoofdays() {
		return noofdays;
	}

	public void setNoofdays(Long noofdays) {
		this.noofdays = noofdays;
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

	public String getLangRead() {
		return langRead;
	}

	public void setLangRead(String langRead) {
		this.langRead = langRead;
	}

	public List<String> getLangReadList() {
		return langReadList;
	}

	public void setLangReadList(List<String> langReadList) {
		this.langReadList = langReadList;
	}

	public String getLangWrite() {
		return langWrite;
	}

	public void setLangWrite(String langWrite) {
		this.langWrite = langWrite;
	}

	public List<String> getLangWriteList() {
		return langWriteList;
	}

	public void setLangWriteList(List<String> langWriteList) {
		this.langWriteList = langWriteList;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public List<String> getDietList() {
		return dietList;
	}

	public void setDietList(List<String> dietList) {
		this.dietList = dietList;
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

	public String getDmrecordedBy() {
		return dmrecordedBy;
	}

	public void setDmrecordedBy(String dmrecordedBy) {
		this.dmrecordedBy = dmrecordedBy;
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

	public String getRcvolunteerIntials() {
		return rcvolunteerIntials;
	}

	public void setRcvolunteerIntials(String rcvolunteerIntials) {
		this.rcvolunteerIntials = rcvolunteerIntials;
	}

	public String getVolunteerRead() {
		return volunteerRead;
	}

	public void setVolunteerRead(String volunteerRead) {
		this.volunteerRead = volunteerRead;
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

	public String getRcrecordedBy() {
		return rcrecordedBy;
	}

	public void setRcrecordedBy(String rcrecordedBy) {
		this.rcrecordedBy = rcrecordedBy;
	}

	public String getVolunteerIntials() {
		return volunteerIntials;
	}

	public void setVolunteerIntials(String volunteerIntials) {
		this.volunteerIntials = volunteerIntials;
	}

	public String getMedicalExamStartTime() {
		return medicalExamStartTime;
	}

	public void setMedicalExamStartTime(String medicalExamStartTime) {
		this.medicalExamStartTime = medicalExamStartTime;
	}

	public Date getMedicalExamStartDate() {
		return medicalExamStartDate;
	}

	public void setMedicalExamStartDate(Date medicalExamStartDate) {
		this.medicalExamStartDate = medicalExamStartDate;
	}

	public String getMedicalStartTime() {
		return medicalStartTime;
	}

	public void setMedicalStartTime(String medicalStartTime) {
		this.medicalStartTime = medicalStartTime;
	}

	public String getRadialPulseRate() {
		return radialPulseRate;
	}

	public void setRadialPulseRate(String radialPulseRate) {
		this.radialPulseRate = radialPulseRate;
	}

	public String getSittingBloodPressure() {
		return sittingBloodPressure;
	}

	public void setSittingBloodPressure(String sittingBloodPressure) {
		this.sittingBloodPressure = sittingBloodPressure;
	}

	public String getBodyTemparature() {
		return bodyTemparature;
	}

	public void setBodyTemparature(String bodyTemparature) {
		this.bodyTemparature = bodyTemparature;
	}

	public String getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(String respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public String getVsrecordedBy() {
		return vsrecordedBy;
	}

	public void setVsrecordedBy(String vsrecordedBy) {
		this.vsrecordedBy = vsrecordedBy;
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

	public Boolean getExTobaccoChewer() {
		return exTobaccoChewer;
	}

	public void setExTobaccoChewer(Boolean exTobaccoChewer) {
		this.exTobaccoChewer = exTobaccoChewer;
	}

	public String getChewerDetails() {
		return chewerDetails;
	}

	public void setChewerDetails(String chewerDetails) {
		this.chewerDetails = chewerDetails;
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

	public String getAlcoholConsumerDetails() {
		return alcoholConsumerDetails;
	}

	public void setAlcoholConsumerDetails(String alcoholConsumerDetails) {
		this.alcoholConsumerDetails = alcoholConsumerDetails;
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

	public String getDrugAbuserDetails() {
		return drugAbuserDetails;
	}

	public void setDrugAbuserDetails(String drugAbuserDetails) {
		this.drugAbuserDetails = drugAbuserDetails;
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

	public String getStudySpecificAllergy() {
		return studySpecificAllergy;
	}

	public void setStudySpecificAllergy(String studySpecificAllergy) {
		this.studySpecificAllergy = studySpecificAllergy;
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

	public String getPhrecordedBy() {
		return phrecordedBy;
	}

	public void setPhrecordedBy(String phrecordedBy) {
		this.phrecordedBy = phrecordedBy;
	}

	public Timestamp getPhrecordedOn() {
		return phrecordedOn;
	}

	public void setPhrecordedOn(Timestamp phrecordedOn) {
		this.phrecordedOn = phrecordedOn;
	}

	public Boolean getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(Boolean medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getTypeOfIllness() {
		return typeOfIllness;
	}

	public void setTypeOfIllness(String typeOfIllness) {
		this.typeOfIllness = typeOfIllness;
	}

	public String getDateOfDetection() {
		return dateOfDetection;
	}

	public void setDateOfDetection(String dateOfDetection) {
		this.dateOfDetection = dateOfDetection;
	}

	public String getHospitalized() {
		return hospitalized;
	}

	public void setHospitalized(String hospitalized) {
		this.hospitalized = hospitalized;
	}

	public String getTreatmentTaken() {
		return treatmentTaken;
	}

	public void setTreatmentTaken(String treatmentTaken) {
		this.treatmentTaken = treatmentTaken;
	}

	public Boolean getSurgicalHistory() {
		return surgicalHistory;
	}

	public void setSurgicalHistory(Boolean surgicalHistory) {
		this.surgicalHistory = surgicalHistory;
	}

	public String getSurgeryName() {
		return surgeryName;
	}

	public void setSurgeryName(String surgeryName) {
		this.surgeryName = surgeryName;
	}

	public String getSurgeryReason() {
		return surgeryReason;
	}

	public void setSurgeryReason(String surgeryReason) {
		this.surgeryReason = surgeryReason;
	}

	public String getSurgeryDate() {
		return surgeryDate;
	}

	public void setSurgeryDate(String surgeryDate) {
		this.surgeryDate = surgeryDate;
	}

	public Boolean getFamilyPlanning() {
		return familyPlanning;
	}

	public void setFamilyPlanning(Boolean familyPlanning) {
		this.familyPlanning = familyPlanning;
	}

	public Boolean getFamilyPlanningSelf() {
		return familyPlanningSelf;
	}

	public void setFamilyPlanningSelf(Boolean familyPlanningSelf) {
		this.familyPlanningSelf = familyPlanningSelf;
	}

	public Boolean getFamilyPlanningSpouse() {
		return familyPlanningSpouse;
	}

	public void setFamilyPlanningSpouse(Boolean familyPlanningSpouse) {
		this.familyPlanningSpouse = familyPlanningSpouse;
	}

	public String getFamilyPlannningSelfDate() {
		return familyPlannningSelfDate;
	}

	public void setFamilyPlannningSelfDate(String familyPlannningSelfDate) {
		this.familyPlannningSelfDate = familyPlannningSelfDate;
	}

	public String getFamilyPlanningSouseDate() {
		return familyPlanningSouseDate;
	}

	public void setFamilyPlanningSouseDate(String familyPlanningSouseDate) {
		this.familyPlanningSouseDate = familyPlanningSouseDate;
	}

	public String getFamilyPlanningSelfComments() {
		return familyPlanningSelfComments;
	}

	public void setFamilyPlanningSelfComments(String familyPlanningSelfComments) {
		this.familyPlanningSelfComments = familyPlanningSelfComments;
	}

	public String getFamilyPlanningSpouseComments() {
		return familyPlanningSpouseComments;
	}

	public void setFamilyPlanningSpouseComments(String familyPlanningSpouseComments) {
		this.familyPlanningSpouseComments = familyPlanningSpouseComments;
	}

	public Boolean getTreatmentHistory() {
		return treatmentHistory;
	}

	public void setTreatmentHistory(Boolean treatmentHistory) {
		this.treatmentHistory = treatmentHistory;
	}

	public String getTreatmentDetails() {
		return treatmentDetails;
	}

	public void setTreatmentDetails(String treatmentDetails) {
		this.treatmentDetails = treatmentDetails;
	}

	public String getPh1recordedBy() {
		return ph1recordedBy;
	}

	public void setPh1recordedBy(String ph1recordedBy) {
		this.ph1recordedBy = ph1recordedBy;
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

	public String getFhrecordedBy() {
		return fhrecordedBy;
	}

	public void setFhrecordedBy(String fhrecordedBy) {
		this.fhrecordedBy = fhrecordedBy;
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

	public String getObestricComments() {
		return obestricComments;
	}

	public void setObestricComments(String obestricComments) {
		this.obestricComments = obestricComments;
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

	public String getMhrecordedBy() {
		return mhrecordedBy;
	}

	public void setMhrecordedBy(String mhrecordedBy) {
		this.mhrecordedBy = mhrecordedBy;
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

	public String getGerecordedBy() {
		return gerecordedBy;
	}

	public void setGerecordedBy(String gerecordedBy) {
		this.gerecordedBy = gerecordedBy;
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

	public String getSerecordedBy() {
		return serecordedBy;
	}

	public void setSerecordedBy(String serecordedBy) {
		this.serecordedBy = serecordedBy;
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

	public String getCarecordedBy() {
		return carecordedBy;
	}

	public void setCarecordedBy(String carecordedBy) {
		this.carecordedBy = carecordedBy;
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
	
	
	
	// ECG
	
	
	@Transient
	private Date ecgDate;

	@Transient
	private String ecgTime;
	
	@Transient
	private String ecgFileLocation;
	
	@Transient
	private String reportConfirmedBy;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Kolkata")
	@Transient
	private Timestamp reportConfirmedTime;
	
	@Transient
	private String ecgHr;

	@Transient
	private String ecgP;

	@Transient
	private String ecgPR;

	@Transient
	private String ecgQrs;

	@Transient
	private String ecgQT;

	@Transient
	private String ecgPqrs;

	@Transient
	private String ecgRv5;

	@Transient
	private String recordedBy;

	@Transient
	private Timestamp recordedOn;

	@Transient
	private String ecgtestDate;

	@Transient
	private String reviewedBy;

	@Transient
	private Timestamp reviewedOn;

	@Transient
	private String reviewedOnDate;

	@Transient
	private String recordedOnDate;
	
	@Transient
	private String regNum;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@Transient
	private StudyCenter center;

	@Transient
	private String ecgvolunteerAge;
	
	@Transient
	private String volunteerSex;
	
	@Transient
	private String updateComments;
	
	
	// X-Ray
	
	
	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public StudyCenter getCenter() {
		return center;
	}

	public void setCenter(StudyCenter center) {
		this.center = center;
	}

	public String getEcgvolunteerAge() {
		return ecgvolunteerAge;
	}

	public void setEcgvolunteerAge(String ecgvolunteerAge) {
		this.ecgvolunteerAge = ecgvolunteerAge;
	}

	public String getVolunteerSex() {
		return volunteerSex;
	}

	public void setVolunteerSex(String volunteerSex) {
		this.volunteerSex = volunteerSex;
	}

	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}

	@Transient
	private String softTissue;
	
	@Transient
	private String softTissueComment;
	
	@Transient
	private String heartSize;
	
	@Transient
	private String heartSizeComment;
	
	@Transient
	private String lungFields;
	
	@Transient
	private String lungFieldsComment;
	
	@Transient
	private String diaphragmDemos;
	
	@Transient
	private String diaphragmDemosComment;
	
	@Transient
	private String cpAngles;
	
	@Transient
	private String cpAnglesComment;
	
	@Transient
	private String impression;
	
	@Transient
	private String impressionComment;
	
	@Transient
	private String physicianBy;
	
	@Transient
	private Timestamp physicianDate;
	
	@Transient
	private String comments;
	
	@Transient
	private String xrayStatus;
	
	@Transient
	private String radiologistBy;
	
	@Transient
	private Timestamp radiologistDate;
	
	@Transient
	private Date xrayDate;
	
	@Transient
	private String xrayTime;

	@Transient
	private String xRayFileLocation;
	
	@Transient
	private String xRaytestDate;



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

	public Timestamp getReportConfirmedTime() {
		return reportConfirmedTime;
	}

	public void setReportConfirmedTime(Timestamp reportConfirmedTime) {
		this.reportConfirmedTime = reportConfirmedTime;
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

	public String getEcgtestDate() {
		return ecgtestDate;
	}

	public void setEcgtestDate(String ecgtestDate) {
		this.ecgtestDate = ecgtestDate;
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

	public String getSoftTissue() {
		return softTissue;
	}

	public void setSoftTissue(String softTissue) {
		this.softTissue = softTissue;
	}

	public String getSoftTissueComment() {
		return softTissueComment;
	}

	public void setSoftTissueComment(String softTissueComment) {
		this.softTissueComment = softTissueComment;
	}

	public String getHeartSize() {
		return heartSize;
	}

	public void setHeartSize(String heartSize) {
		this.heartSize = heartSize;
	}

	public String getHeartSizeComment() {
		return heartSizeComment;
	}

	public void setHeartSizeComment(String heartSizeComment) {
		this.heartSizeComment = heartSizeComment;
	}

	public String getLungFields() {
		return lungFields;
	}

	public void setLungFields(String lungFields) {
		this.lungFields = lungFields;
	}

	public String getLungFieldsComment() {
		return lungFieldsComment;
	}

	public void setLungFieldsComment(String lungFieldsComment) {
		this.lungFieldsComment = lungFieldsComment;
	}

	public String getDiaphragmDemos() {
		return diaphragmDemos;
	}

	public void setDiaphragmDemos(String diaphragmDemos) {
		this.diaphragmDemos = diaphragmDemos;
	}

	public String getDiaphragmDemosComment() {
		return diaphragmDemosComment;
	}

	public void setDiaphragmDemosComment(String diaphragmDemosComment) {
		this.diaphragmDemosComment = diaphragmDemosComment;
	}

	public String getCpAngles() {
		return cpAngles;
	}

	public void setCpAngles(String cpAngles) {
		this.cpAngles = cpAngles;
	}

	public String getCpAnglesComment() {
		return cpAnglesComment;
	}

	public void setCpAnglesComment(String cpAnglesComment) {
		this.cpAnglesComment = cpAnglesComment;
	}

	public String getImpression() {
		return impression;
	}

	public void setImpression(String impression) {
		this.impression = impression;
	}

	public String getImpressionComment() {
		return impressionComment;
	}

	public void setImpressionComment(String impressionComment) {
		this.impressionComment = impressionComment;
	}

	public String getPhysicianBy() {
		return physicianBy;
	}

	public void setPhysicianBy(String physicianBy) {
		this.physicianBy = physicianBy;
	}

	public Timestamp getPhysicianDate() {
		return physicianDate;
	}

	public void setPhysicianDate(Timestamp physicianDate) {
		this.physicianDate = physicianDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getXrayStatus() {
		return xrayStatus;
	}

	public void setXrayStatus(String xrayStatus) {
		this.xrayStatus = xrayStatus;
	}

	public String getRadiologistBy() {
		return radiologistBy;
	}

	public void setRadiologistBy(String radiologistBy) {
		this.radiologistBy = radiologistBy;
	}

	public Timestamp getRadiologistDate() {
		return radiologistDate;
	}

	public void setRadiologistDate(Timestamp radiologistDate) {
		this.radiologistDate = radiologistDate;
	}

	public Date getXrayDate() {
		return xrayDate;
	}

	public void setXrayDate(Date xrayDate) {
		this.xrayDate = xrayDate;
	}

	public String getXrayTime() {
		return xrayTime;
	}

	public void setXrayTime(String xrayTime) {
		this.xrayTime = xrayTime;
	}

	public String getxRayFileLocation() {
		return xRayFileLocation;
	}

	public void setxRayFileLocation(String xRayFileLocation) {
		this.xRayFileLocation = xRayFileLocation;
	}

	public String getxRaytestDate() {
		return xRaytestDate;
	}

	public void setxRaytestDate(String xRaytestDate) {
		this.xRaytestDate = xRaytestDate;
	}

	public String getCareviewedBy() {
		return careviewedBy;
	}

	public void setCareviewedBy(String careviewedBy) {
		this.careviewedBy = careviewedBy;
	}
	
	
}