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
@Table(name="completeUrineExamination")
public class CompleteUrineExamination {
@Id
@GeneratedValue
@Column
private Long id;
	
@Column(name="colour")
private String colour;

@Column(name="appearance")
private String appearance;

@Column(name="gravity")
private String gravity;

@Column(name="pH")
private String pH;

@Column(name="leucocytes")
private String leucocytes;

@Column(name="proteins")
private String proteins;

@Column(name="glucose")
private String glucose;

@Column(name="ketones")
private String ketones;

@Column(name="blood")
private String blood;

@Column(name="bilirubin")
private String bilirubin;

@Column(name="urobilinogen")
private String urobilinogen;

@Column(name="nitrite")
private String nitrite;

@Column(name="pus_cells")
private String pusCells;

@Column(name="red_blood_cells")
private String redBloodCells;

@Column(name="epithelial_cells")
private String epithelialCells;

@Column(name="casts")
private String casts;

@Column(name="crystals")
private String crystals;

@Column(name="others")
private String others;

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

public String getColour() {
	return colour;
}

public void setColour(String colour) {
	this.colour = colour;
}

public String getAppearance() {
	return appearance;
}

public void setAppearance(String appearance) {
	this.appearance = appearance;
}

public String getGravity() {
	return gravity;
}

public void setGravity(String gravity) {
	this.gravity = gravity;
}

public String getpH() {
	return pH;
}

public void setpH(String pH) {
	this.pH = pH;
}

public String getLeucocytes() {
	return leucocytes;
}

public void setLeucocytes(String leucocytes) {
	this.leucocytes = leucocytes;
}

public String getProteins() {
	return proteins;
}

public void setProteins(String proteins) {
	this.proteins = proteins;
}

public String getGlucose() {
	return glucose;
}

public void setGlucose(String glucose) {
	this.glucose = glucose;
}

public String getKetones() {
	return ketones;
}

public void setKetones(String ketones) {
	this.ketones = ketones;
}

public String getBlood() {
	return blood;
}

public void setBlood(String blood) {
	this.blood = blood;
}

public String getBilirubin() {
	return bilirubin;
}

public void setBilirubin(String bilirubin) {
	this.bilirubin = bilirubin;
}

public String getUrobilinogen() {
	return urobilinogen;
}

public void setUrobilinogen(String urobilinogen) {
	this.urobilinogen = urobilinogen;
}

public String getNitrite() {
	return nitrite;
}

public void setNitrite(String nitrite) {
	this.nitrite = nitrite;
}

public String getPusCells() {
	return pusCells;
}

public void setPusCells(String pusCells) {
	this.pusCells = pusCells;
}

public String getRedBloodCells() {
	return redBloodCells;
}

public void setRedBloodCells(String redBloodCells) {
	this.redBloodCells = redBloodCells;
}

public String getEpithelialCells() {
	return epithelialCells;
}

public void setEpithelialCells(String epithelialCells) {
	this.epithelialCells = epithelialCells;
}

public String getCasts() {
	return casts;
}

public void setCasts(String casts) {
	this.casts = casts;
}

public String getCrystals() {
	return crystals;
}

public void setCrystals(String crystals) {
	this.crystals = crystals;
}

public String getOthers() {
	return others;
}

public void setOthers(String others) {
	this.others = others;
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

