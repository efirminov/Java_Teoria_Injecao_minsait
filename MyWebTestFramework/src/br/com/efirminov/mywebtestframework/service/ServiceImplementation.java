package br.com.efirminov.mywebtestframework.service;

import br.com.efirminov.webframeword.annotations.WebframeworkService;

@WebframeworkService
public class ServiceImplementation implements IService {

	@Override
	public String chamadaCustom(String mensagem) {
		return "Teste chamada servico: " + mensagem;
	}

}