package com.aizant.vms.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "volunteer_status")
public class VolunteerStatus extends Auditable<String>{

	@Id
	@GeneratedValue
	private Long id;

	
	@Column(name = "volunteer_registration_number",nullable=false)
	private String registrationNumber;

	@Column(name = "is_rejected_permanantly")
	private Boolean isRejectedPermanantly;

	@Column(name = "rejected_no_of_days")
	private Long rejectedNoOfDays;

	@Column(name = "reason_for_rejection")
	private String reasonForRejection;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_master_id", nullable = false)
	private StatusMaster statusMaster;
	
	@Column(name="rejected_date")
	private Date rejectedDate;
	
	@Column(name="screening_id")
	private Long screeningId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public StatusMaster getStatusMaster() {
		return statusMaster;
	}

	public void setStatusMaster(StatusMaster statusMaster) {
		this.statusMaster = statusMaster;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Date getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(Date rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	public Long getScreeningId() {
		return screeningId;
	}

	public void setScreeningId(Long screeningId) {
		this.screeningId = screeningId;
	}
	
	

}
