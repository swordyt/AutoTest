package com.autotest.interfaces;

import java.util.List;

import com.autotest.model.Checks;
import com.autotest.model.Requests;
import com.autotest.model.Runners;
import com.autotest.model.Sets;

public interface RunnersMapper {
	//static wait running complete;
	public List<Sets> getSets(String state);
	public List<Requests> getRequests(int setid);
	public Checks getCheck(int setid);
	public void setStateAsStatic(int setid);
	public void setStateAsWait(int setid);
	public void setStateAsRunning(int setid);
	public void setStateAsComplete(int setid);
	public void setCheckstateAsOne(int setid);
	public void setCheckstateAsZero(int setid);
}
