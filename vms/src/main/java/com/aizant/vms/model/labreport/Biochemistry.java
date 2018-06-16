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

@Entity
@Table(name="biochemistry")
public class Biochemistry {
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(name="rpg")
	private String rpg;
	
	@Column(name="urea")
	private String urea;
	
	@Column(name="creatinine")
	private String creatinine;
	
	@Column(name="total_bilirubin")
	private String totalBilirubin;
	
	@Column(name="sgot")
	private String sgot;
	
	@Column(name="sgpt")
	private String sgpt;
	
	@Column(name="alkaline_phosphatase")
	private String alkalinePhosphatase;
	
	@Column(name="electrolytes")
	private String electrolytes;
	
	@Column(name="sodium")
	private String sodium;
	
	@Column(name="potassium")
	private String potassium;
	
	@Column(name="chloride")
	private String chloride;
	
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

	public String getRpg() {
		return rpg;
	}

	public void setRpg(String rpg) {
		this.rpg = rpg;
	}

	public String getUrea() {
		return urea;
	}

	public void setUrea(String urea) {
		this.urea = urea;
	}

	public String getCreatinine() {
		return creatinine;
	}

	public void setCreatinine(String creatinine) {
		this.creatinine = creatinine;
	}

	public String getTotalBilirubin() {
		return totalBilirubin;
	}

	public void setTotalBilirubin(String totalBilirubin) {
		this.totalBilirubin = totalBilirubin;
	}

	public String getSgot() {
		return sgot;
	}

	public void setSgot(String sgot) {
		this.sgot = sgot;
	}

	public String getSgpt() {
		return sgpt;
	}

	public void setSgpt(String sgpt) {
		this.sgpt = sgpt;
	}

	public String getAlkalinePhosphatase() {
		return alkalinePhosphatase;
	}

	public void setAlkalinePhosphatase(String alkalinePhosphatase) {
		this.alkalinePhosphatase = alkalinePhosphatase;
	}

	public String getElectrolytes() {
		return electrolytes;
	}

	public void setElectrolytes(String electrolytes) {
		this.electrolytes = electrolytes;
	}

	public String getSodium() {
		return sodium;
	}

	public void setSodium(String sodium) {
		this.sodium = sodium;
	}

	public String getPotassium() {
		return potassium;
	}

	public void setPotassium(String potassium) {
		this.potassium = potassium;
	}

	public String getChloride() {
		return chloride;
	}

	public void setChloride(String chloride) {
		this.chloride = chloride;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
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

	public LabDetails getLabDetails() {
		return labDetails;
	}

	public void setLabDetails(LabDetails labDetails) {
		this.labDetails = labDetails;
	}
	
	
}
