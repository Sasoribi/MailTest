package com.tsugaru.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tsugaru.mail.MailService;
import com.tsugaru.utils.Attachment;

@RestController
@RequestMapping(value="/mail")
public class MailController {
	
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value="/sendmail/{message}",method=RequestMethod.POST)
	public String mailsend(@PathVariable String message,
			@RequestParam("sendTo") String sendTo
			) {
		if(StringUtils.isEmpty(message)) {
			return "信息为空发送失败!";
		}
		
		mailService.sendSimpleMail(sendTo, message);
		
		return "信息已经进入发送队列!";
	}
	@RequestMapping(value="/sendcmail/{message}",method=RequestMethod.POST)
	public String mailsendWithFile(@PathVariable String message,
			@RequestParam("sendTo") String sendTo
			) {
		if(StringUtils.isEmpty(message)) {
			return "信息为空发送失败!";
		}
		File file = new File("/Users/sasoribi/Pictures/WechatIMG12764.jpeg");
		Attachment atm = new Attachment();
		atm.setAttachement(file);
		atm.setAttachementName(file.getName());
		mailService.sendComplexMail(sendTo, message, atm);
		
		return "信息已经进入发送队列!";
	}
}
