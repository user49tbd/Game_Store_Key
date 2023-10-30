package GameStore.GameStore.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import GameStore.GameStore.model.ContaModel;

@Repository
@Primary
public class DaoConta {
	@Autowired
	GenericDao gd;
	
	public ContaModel ContaConf(ContaModel cm) throws ClassNotFoundException, SQLException {
		Connection c = gd.getC();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("SELECT c.NOMEUSR,C.EMAIL,C.SENHA,C.TIPO FROM CONTA C WHERE C.NOMEUSR = ? ");
		sb1.append("AND C.SENHA = ? AND C.TIPO = ? AND C.CSTATUS = 'V'");
		PreparedStatement ps = c.prepareStatement(sb1.toString());
		ps.setString(1, cm.getNome());
		ps.setString(2, cm.getSenha());
		ps.setString(3, cm.getTipo());
		ResultSet rs = ps.executeQuery();
		ContaModel cmr = new ContaModel();
		if(rs.next()) {
			cmr.setNome(rs.getString("NOMEUSR"));
			cmr.setEmail(rs.getString("EMAIL"));
			cmr.setSenha(rs.getString("SENHA"));
			cmr.setTipo(rs.getString("TIPO"));
			}
		return cmr;
	}
	public String criarConta(ContaModel cm) throws ClassNotFoundException, SQLException {
		Connection c = gd.getC();
		CallableStatement cs = c.prepareCall("{call P_ISRCONTA (?,?,?,?)}");
		cs.setString(1, cm.getNome());
		cs.setString(2, cm.getEmail());
		cs.setString(3, cm.getSenha());
		cs.setString(4, cm.getTipo());
		
		ResultSet rs = cs.executeQuery();
		String resposta="";
		if(rs.next()) {
			resposta = rs.getString("MENSAGEM");
		}
		//cs.registerOutParameter(5, java.sql.Types.VARCHAR);
		//cs.execute();
		//String wrd = cs.getString(5);
		cs.close();
		System.out.println(resposta);
		return resposta;
	}
	public String upDelConta(ContaModel cm, String op) throws ClassNotFoundException, SQLException {
		Connection c = gd.getC();
		CallableStatement cs = c.prepareCall("{call OP_UPDELCONTA (?,?,?,?,?)}");
		cs.setString(1, op);
		cs.setString(2, cm.getNome());
		cs.setString(3, cm.getEmail());
		cs.setString(4, cm.getSenha());
		cs.setString(5, cm.getTipo());
		ResultSet rs = cs.executeQuery();
		String resposta="";
		if(rs.next()) {
			resposta = rs.getString("MENSAGEM");
		}
		return resposta;
	}
}
