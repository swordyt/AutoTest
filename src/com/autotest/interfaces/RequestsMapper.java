package com.autotest.interfaces;

import java.util.List;

import com.autotest.model.Fields;
import com.autotest.model.Requests;

public interface RequestsMapper {
	public Requests selectRequestByID(int id);
	public List<Requests> selectRequests();
	public void addRequest(Requests request);
	public void updateRequest(Requests request);
	public void deleteRequest(int id);
	public List<Fields> getFields(int id);
}
