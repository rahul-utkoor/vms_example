package com.aizant.vms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "vol_finger_dtls")
public class VolunteerFingerDtls {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column
	private String dbid;

	@Lob
	@Column(length = 100000)
	private byte[] template;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDbid() {
		return dbid;
	}

	public void setDbid(String dbid) {
		this.dbid = dbid;
	}

	public byte[] getTemplate() {
		return template;
	}

	public void setTemplate(byte[] template) {
		this.template = template;
	}
	
	
	
	
	

}
