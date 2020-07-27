package com.xoftix.xdms.workflow.activiti.util;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.xoftix.xdms.workflow.activiti.config.AppConfig;
import com.xoftix.xdms.workflow.activiti.vo.EmailVo;


/**
 * 
 * @author plabrada
 *
 */
@Service
public class EmailUtil implements Serializable {

	private static final long serialVersionUID = -6859512609799661089L;
	public final static String CONEXION_EXITOSA = "Mail Service is Up!!";

	@Autowired
	private AppConfig appConfig;

	/**
	 * Validar si hay conexión con el servicio de envío de emails
	 * 
	 * @return true si la conexión fue realizada con éxito y false en caso contrario
	 */
	public Boolean validarConexionServicio() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(appConfig.getUrlStatusMail(), String.class);
		if (response.getBody().equals(CONEXION_EXITOSA)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Enviar un email
	 * 
	 * @param emailVo información del email
	 * @return true si el mensaje fue enviado, false en caso contrario
	 */
	public Boolean enviarEmailBoolean(EmailVo emailVo) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		// Construir body. Nota: pueden llegar valores nulos al body
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("mailFrom", emailVo.getMailFrom());
		body.add("mailTo", emailVo.getMailTo());
		body.add("mailSubject", emailVo.getMailSubject());
		body.add("mailContent", emailVo.getMailContent());
		body.add("mailSendDate", emailVo.getMailSendDate());

		if (emailVo.getContentType() != null) {
			body.add("contentType", emailVo.getContentType());
		}
		if (emailVo.getMailBcc() != null) {
			body.add("mailBcc", emailVo.getMailBcc());
		}
		if (emailVo.getMailBcc() != null) {
			body.add("mailCc", emailVo.getMailCc());
		}

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(appConfig.getUrlSendEmail(), requestEntity,
				String.class);

		if (response.getStatusCodeValue() == 200) {
			return true;
		} else {
			return false;
		}
	}

}
