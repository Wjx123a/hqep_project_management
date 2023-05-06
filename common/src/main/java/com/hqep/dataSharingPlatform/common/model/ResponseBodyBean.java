package com.hqep.dataSharingPlatform.common.model;

import java.io.Serializable;


public class ResponseBodyBean implements Serializable{

	private ReasonBean reason;
	private Object result;


	/**
	 * @return result
	 */

	public Object getResult() {
		return result;
	}
	/**
	 * @param result 要设置的 result
	 */
	public void setResult(Object result) {
		this.result = result;
	}


	/**
	 * @return reason
	 */

	public ReasonBean getReason() {
		return reason;
	}
	/**
	 * @param reason 要设置的 reason
	 */
	public void setReason(ReasonBean reason) {
		this.reason = reason;
	}

	/** (非 Javadoc)
	*
	*
	* @return
	* @see Object#toString()
	*/
	@Override
	public String toString() {
		return "ResponseBodyBean [reason=" + reason + ", result=" + result
				+ "]";
	}

	/**
	*
	*
	* @param reason
	* @param result
	*/

	public ResponseBodyBean(ReasonBean reason, Object result) {
		super();
		this.reason = reason;
		this.result = result;
	}

	/**
	*
	*
	*/

	public ResponseBodyBean() {
		super();
		// TODO Auto-generated constructor stub
	}




}
