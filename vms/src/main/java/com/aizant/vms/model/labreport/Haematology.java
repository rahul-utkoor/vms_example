package com.aizant.vms.model.labreport;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="haematology")
public class Haematology {
	
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(name="wbc_total")
	private String wbcTotal;
	
	@Column(name="differentital_count")
	private String differentialCount;
	
	@Column(name="neutrophils")
	private String neutrophils;
	
	@Column(name="lymphocytes")
	private String lymphocytes;
	
	@Column(name="eosinophils")
	private String eosinophils;
	
	@Column(name="monocytes")
	private String monocytes;
	
	@Column(name="basophils")
	private String basophils;
	
	@Column(name="eosinophils_abs")
	private String eosinophils_abs;
	
	@Column(name="rbc_count")
	private String rbcCount;
	
	@Column(name="haemoglobin")
	private String haemoglobin;
	
	@Column(name="hct")
	private String hct;
	
	@Column(name="mcv")
	private String mcv;
	
	@Column(name="mch")
	private String mch;
	
	@Column(name="mchc")
	private String mchc;
	
	@Column(name="rdw")
	private String rdw;
	
	@Column(name="platelet_count")
	private String plateletCount;
	
	@Column(name="mpv")
	private String mpv;
	
	@Column(name="esr")
	private String esr;
	
	@Column(name="blood_group")
	private String bloodGroup;
	
	@Column(name="sample_type")
	private String sampleType;
	
	@Column(name="prepared_by")
	private String preparedBy;
	
	@Column(name="prepared_on")
	private Timestamp preparedOn;
	
	@Column(name="reviewed_by")
	private String reviewedBy;
	
	@Column(name="reviewed_on")
	private Timestamp reviewedOn;
	
	@Column(name="authorized_by")
	private String authorizedBy;
	
	@Column(name="authorized_on")
	private Timestamp authorizedOn;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "labDetails_id",unique=true)
    private LabDetails labDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWbcTotal() {
		return wbcTotal;
	}

	public void setWbcTotal(String wbcTotal) {
		this.wbcTotal = wbcTotal;
	}

	public String getDifferentialCount() {
		return differentialCount;
	}

	public void setDifferentialCount(String differentialCount) {
		this.differentialCount = differentialCount;
	}

	public String getNeutrophils() {
		return neutrophils;
	}

	public void setNeutrophils(String neutrophils) {
		this.neutrophils = neutrophils;
	}

	public String getLymphocytes() {
		return lymphocytes;
	}

	public void setLymphocytes(String lymphocytes) {
		this.lymphocytes = lymphocytes;
	}

	public String getEosinophils() {
		return eosinophils;
	}

	public void setEosinophils(String eosinophils) {
		this.eosinophils = eosinophils;
	}

	public String getMonocytes() {
		return monocytes;
	}

	public void setMonocytes(String monocytes) {
		this.monocytes = monocytes;
	}

	public String getBasophils() {
		return basophils;
	}

	public void setBasophils(String basophils) {
		this.basophils = basophils;
	}

	public String getEosinophils_abs() {
		return eosinophils_abs;
	}

	public void setEosinophils_abs(String eosinophils_abs) {
		this.eosinophils_abs = eosinophils_abs;
	}

	public String getRbcCount() {
		return rbcCount;
	}

	public void setRbcCount(String rbcCount) {
		this.rbcCount = rbcCount;
	}

	public String getHaemoglobin() {
		return haemoglobin;
	}

	public void setHaemoglobin(String haemoglobin) {
		this.haemoglobin = haemoglobin;
	}

	public String getHct() {
		return hct;
	}

	public void setHct(String hct) {
		this.hct = hct;
	}

	public String getMcv() {
		return mcv;
	}

	public void setMcv(String mcv) {
		this.mcv = mcv;
	}

	public String getMch() {
		return mch;
	}

	public void setMch(String mch) {
		this.mch = mch;
	}

	public String getMchc() {
		return mchc;
	}

	public void setMchc(String mchc) {
		this.mchc = mchc;
	}

	public String getRdw() {
		return rdw;
	}

	public void setRdw(String rdw) {
		this.rdw = rdw;
	}

	public String getPlateletCount() {
		return plateletCount;
	}

	public void setPlateletCount(String plateletCount) {
		this.plateletCount = plateletCount;
	}

	public String getMpv() {
		return mpv;
	}

	public void setMpv(String mpv) {
		this.mpv = mpv;
	}

	public String getEsr() {
		return esr;
	}

	public void setEsr(String esr) {
		this.esr = esr;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	@JsonIgnore
	public LabDetails getLabDetails() {
		return labDetails;
	}

	
	public void setLabDetails(LabDetails labDetails) {
		this.labDetails = labDetails;
	}

	public String getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	public Timestamp getPreparedOn() {
		return preparedOn;
	}

	public void setPreparedOn(Timestamp preparedOn) {
		this.preparedOn = preparedOn;
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

	public String getAuthorizedBy() {
		return authorizedBy;
	}

	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}

	public Timestamp getAuthorizedOn() {
		return authorizedOn;
	}

	public void setAuthorizedOn(Timestamp authorizedOn) {
		this.authorizedOn = authorizedOn;
	}
	
	
}
