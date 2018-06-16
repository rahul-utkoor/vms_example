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

@Entity
@Table(name="v_xray")
public class VolunteerXRay extends Auditable<String>{

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(name="soft_tissue")
	private String softTissue;
	
	@Column(name="soft_tissue_comment")
	private String softTissueComment;
	
	@Column(name="heart_size")
	private String heartSize;
	
	@Column(name="heart_size_comment")
	private String heartSizeComment;
	
	@Column(name="lung_fields")
	private String lungFields;
	
	@Column(name="lung_fields_comment")
	private String lungFieldsComment;
	
	@Column(name="diaphragm_demos")
	private String diaphragmDemos;
	
	@Column(name="diaphragm_demos_comment")
	private String diaphragmDemosComment;
	
	@Column(name="cp_angles")
	private String cpAngles;
	
	@Column(name="cp_angles_comment")
	private String cpAnglesComment;
	
	@Column(name="impression")
	private String impression;
	
	@Column(name="impression_comment")
	private String impressionComment;
	
	@Column(name="physician_by")
	private String physicianBy;
	
	@Column(name="physician_date")
	private Timestamp physicianDate;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="xray_status")
	private String xrayStatus;
	
	@Column(name="radiologist_by")
	private String radiologistBy;
	
	@Column(name="radiologist_date")
	private Timestamp radiologistDate;
	
	@Column(name="xray_date",nullable=false)
	private Date xrayDate;
	
	@Column(name="xray_time",nullable=false)
	private String xrayTime;

	@Column(name="x_ray_location")
	private String xRayFileLocation;
	
	@Column(name="test_date")
	private String testDate;
	
/*	@Column(name = "recorded_by",nullable=false)
	private Long recordedBy;

	@Column(name = "recorded_on")
	private Timestamp recordedOn;*/

	/*@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@Column(name = "updated_by")
	private Long updatedBy;*/

	/*@Column(name = "reviewed_by")
	private Long reviewedBy;

	@Column(name = "reviewed_on")
	private Timestamp reviewedOn;*/
	
/*	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
	@JoinColumn(name="volunteer_registration_number",referencedColumnName="registration_number")
	private Volunteer volunteer;*/
	
	@Transient
	private String updateComments;
	
	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}

	
	@Column(name="volunteer_registration_number",nullable=false)
	private String regNum;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	@JoinColumn(name="volunteer_center_id",referencedColumnName="id")
	private StudyCenter center;
	@Transient
	private String imageName;
	
	@Transient
	private String reviewedOnDate;
	
	@Transient
	private String recordedOnDate;

	@Transient
	private String volunteerRegNum;

	@Transient
	private String volunteerAge;
	
	@Transient
	private String volunteerSex;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSoftTissue() {
		return softTissue;
	}

	public void setSoftTissue(String softTissue) {
		this.softTissue = softTissue;
	}

	public String getHeartSize() {
		return heartSize;
	}

	public void setHeartSize(String heartSize) {
		this.heartSize = heartSize;
	}

	public String getLungFields() {
		return lungFields;
	}

	public void setLungFields(String lungFields) {
		this.lungFields = lungFields;
	}

	public String getDiaphragmDemos() {
		return diaphragmDemos;
	}

	public void setDiaphragmDemos(String diaphragmDemos) {
		this.diaphragmDemos = diaphragmDemos;
	}

	public String getCpAngles() {
		return cpAngles;
	}

	public void setCpAngles(String cpAngles) {
		this.cpAngles = cpAngles;
	}

	public String getImpression() {
		return impression;
	}

	public void setImpression(String impression) {
		this.impression = impression;
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

	

	public String getxRayFileLocation() {
		return xRayFileLocation;
	}

	public void setxRayFileLocation(String xRayFileLocation) {
		this.xRayFileLocation = xRayFileLocation;
	}

	

	public StudyCenter getCenter() {
		return center;
	}

	public void setCenter(StudyCenter center) {
		this.center = center;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
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

	public String getSoftTissueComment() {
		return softTissueComment;
	}

	public void setSoftTissueComment(String softTissueComment) {
		this.softTissueComment = softTissueComment;
	}

	public String getHeartSizeComment() {
		return heartSizeComment;
	}

	public void setHeartSizeComment(String heartSizeComment) {
		this.heartSizeComment = heartSizeComment;
	}

	public String getLungFieldsComment() {
		return lungFieldsComment;
	}

	public void setLungFieldsComment(String lungFieldsComment) {
		this.lungFieldsComment = lungFieldsComment;
	}

	public String getDiaphragmDemosComment() {
		return diaphragmDemosComment;
	}

	public void setDiaphragmDemosComment(String diaphragmDemosComment) {
		this.diaphragmDemosComment = diaphragmDemosComment;
	}

	public String getCpAnglesComment() {
		return cpAnglesComment;
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public void setCpAnglesComment(String cpAnglesComment) {
		this.cpAnglesComment = cpAnglesComment;
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

	public void setXrayDate(Date date) {
		this.xrayDate = date;
	}

	public String getXrayTime() {
		return xrayTime;
	}

	public void setXrayTime(String xrayTime) {
		this.xrayTime = xrayTime;
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

	


}
