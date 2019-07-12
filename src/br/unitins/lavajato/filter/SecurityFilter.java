package br.unitins.lavajato.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.unitins.lavajato.model.Usuario;

//@WebFilter("/*")
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/faces/pages/*", "/faces/relatorio/*"})
public class SecurityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		// imprime o endereco da pagina
		String endereco = servletRequest.getRequestURI();
		System.out.println(endereco);
		if (endereco.equals("/Lavajato/faces/login.xhtml")) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpSession session = servletRequest.getSession(false);
		
		Usuario usuario = null;
		if (session != null)
			usuario = (Usuario) session.getAttribute("usuarioLogado");
		
		if (usuario == null) {
			((HttpServletResponse) response).sendRedirect("/Lavajato/faces/login.xhtml");
		}  else {
			for (String pagina : usuario.getPerfil().getPages()) {
				if (endereco.contains(pagina)) {
					// deixa a pagina ser concluida
					chain.doFilter(request, response);
					return;
				}
			}
			((HttpServletResponse) response).sendRedirect("/Lavajato/faces/acessonegado.xhtml");
			
		}
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
		System.out.println("SecurityFilter Iniciado.");
	}
	

}
