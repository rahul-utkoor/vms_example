package com.aizant.vms.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "volunteer")
public class Volunteer extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column
	private String address;
	
	@Column(name = "height")
	private String height;

	@Column(name = "weight")
	private String weight;
	
	@Column(name="race")
	private String race;
	
	@Transient
	private String bmi;

	@Column
	private String diet;

	@Transient
	private String age;

	@Column
	private Date dob;

	@Column
	private String intials;

	@Column(name = "first_name")
	private String firstName;

	@Column
	private String gender;

	@Column(name = "father_name")
	private String fatherName;

	@Column
	private String qualification;

	@Column(name = "id_mark_1")
	private String idMark1;

	@Column(name = "id_mark_2")
	private String idMark2;

	@Column
	private String image;

	@Column(name = "lang_speak")
	private String langSpeak;

	@Column(name = "lang_write")
	private String langWrite;
	
	@Transient
	private Date nextEligibleDate;

	

	public Date getNextEligibleDate() {
		return nextEligibleDate;
	}

	public void setNextEligibleDate(Date nextEligibleDate) {
		this.nextEligibleDate = nextEligibleDate;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	@Column(name = "lang_read")
	private String langRead;

	@Column(name = "last_name")
	private String lastName;

	@Column
	private String phone;

	@Column
	private String nationality;

	@Column
	private String occupation;

	@Column
	private String signature;

	@Column(name = "thumb")
	private Blob thumb;

	@Column(name = "e_name")
	private String eName;

	@Column(name = "e_address")
	private String eAddress;

	@Column(name = "e_phone")
	private String ePhone;

	@Column(name = "relation_vol")
	private String relationVol;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	@Column(name="comments")
	private String comments;

	@Column(name = "registered_on")
	private Timestamp registeredOn;

	@Column(name = "last_participation_date")
	private Date lastParticipationDate;
	
	@Column(name="no_of_days_completed")
	private Long noOfDaysCompletedFromLastPartcipation;
	
	@Transient
	private Boolean proceedForScreening;

	@Column(name = "last_screening_date")
	private Date lastScreeningDate;

	@Column(name = "v_status")
	private String vStatus;

	@Column(name = "registration_number")
	private String registrationNumber;

	@Column(name = "registered_by")
	private String registeredBy;
	
	
	/*@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;*/

	@Column(name = "center_id")
	private Long centerId;

	@Column(name = "image_location")
	private String imageLocation;

	@Transient
	private String imageName;
	
	@Column(name="image_id")
	private Long imageId;
	
	@Column(name="finger_id")
	private Long fingerId;
	
	@Column(name="approvalStatus")
	private Long approvalStatus;
	
	@Column(name="user_status_date")
	private Timestamp userStatusDate;
	
	@Transient
	private Boolean isRejectedPermanantly;
	
	@Transient
	private Long rejectedNoOfDays;
	
	@Transient
	private String reasonForRejection;
	
	
	@Transient
	private String updateComments;
	
	@Column(name="current_status")
	private Long currentStatus;
	
	@Transient
	private String statusValue;
	
	@Transient
	private Long statusId;
	
	@Transient
    private String formNumber;
    @Transient
    private String sopNumber;
    @Transient
    private String idCardIssuedBy;
    @Transient
    private Date idCardIssuedDate;
    @Transient
    private Date screeningActivityDate;
    @Transient
    public VolunteerStatus volunteerStatus;
    @Transient
    public String latestStudyNumber;
  
	public Long getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Long currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getIntials() {
		return intials;
	}

	public void setIntials(String intials) {
		this.intials = intials;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getIdMark1() {
		return idMark1;
	}

	public void setIdMark1(String idMark1) {
		this.idMark1 = idMark1;
	}

	public String getIdMark2() {
		return idMark2;
	}

	public void setIdMark2(String idMark2) {
		this.idMark2 = idMark2;
	}

	public String getImage() {
		return image;
	}

	public VolunteerStatus getVolunteerStatus() {
		return volunteerStatus;
	}

	public void setVolunteerStatus(VolunteerStatus volunteerStatus) {
		this.volunteerStatus = volunteerStatus;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLangSpeak() {
		return langSpeak;
	}

	public void setLangSpeak(String langSpeak) {
		this.langSpeak = langSpeak;
	}

	public String getLangWrite() {
		return langWrite;
	}

	public void setLangWrite(String langWrite) {
		this.langWrite = langWrite;
	}

	public String getLangRead() {
		return langRead;
	}


	public void setLangRead(String langRead) {
		this.langRead = langRead;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Blob getThumb() {
		return thumb;
	}

	public void setThumb(Blob thumb) {
		this.thumb = thumb;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String geteAddress() {
		return eAddress;
	}

	public void seteAddress(String eAddress) {
		this.eAddress = eAddress;
	}

	public String getePhone() {
		return ePhone;
	}

	public void setePhone(String ePhone) {
		this.ePhone = ePhone;
	}

	public String getRelationVol() {
		return relationVol;
	}

	public void setRelationVol(String relationVol) {
		this.relationVol = relationVol;
	}


	/*public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
*/

	public Timestamp getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Timestamp registeredOn) {
		this.registeredOn = registeredOn;
	}

	public Date getLastParticipationDate() {
		return lastParticipationDate;
	}

	public void setLastParticipationDate(Date lastParticipationDate) {
		this.lastParticipationDate = lastParticipationDate;
	}

	public Date getLastScreeningDate() {
		return lastScreeningDate;
	}

	public void setLastScreeningDate(Date lastScreeningDate) {
		this.lastScreeningDate = lastScreeningDate;
	}

	public Long getNoOfDaysCompletedFromLastPartcipation() {
		return noOfDaysCompletedFromLastPartcipation;
	}

	public void setNoOfDaysCompletedFromLastPartcipation(Long noOfDaysCompletedFromLastPartcipation) {
		this.noOfDaysCompletedFromLastPartcipation = noOfDaysCompletedFromLastPartcipation;
	}

	public String getvStatus() {
		return vStatus;
	}

	public void setvStatus(String vStatus) {
		this.vStatus = vStatus;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getRegisteredBy() {
		return registeredBy;
	}

	public void setRegisteredBy(String registeredBy) {
		this.registeredBy = registeredBy;
	}


	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/

	public Boolean getProceedForScreening() {
		return proceedForScreening;
	}

	
	public void setProceedForScreening(Boolean proceedForScreening) {
		this.proceedForScreening = proceedForScreening;
	}

	/*public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
*/

	public Long getCenterId() {
		return centerId;
	}

	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public Long getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Long approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}


	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Long getFingerId() {
		return fingerId;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public void setFingerId(Long fingerId) {
		this.fingerId = fingerId;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getBmi() {
		return bmi;
	}

	public void setBmi(String bmi) {
		this.bmi = bmi;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getUserStatusDate() {
		return userStatusDate;
	}

	public void setUserStatusDate(Timestamp userStatusDate) {
		this.userStatusDate = userStatusDate;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

//	@JsonIgnore
//	public Set<VolunteerScreeningHistory> getVolunteerScreeningHistories() {
//		return volunteerScreeningHistories;
//	}
//
//	public void setVolunteerScreeningHistories(Set<VolunteerScreeningHistory> volunteerScreeningHistories) {
//		this.volunteerScreeningHistories = volunteerScreeningHistories;
//	}

	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}

	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
*/
	
	
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
	
	@Override
	public String toString() {
	  StringBuilder sb = new StringBuilder();
	  sb.append(getClass().getName());
	  sb.append(": ");
	  for (Field f : getClass().getDeclaredFields()) {
	    sb.append(f.getName());
	    sb.append("=");
	    try {
			sb.append(f.get(this));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    sb.append(", ");
	  }
	  return sb.toString();
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

	public String getIdCardIssuedBy() {
		return idCardIssuedBy;
	}

	public void setIdCardIssuedBy(String idCardIssuedBy) {
		this.idCardIssuedBy = idCardIssuedBy;
	}

	public Date getIdCardIssuedDate() {
		return idCardIssuedDate;
	}

	public void setIdCardIssuedDate(Date idCardIssuedDate) {
		this.idCardIssuedDate = idCardIssuedDate;
	}

	public Date getScreeningActivityDate() {
		return screeningActivityDate;
	}

	public void setScreeningActivityDate(Date screeningActivityDate) {
		this.screeningActivityDate = screeningActivityDate;
	}

	public String getLatestStudyNumber() {
		return latestStudyNumber;
	}

	public void setLatestStudyNumber(String latestStudyNumber) {
		this.latestStudyNumber = latestStudyNumber;
	}
	
}