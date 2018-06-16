package com.aizant.vms.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="vms_audit")
public class Audit extends Auditable<String>{
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="action_performed")
	private String actionPerformed;
	
	@Column(name="action_performed_date")
	private Timestamp actionPerformedDate;
	
	@Column(name="action_performed_by")
	private String actionPerformedBy;
	
	@Lob 
	@Column(name="value")
	private String value;
	
	@Column(name="column_name")
	private String columnName;
	
	@Column(name="old_value")
	private String oldValue;
	
	@Column(name="new_value")
	private String newValue;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="form_name")
	private String formName;
	
	@Column(name="table_name")
	private String tableName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionPerformed() {
		return actionPerformed;
	}

	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

	public Timestamp getActionPerformedDate() {
		return actionPerformedDate;
	}

	public void setActionPerformedDate(Timestamp actionPerformedDate) {
		this.actionPerformedDate = actionPerformedDate;
	}

	public String getActionPerformedBy() {
		return actionPerformedBy;
	}

	public void setActionPerformedBy(String actionPerformedBy) {
		this.actionPerformedBy = actionPerformedBy;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@Override
	public String toString() {
		return "Audit [id=" + id + ", actionPerformed=" + actionPerformed + ", actionPerformedDate="
				+ actionPerformedDate + ", actionPerformedBy=" + actionPerformedBy + ", value=" + value
				+ ", columnName=" + columnName + ", oldValue=" + oldValue + ", newValue=" + newValue + ", comments="
				+ comments + ", formName=" + formName + ", tableName=" + tableName + "]";
	}
	
}
