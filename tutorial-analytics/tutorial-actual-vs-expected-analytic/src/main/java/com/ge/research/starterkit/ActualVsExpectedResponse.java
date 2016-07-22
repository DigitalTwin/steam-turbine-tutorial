package com.ge.research.starterkit;

import java.util.Map;

public class ActualVsExpectedResponse {

	private Map<String, Object> result;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ActualVsExpectedResponse that = (ActualVsExpectedResponse) o;

		if (result != null ? !result.equals(that.result) : that.result != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return result != null ? result.hashCode() : 0;
	}

	@Override public String toString() {
		return "Output{" +
			"result=" + result + '}';
	}


	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> value) {
		this.result = value;
	}

}
