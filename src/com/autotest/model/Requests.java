package com.autotest.model;

public class Requests {
	private Integer id;
	private String name;
	private String dataaddress;
	private Integer created_at;
	private Integer updated_at;
	private Integer delete_at;
	public Integer getDelete_at() {
		return delete_at;
	}
	public void setDelete_at(Integer delete_at) {
		this.delete_at = delete_at;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataaddress() {
		return dataaddress;
	}
	public void setDataaddress(String dataaddress) {
		this.dataaddress = dataaddress;
	}
	public Integer getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Integer created_at) {
		this.created_at = created_at;
	}
	public Integer getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Integer updated_at) {
		this.updated_at = updated_at;
	}
}
