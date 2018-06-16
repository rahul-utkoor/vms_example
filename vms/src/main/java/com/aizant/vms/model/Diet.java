package com.aizant.vms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="diet")
public class Diet {

	@Id
	@Column
	private Integer id;
	
	@Column
	private String diet;
	

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDiet() {
		return diet;
	}
	
	public void setDiet(String diet) {
		this.diet = diet;
	}
}
