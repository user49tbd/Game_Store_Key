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

import GameStore.GameStore.model.BibliotecaModel;
import GameStore.GameStore.model.ChaveModel;
import GameStore.GameStore.model.JogoModel;

@Repository
public class DaoChaves {
	@Autowired
	GenericDao gd;
	public String IsrDelChave(String op,JogoModel jm,int qtd) throws ClassNotFoundException, SQLException {
		Connection c = gd.getC();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("{call ");
		if(op == "I") {
			sb1.append("P_ISRCHAVE ");
		}
		if(op == "D") {
			sb1.append("P_DELCHAVE ");
		}
		sb1.append("(?,?,?,?)}");
		CallableStatement cs = c.prepareCall(sb1.toString());
		cs.setString(1, jm.getPublicadora());
		cs.setString(2, jm.getTitulo());
		cs.setInt(3, jm.getAno());
		cs.setInt(4, qtd);
		
		//cs.registerOutParameter(5, java.sql.Types.VARCHAR);
		//cs.execute();
		//String wrd = cs.getString(5);
		ResultSet rs = cs.executeQuery();
		String wrd="";
		if(rs.next()) {
			wrd = rs.getString("MENSAGEM");
		}
		cs.close();
		return wrd;
		
	}
	public List<ChaveModel> BLChaves(String op,ChaveModel cm) throws ClassNotFoundException, SQLException {
		List<ChaveModel> ls = new ArrayList<>();
		Connection c = gd.getC();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("SELECT JK.TITULO,JK.ANO,JK.CHAVECOD,JK.STATUS FROM JKEY JK INNER JOIN ");
		sb1.append("JOGO J ON J.TITULO = JK.TITULO AND J.ANO = JK.ANO WHERE J.NOMEUSR = ? AND J.JSTATUS = 'V' ");
		if(op == "B") {
			sb1.append("AND JK.TITULO = ? AND JK.ANO = ? ");
		}
		
		PreparedStatement ps = c.prepareStatement(sb1.toString());
		ps.setString(1, cm.getUsr());
		if(op=="B") {
			ps.setString(2, cm.getTitulo());
			ps.setInt(3, cm.getAno());
		}
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ChaveModel cmr = new ChaveModel();
			cmr.setUsr(cm.getUsr());
			cmr.setTitulo(rs.getString("TITULO"));
			cmr.setAno(rs.getInt("ANO"));
			cmr.setChave(rs.getString("CHAVECOD"));
			cmr.setStatus(rs.getString("STATUS"));
			ls.add(cmr);
		}
		return ls;
	}
	public String rescueKey(BibliotecaModel bm) throws ClassNotFoundException, SQLException {
		Connection c = gd.getC();
		String sql = "{call ISRBIB (?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, bm.getUsr());
		cs.setString(2, bm.getTitulo());
		cs.setInt(3, bm.getAno());
		//cs.registerOutParameter(4, java.sql.Types.VARCHAR);
		///cs.execute();
		//String wrd = cs.getString(4);
		ResultSet rs = cs.executeQuery();
		String resposta="";
		if(rs.next()) {
			resposta = rs.getString("MENSAGEM");
		}
		cs.close();
		return resposta;
	}
}
