package com.aizant.vms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "vol_image_dtls")
public class VolunteerImageDtls {

	@Id
	@GeneratedValue
	@Column(name="image_id")
	private Long imageId;

	@Lob
	@Column(length = 100000)
	private byte[] photo;

	@Lob
	@Column(length = 100000)
	private byte[] ageproof;

	@Lob
	@Column(length = 100000)
	private byte[] volSig;


	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public byte[] getAgeproof() {
		return ageproof;
	}

	public void setAgeproof(byte[] ageproof) {
		this.ageproof = ageproof;
	}

	public byte[] getVolSig() {
		return volSig;
	}

	public void setVolSig(byte[] volSig) {
		this.volSig = volSig;
	}

}
