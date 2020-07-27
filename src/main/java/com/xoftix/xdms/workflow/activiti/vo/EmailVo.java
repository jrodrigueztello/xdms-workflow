package com.xoftix.xdms.workflow.activiti.vo;
/***
 * @author plabrada
 *
 */

public class EmailVo {

	private String contentType;
	private String mailBcc;
	private String mailCc;
	private String mailContent;
	private String mailFrom;
	private String mailSendDate;
	private String mailSubject;
	private String mailTo;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}

	public String getMailCc() {
		return mailCc;
	}

	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailSendDate() {
		return mailSendDate;
	}

	public void setMailSendDate(String mailSendDate) {
		this.mailSendDate = mailSendDate;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

}
