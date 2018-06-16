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
@Table(name="serology")
public class Serology {
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(name="hbsag")
	private String hbsag;
	
	@Column(name="ahcv")
	private String ahcv;
	
	@Column(name="ahiv")
	private String ahiv;
	
	@Column(name="vdrl")
	private String vdrl;
	
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

	public String getHbsag() {
		return hbsag;
	}

	public void setHbsag(String hbsag) {
		this.hbsag = hbsag;
	}

	public String getAhcv() {
		return ahcv;
	}

	public void setAhcv(String ahcv) {
		this.ahcv = ahcv;
	}

	public String getAhiv() {
		return ahiv;
	}

	public void setAhiv(String ahiv) {
		this.ahiv = ahiv;
	}

	public String getVdrl() {
		return vdrl;
	}

	public void setVdrl(String vdrl) {
		this.vdrl = vdrl;
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
