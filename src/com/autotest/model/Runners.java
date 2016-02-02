package com.autotest.model;

public class Runners {
	private Checks checkid;
	private Requests requestid;
	private Sets setid;
	public Checks getCheckid() {
		return checkid;
	}
	public void setCheckid(Checks checkid) {
		this.checkid = checkid;
	}
	public Requests getRequestid() {
		return requestid;
	}
	public void setRequestid(Requests requestid) {
		this.requestid = requestid;
	}
	public Sets getSetid() {
		return setid;
	}
	public void setSetid(Sets setid) {
		this.setid = setid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getCheckstate() {
		return checkstate;
	}
	public void setCheckstate(Integer checkstate) {
		this.checkstate = checkstate;
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
	public Integer getDeleted_at() {
		return deleted_at;
	}
	public void setDeleted_at(Integer deleted_at) {
		this.deleted_at = deleted_at;
	}
	private String state;
	private Integer checkstate=0;
	private Integer created_at;
	private Integer updated_at;
	private Integer deleted_at;
}
