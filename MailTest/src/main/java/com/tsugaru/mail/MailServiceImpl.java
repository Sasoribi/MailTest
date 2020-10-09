package com.tsugaru.mail;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tsugaru.utils.Attachment;

/**
 * 
 * 项目名称：MailTest 类名称：MailServiceImpl 类描述：附件邮件和纯文本邮件的接口实现 创建人：sasoribi
 * 创建时间：2020年10月9日 下午8:02:11
 * 
 * @version
 */
@Service
public class MailServiceImpl implements MailService {

	private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;
	@Value("${mail.from}")
	private String sender;

	/**
	 * 纯本文邮件发送
	 */
	@Override
	public void sendSimpleMail(String sendTo, String message) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setFrom(sender);
		smm.setTo(sendTo);
		smm.setSubject("A TestMail");
		smm.setText(message);
		mailSender.send(smm);
		logger.info("邮件已发送!");
	}

	/**
	 * 附件邮件发送 
	 * 
	 * @exception java.lang.IllegalStateException Not in multipart mode - create an appropriate MimeMessageHelper via a
	 * constructor that takes a 'multipart' flag if you need to set alternative
	 * texts or add inline elements or attachments.如果要发送多元素邮件,创建Helper时需要设置multipartFlagToTrue.
	 */
	@Override
	public void sendComplexMail(String sendTo, String message, Attachment attachment) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(mimeMessage,true);
			helper.setTo(sendTo);
			helper.setFrom(sender);
			helper.setSubject("A Test Mail With Appender!");
			helper.setText(message);
			helper.addAttachment(attachment.getAttachementName(), (File) attachment.getAttachement());
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			logger.error(e.toString());
			logger.error("创建附件信息失败!");
		}

	}
}