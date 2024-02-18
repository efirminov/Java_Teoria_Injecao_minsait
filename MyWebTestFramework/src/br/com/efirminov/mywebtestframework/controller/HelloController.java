package br.com.efirminov.mywebtestframework.controller;

import br.com.efirminov.mywebtestframework.model.Produto;
import br.com.efirminov.webframeword.annotations.WebframeworkBody;
import br.com.efirminov.webframeword.annotations.WebframeworkController;
import br.com.efirminov.webframeword.annotations.WebframeworkGetMethod;
import br.com.efirminov.webframeword.annotations.WebframeworkPostMethod;

@WebframeworkController
public class HelloController {

	@WebframeworkGetMethod("/hello")
	public String returnHelloWorld() {
		return "Hello world!!!";
	}

	@WebframeworkGetMethod("/produto")
	public Produto exibirPorduto() {
		Produto p = new Produto(1, "Nome1", 2000.0, "teste.jpg");
		return p;
	}

	@WebframeworkPostMethod("/produto")
	public String cadastrarProduto(@WebframeworkBody Produto produtoNovo) {
		System.out.println(produtoNovo);
		return "Produto cadastrado";
	}

	@WebframeworkGetMethod("/teste")
	public String teste() {
		return "Testes";
	}

}