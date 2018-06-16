package com.aizant.vms.model.labreport;

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

import com.aizant.vms.model.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="lab_details")
public class LabDetails extends Auditable<String>{

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	
	@Column(name="lab_id")
	private String labId;
	
	@Column(name="reg_num",nullable=false)
	private String regNum;
	
	@Transient
	private String age;
	
	@Column(name="referred_by")
	private String referredBy;
	
	@Column(name="subject_initials")
	private String subjectInitials;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="project_no")
	private String projectNo;
	
	@Column(name="sample_collected_at")
	private String samplecollectedAt;
	
	@Column(name="sample_collected_on")
	private Timestamp samplecollectedOn;
	
	@Column(name="sample_received_on")
	private Timestamp sampleReceivedOn;
	
	
	@Column(name="reported_on")
	private Timestamp reportedOn;
	
	@OneToMany(mappedBy="labDetails",fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private Set<Haematology> haematologies;

	@OneToMany(mappedBy="labDetails",fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private Set<Biochemistry> biochemistries;

	@OneToMany(mappedBy="labDetails",fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private Set<Serology> serologies;
	
	@OneToMany(mappedBy="labDetails",fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private Set<CompleteUrineExamination> completeUrineExamination;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public String getSubjectInitials() {
		return subjectInitials;
	}

	public void setSubjectInitials(String subjectInitials) {
		this.subjectInitials = subjectInitials;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getSamplecollectedAt() {
		return samplecollectedAt;
	}

	public void setSamplecollectedAt(String samplecollectedAt) {
		this.samplecollectedAt = samplecollectedAt;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Kolkata")
	public Timestamp getSamplecollectedOn() {
		return samplecollectedOn;
	}

	public void setSamplecollectedOn(Timestamp samplecollectedOn) {
		this.samplecollectedOn = samplecollectedOn;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Kolkata")
	public Timestamp getSampleReceivedOn() {
		return sampleReceivedOn;
	}

	public void setSampleReceivedOn(Timestamp sampleReceivedOn) {
		this.sampleReceivedOn = sampleReceivedOn;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Kolkata")
	public Timestamp getReportedOn() {
		return reportedOn;
	}

	public void setReportedOn(Timestamp reportedOn) {
		this.reportedOn = reportedOn;
	}
	
	
	
	
	
	
}
