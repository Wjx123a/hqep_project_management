package com.hqep.dataSharingPlatform.common.model;



public class ReasonBean {

	private String code;
	private String text;
	/**
	 * @return code
	 */

	public String getCode() {
		return code;
	}
	/**
	 * @param code 要设置的 code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return text
	 */

	public String getText() {
		return text;
	}
	/**
	 * @param text 要设置的 text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/** (非 Javadoc)
	*
	*
	* @return
	* @see Object#toString()
	*/
	@Override
	public String toString() {
		return "ReasonBean [code=" + code + ", text=" + text + "]";
	}

	/**
	*
	*
	* @param code
	* @param text
	*/

	public ReasonBean(String code, String text) {
		super();
		this.code = code;
		this.text = text;
	}

	/**
	*
	*
	*/

	public ReasonBean() {
		super();
		// TODO Auto-generated constructor stub
	}


}
