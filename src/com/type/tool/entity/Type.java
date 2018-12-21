package com.type.tool.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "catagory")
public class Type implements Serializable{

	private static final long serialVersionUID = 5677118288790705648L;
	@Id
	@Column(name="id")
	private Long id;
	@Column(name="catagory_name")
	private String catagory_name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCatagory_name() {
		return catagory_name;
	}
	public void setCatagory_name(String catagory_name) {
		this.catagory_name = catagory_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
