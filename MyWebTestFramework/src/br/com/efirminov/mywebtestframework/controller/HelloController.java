package br.com.efirminov.mywebtestframework.controller;

import br.com.efirminov.mywebtestframework.model.Produto;
import br.com.efirminov.mywebtestframework.service.IService;
import br.com.efirminov.webframeword.annotations.WebframeworkBody;
import br.com.efirminov.webframeword.annotations.WebframeworkController;
import br.com.efirminov.webframeword.annotations.WebframeworkGetMethod;
import br.com.efirminov.webframeword.annotations.WebframeworkInject;
import br.com.efirminov.webframeword.annotations.WebframeworkPostMethod;

@WebframeworkController
public class HelloController {

	@WebframeworkInject
	private IService iService;

	@WebframeworkGetMethod("/hello")
	public String returnHelloWorld() {
		return "Return Hello world!!!";
	}

	@WebframeworkGetMethod("/produto")
	public Produto exibirProduto() {
		Produto p = new Produto(1, "Nome1", 5432.1, "teste.jpg");
		return p;
	}

	@WebframeworkPostMethod("/produto")
	public Produto cadastrarProduto(@WebframeworkBody Produto produtoNovo) {
		System.out.println(produtoNovo);
		return produtoNovo;
	}

	@WebframeworkGetMethod("/teste")
	public String teste() {
		return "Testes";
	}

	@WebframeworkGetMethod("/injected")
	public String chamadaCustom() {
		return iService.chamadaCustom("Hello injected");
	}

}