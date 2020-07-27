package com.xoftix.xdms.workflow.activiti.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoftix.xdms.workflow.activiti.config.AppConfig;
import com.xoftix.xdms.workflow.activiti.util.EmailUtil;
import com.xoftix.xdms.workflow.activiti.vo.EmailVo;

@Service
public class EmailService {
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private AppConfig appConfig;
	
	public EmailVo enviarNotificacionEmail(String asunto, String email, String mensaje) {
		EmailVo emailVo = new EmailVo();
		if (email != null && !email.isEmpty() && emailUtil.validarConexionServicio()) {
			emailVo.setMailTo(email);
			emailVo.setMailSubject(asunto);
			emailVo.setMailContent(mensaje);
			emailVo.setMailSendDate(String.valueOf(new Date()));
			emailVo.setMailFrom(appConfig.getEmail());
			emailUtil.enviarEmailBoolean(emailVo);
		}
		return emailVo;
	}
	
	
}
