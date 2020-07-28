package com.exercise.pingidentity.network.response;

import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("error")
	private String error;

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"error = '" + error + '\'' + 
			"}";
		}
}