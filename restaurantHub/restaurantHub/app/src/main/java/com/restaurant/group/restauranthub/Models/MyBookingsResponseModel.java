package com.restaurant.manasa.restauranthub.webservices;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyBookingsResponseModel{

	@SerializedName("result")
	private List<MyBookingsItem> result;

	@SerializedName("status")
	private boolean status;

	public void setResult(List<MyBookingsItem> result){
		this.result = result;
	}

	public List<MyBookingsItem> getResult(){
		return result;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"MyBookingsResponseModel{" + 
			"result = '" + result + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}