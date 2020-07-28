package com.exercise.pingidentity.network.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class FcmResponse {

	@SerializedName("canonical_ids")
	private int canonicalIds;

	@SerializedName("success")
	private int success;

	@SerializedName("failure")
	private int failure;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("multicast_id")
	private long multicastId;

	public void setCanonicalIds(int canonicalIds){
		this.canonicalIds = canonicalIds;
	}

	public int getCanonicalIds(){
		return canonicalIds;
	}

	public void setSuccess(int success){
		this.success = success;
	}

	public int getSuccess(){
		return success;
	}

	public void setFailure(int failure){
		this.failure = failure;
	}

	public int getFailure(){
		return failure;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setMulticastId(long multicastId){
		this.multicastId = multicastId;
	}

	public long getMulticastId(){
		return multicastId;
	}

	@Override
 	public String toString(){
		return 
			"FCMResponse{" + 
			"canonical_ids = '" + canonicalIds + '\'' + 
			",success = '" + success + '\'' + 
			",failure = '" + failure + '\'' + 
			",results = '" + results + '\'' + 
			",multicast_id = '" + multicastId + '\'' + 
			"}";
		}
}