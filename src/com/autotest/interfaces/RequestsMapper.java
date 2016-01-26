package com.autotest.interfaces;

import java.util.List;

import com.autotest.model.Requests;

public interface RequestsMapper {
	public Requests selectRequestByID(int id);
	public List<Requests> selectRequests();
	public void addRequests(Requests request);
	public void updateRequests(Requests request);
}
