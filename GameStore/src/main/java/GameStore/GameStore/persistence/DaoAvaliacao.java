package GameStore.GameStore.persistence;

//import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import GameStore.GameStore.model.AvaliacaoModel;

@Repository
@Primary
public class DaoAvaliacao {
	@Autowired
	GenericDao gd;
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
}
