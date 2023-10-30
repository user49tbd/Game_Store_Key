package GameStore.GameStore.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import GameStore.GameStore.model.AvaliacaoModel;

@Repository
public class DaoAvaliacaoJ extends DaoAvaliacao {
	@Autowired
	GenericDao gd;
	public String AvOps(AvaliacaoModel avm,String op) throws ClassNotFoundException, SQLException {
		Connection c = gd.getC();
		CallableStatement cs = c.prepareCall("{call OP_AVALIACAO (?,?,?,?,?,?)}");
		cs.setString(1, op);
		cs.setString(2, avm.getUsr());
		cs.setString(3, avm.getTitulo());
		cs.setInt(4, avm.getAno());
		cs.setDouble(5, avm.getNota());
		cs.setString(6, avm.getDescricao());
		
		//cs.registerOutParameter(7, java.sql.Types.VARCHAR);
		//cs.execute();
		//String wrd = cs.getString(7);
		ResultSet rs = cs.executeQuery();
		String resposta="";
		if(rs.next()) {
			resposta = rs.getString("MENSAGEM");
		}
		cs.close();
		return resposta;
	}
	public List<AvaliacaoModel> getAvaliacaoJ(AvaliacaoModel avm,String op) throws ClassNotFoundException, SQLException {
		List<AvaliacaoModel> ls = new ArrayList<>();
		Connection c = gd.getC();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("SELECT AV.USRNOME,AV.TITULO,AV.ANO,AV.NOTA,AV.DESCRICAO ");
		sb1.append("FROM AVALIACAO AV WHERE AV.USRNOME = ? ");
		if(op == "B") {
			sb1.append(" AND AV.TITULO = ? AND AV.ANO = ?");
		}
		
		
		PreparedStatement ps = c.prepareStatement(sb1.toString());
		ps.setString(1, avm.getUsr());
		if(op=="B") {
			ps.setString(2, avm.getTitulo());
			ps.setInt(3, avm.getAno());
		}
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			AvaliacaoModel avmr = new AvaliacaoModel();
			avmr.setUsr(rs.getString("USRNOME"));
			avmr.setTitulo(rs.getString("TITULO"));
			avmr.setAno(rs.getInt("ANO"));
			avmr.setNota(rs.getDouble("NOTA"));
			avmr.setDescricao(rs.getString("DESCRICAO"));
			ls.add(avmr);
		}
		return ls;
	}
}
