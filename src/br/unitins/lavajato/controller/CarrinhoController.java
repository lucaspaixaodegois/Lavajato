package br.unitins.lavajato.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.lavajato.application.Session;
import br.unitins.lavajato.application.Util;
import br.unitins.lavajato.dao.ServicoDAO;
import br.unitins.lavajato.dao.VendaDAO;
import br.unitins.lavajato.model.ItemVenda;
import br.unitins.lavajato.model.Servico;
import br.unitins.lavajato.model.Usuario;
import br.unitins.lavajato.model.Venda;

@Named
@RequestScoped
public class CarrinhoController  implements Serializable {

	private static final long serialVersionUID = 8149999991497336066L;

	private Venda venda;
	
	public void remover(int id) {
		// implementar
	}
	
	public void finalizar() {
		getVenda().setCliente("Joao");
		getVenda().setUsuario((Usuario)Session.getInstance().getAttribute("usuarioLogado"));
		VendaDAO dao = new VendaDAO();
		dao.create(getVenda());
	}
	
	public Venda getVenda() {
		if (venda == null) {
			venda = new Venda();
		}
		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = 
				(List<ItemVenda>)Session.getInstance().getAttribute("carrinho");
		
		if (carrinho != null)
			venda.setListaItemVenda(carrinho);
		else
			venda.setListaItemVenda(new ArrayList<ItemVenda>());
		
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public void adicionar(int id) {
		// pesquisa o servico selecionado
		ServicoDAO dao = new ServicoDAO();
		Servico servico = dao.findById(id);
		
		// verifica se existe o carrinho na sessao
		if (Session.getInstance().getAttribute("carrinho") == null) {
			// adiciona o carrinho na sessao
			Session.getInstance().setAttribute("carrinho", new ArrayList<ItemVenda>());
		}
		// busca o carrinho da sessao
		List<ItemVenda> carrinho =  
				(List<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		
		// cria um item de venda
		ItemVenda item = new ItemVenda();
		item.setServico(servico);
		item.setValor(servico.getValor());
		
		// adiciona o item no objeto de referencia do carrinho
		carrinho.add(item);
		
		// atualiza o carrinho
		Session.getInstance().setAttribute("carrinho", carrinho);
		
		Util.addMessageError("Adicionado com Sucesso! ");
	}

	
}
