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

//import GameStore.GameStore.model.AvaliacaoModel;
//import GameStore.GameStore.model.BibliotecaModel;
//import GameStore.GameStore.model.contaModel;
import GameStore.GameStore.model.JogoModel;

@Repository
public class DaoJogo {
	@Autowired
	GenericDao gd;
	public List<String> getJlistM(String title,String scope) throws ClassNotFoundException, SQLException{
		List<String> ls = new ArrayList<>();
		Connection c = gd.getC();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("SELECT J.TITULO+' | '+CAST(J.ANO AS VARCHAR(04)) AS VAL FROM JOGO J ");
		if(scope.equalsIgnoreCase("ALL")) {
			sb1.append("WHERE J.TITULO LIKE '%'+?+'%' AND J.JSTATUS = 'V'");
		}else {
			sb1.append("WHERE J.NOMEUSR = ? AND J.JSTATUS = 'V'");
		}
		PreparedStatement ps = c.prepareStatement(sb1.toString());
		ps.setString(1, title);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ls.add(rs.getString("VAL"));
		}
		return ls;
	}
	/*
	public List<AvaliacaoModel> getAvaliacoes(String title, int ano) throws ClassNotFoundException, SQLException{
		List<AvaliacaoModel> ls = new ArrayList<>();
		Connection c = gd.getC();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("SELECT AV.USRNOME,AV.TITULO,AV.ANO,AV.NOTA,AV.DESCRICAO FROM AVALIACAO AV ");
		sb1.append("WHERE AV.TITULO = ? AND AV.ANO = ?");
		PreparedStatement ps = c.prepareStatement(sb1.toString());
		ps.setString(1, title);
		ps.setInt(2, ano);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			AvaliacaoModel avm = new AvaliacaoModel();
			avm.setUsr(rs.getString("USRNOME"));
			avm.setTitulo(rs.getString("TITULO"));
			avm.setAno(rs.getInt("ANO"));
			avm.setNota(rs.getDouble("NOTA"));
			avm.setDescricao(rs.getString("DESCRICAO"));
			
			ls.add(avm);
		}
		return ls;
	}
	*/
	public JogoModel busca(JogoModel jm) throws SQLException, ClassNotFoundException {
		Connection c = gd.getC();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("SELECT J.NOMEUSR,J.TITULO,J.DESCRICAO,J.ANO FROM JOGO J ");
		sb1.append("WHERE J.TITULO = ? AND J.ANO = ? AND J.JSTATUS = 'V'");
		PreparedStatement ps = c.prepareStatement(sb1.toString());
		ps.setString(1, jm.getTitulo());
		ps.setInt(2, jm.getAno());
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			jm.setDescricao(rs.getString("DESCRICAO"));
			jm.setPublicadora(rs.getString("NOMEUSR"));
		}
		return jm;
	}
	public Double getMed(JogoModel jm) throws ClassNotFoundException, SQLException {
		Connection c = gd.getC();
		CallableStatement cs = c.prepareCall("{call CALCMED (?,?)}");
		cs.setString(1, jm.getTitulo());
		cs.setInt(2, jm.getAno());
		ResultSet rs = cs.executeQuery();
		double med=0;
		if(rs.next()) {
			med = rs.getInt("MED");
		}
		cs.close();
		return med;
	}
	public List<String> getGen() throws ClassNotFoundException, SQLException{
		List<String> ls = new ArrayList<>();
		Connection c = gd.getC();
		String sql="SELECT G.NOMEGEN FROM GENERO G";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ls.add(rs.getString("NOMEGEN"));
		}
		return ls;
	}
	public String opjogoM(JogoModel jm,String op) throws ClassNotFoundException, SQLException{
		System.out.println(jm.getPublicadora()+" - "+jm.getTitulo()+" - "
				+jm.getAno()+" - "+jm.getCgen()+" - "+jm.getDescricao()+" op "+op);
		Connection c = gd.getC();
		CallableStatement cs = c.prepareCall("{call OP_JOGOM (?,?,?,?,?,?)}");
		cs.setString(1, op);
		cs.setString(2, jm.getPublicadora());
		cs.setString(3, jm.getTitulo());
		cs.setString(4, jm.getDescricao());
		cs.setInt(5, jm.getAno());
		cs.setString(6, jm.getCgen());
		
		ResultSet rs = cs.executeQuery();
		String resposta="";
		if(rs.next()) {
			resposta = rs.getString("MENSAGEM");
		}
		cs.close();
		return resposta;
	}
	public List<JogoModel> getjogoMBL(JogoModel jm,String op) throws ClassNotFoundException, SQLException {
		List<JogoModel> ls = new ArrayList<>();
		Connection c = gd.getC();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("SELECT J.NOMEUSR,J.TITULO,J.DESCRICAO,J.ANO FROM JOGO J ");
		sb1.append("WHERE J.NOMEUSR = ? AND J.JSTATUS = 'V'");
		if(op == "B") {
			sb1.append(" AND J.TITULO = ? AND J.ANO = ?");
		}
		
		PreparedStatement ps = c.prepareStatement(sb1.toString());
		ps.setString(1, jm.getPublicadora());
		if(op=="B") {
			ps.setString(2, jm.getTitulo());
			ps.setInt(3, jm.getAno());
		}
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			JogoModel jmr = new JogoModel();
			jmr.setPublicadora(rs.getString("NOMEUSR"));
			jmr.setTitulo(rs.getString("TITULO"));
			jmr.setAno(rs.getInt("ANO"));
			//jmr.setCgen(rs.getString(""));
			jmr.setDescricao(rs.getString("DESCRICAO"));
			jmr.setCgen(getGenJogoM(jmr,"F"));
			ls.add(jmr);
		}
		return ls;
	}
	/*
	public String getGenJogoM(jogoModel jm) throws ClassNotFoundException, SQLException{
		String gens="";
		Connection c = gd.getC();
		String sql="SELECT JG.NOMEGEN FROM JOGOGEN JG WHERE JG.TITULO=? AND JG.ANO = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, jm.getTitulo());
		ps.setInt(2, jm.getAno());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			gens +=(rs.getString("NOMEGEN"))+" ";
		}
		return gens;
	}
	*/
	public String getGenJogoM(JogoModel jm,String bar) throws ClassNotFoundException, SQLException{
		String gens="";
		Connection c = gd.getC();
		String sql="SELECT JG.NOMEGEN FROM JOGOGEN JG WHERE JG.TITULO=? AND JG.ANO = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, jm.getTitulo());
		ps.setInt(2, jm.getAno());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(bar.equalsIgnoreCase("F")) {
				gens +=(rs.getString("NOMEGEN"))+" ";
			}
			if(bar.equalsIgnoreCase("V")) {
				gens +=(rs.getString("NOMEGEN"))+"|";
			}
		}
		return gens;
	}
	/*
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
		String wrd="";
		if(rs.next()) {
			wrd = rs.getString("MENSAGEM");
		}
		cs.close();
		return wrd;
	}
	*/
}
