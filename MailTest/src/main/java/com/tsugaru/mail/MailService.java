package com.tsugaru.mail;

import com.tsugaru.utils.Attachment;

public interface MailService {
	
	public void sendSimpleMail(String sendTo, String message);
	
	public void sendComplexMail(String sendTo, String message ,Attachment subject);
}
