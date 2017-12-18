package com.co.example.common.utils;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailUtil {

	/** 日志记录 */
	protected static final Log logger = LogFactory.getLog(EmailUtil.class);

	/**
	 * 发送email
	 * @param toEmail
	 * @param toName
	 * @param title
	 * @param content
	 */
	public static void sendEmail(String toEmail, String toName, String title,
			String content) {
		HtmlEmail email = new HtmlEmail();
		String fromEmail = "moncat@126.com";
		String fromEmailPwd= "95000736";
		String server = "smtp.126.com";
		email.setHostName(server);// 发信邮件服务器
		email.setAuthentication(fromEmail, fromEmailPwd);// smtp认证的用户名和密码
		try {
			email.addTo(toEmail, toName);// 收件人地址和收件人名字
			email.setFrom(fromEmail, "【管理员】");// 发信者
			email.setSubject(title);// 标题
			email.setCharset("UTF-8");// 编码格式
			email.setHtmlMsg(content);// 内容
			email.send();// 发送
		} catch (EmailException e) {
			logger.error("邮件发送失败!toEmail=" + toEmail + "content=" + content);
			e.printStackTrace();
		}
	}

	/**
	 * email发送验证码
	 * @param toEmail
	 * @param code
	 * @param usersName
	 */
	public static void sendEmailCode(String toEmail,String usersName, String code) {
		sendEmail(toEmail, usersName, "邮箱验证",sendEmailContent(usersName, code));
	}

	private static String sendEmailContent(String usersName, String code) {
		StringBuffer  sb = new StringBuffer();
        sb.append(usersName);
        sb.append( " ,您"+DateFormatUtil.format(new Date(),DateFormatUtil.formartDateTime));
        sb.append("提交的账户安全邮箱验证，验证码为："+code);
        sb.append(" 。该邮件为系统自动发送，请勿回复");
        return sb.toString();
	}

}
