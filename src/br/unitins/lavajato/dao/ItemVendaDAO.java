package br.unitins.lavajato.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.lavajato.application.Util;
import br.unitins.lavajato.model.ItemVenda;
import br.unitins.lavajato.model.Servico;
import br.unitins.lavajato.model.Venda;

public class ItemVendaDAO extends DAO<ItemVenda>  {
	

	public List<ItemVenda> findAll(Venda venda) {
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return null;
		}
		
		List<ItemVenda> listaItemVenda = new ArrayList<ItemVenda>();
		
		PreparedStatement stat = null;
	
		try {
			stat = getConnection().prepareStatement("SELECT  "+
													" i.id, "+
													" i.valor, "+
													" i.idvenda, "+
													" i.idservico, "+
													" s.descricao, "+
													" s.valor as valorservico "+
													"FROM "+
													" itemvenda i, "+
													" servico s "+
													"WHERE "+
													" i.idservico = s.id AND "+
													" i.idvenda = ? ");
			stat.setInt(1, venda.getId());
			
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				ItemVenda itemVenda = new ItemVenda();
				itemVenda.setId(rs.getInt("id"));
				itemVenda.setValor(rs.getDouble("valor"));
				itemVenda.setVenda(venda);
				itemVenda.setServico(new Servico());
				itemVenda.getServico().setId(rs.getInt("idservico"));
				itemVenda.getServico().setDescricao(rs.getString("descricao"));
				itemVenda.getServico().setValor(rs.getDouble("valorservico"));
				
				listaItemVenda.add(itemVenda);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Util.addMessageError("Falha ao consultar o Banco de Dados.");
			listaItemVenda = null;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaItemVenda;
	}
	
	@Override
	public boolean create(ItemVenda obj) {
		boolean resultado = false;
		return resultado;
	}
	
	@Override
	public List<ItemVenda> findAll() {
		return null;
	}

	@Override
	public boolean update(ItemVenda obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemVenda findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
