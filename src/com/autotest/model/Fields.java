package com.autotest.model;

public class Fields {
	private Integer id;
	private String name;
	private Requests request;
	private Integer deleted_at;
	private Integer created_at;
	private Integer updated_at;
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
	public Requests getRequest() {
		return request;
	}
	public void setRequest(Requests request) {
		this.request = request;
	}
	public Integer getDeleted_at() {
		return deleted_at;
	}
	public void setDeleted_at(Integer deleted_at) {
		this.deleted_at = deleted_at;
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
