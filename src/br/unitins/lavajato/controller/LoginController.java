package br.unitins.lavajato.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.lavajato.application.Session;
import br.unitins.lavajato.application.Util;
import br.unitins.lavajato.dao.UsuarioDAO;
import br.unitins.lavajato.model.Usuario;

@Named
@RequestScoped
public class LoginController {

	private Usuario usuario;
	
	public void entrar() {
		UsuarioDAO dao = new UsuarioDAO();
		// gerando o hash da senha informada na tela de login
		String senhaEncriptada = Util.encrypt(getUsuario().getSenha());
		
		Usuario usuLogado = dao.findUsuario(getUsuario().getLogin(), senhaEncriptada);
		
		// comparando os dados da tela de login com o banco de dados
		if (usuLogado != null) {
			Session.getInstance().setAttribute("usuarioLogado", usuLogado);
			// login valido
			Util.redirect("template.xhtml");
		} else 
			Util.addMessageError("Usu�rio ou senha inv�lido.");
		
	}
	
	public void limpar() {
		setUsuario(null);
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
