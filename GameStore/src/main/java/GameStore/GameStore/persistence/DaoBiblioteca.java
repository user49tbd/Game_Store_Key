package GameStore.GameStore.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import GameStore.GameStore.model.BibliotecaModel;

@Repository
public class DaoBiblioteca {
	@Autowired
	GenericDao gd;
	
	public List<BibliotecaModel> BLBiblioteca(BibliotecaModel db,String op) throws ClassNotFoundException, SQLException {
		List<BibliotecaModel> jogols = new ArrayList<>();
		Connection c = gd.getC();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("SELECT B.USRNOME,B.TITULO,B.ANO,B.GEN,B.JKEY ");
		sb1.append("FROM BIBLIOTECA B WHERE B.USRNOME = ? ");
		if(op == "B") {
			sb1.append("AND B.TITULO LIKE '%'+?+'%' ");
		}
		
		PreparedStatement ps = c.prepareStatement(sb1.toString());
		ps.setString(1, db.getUsr());
		if(op=="B") {
			ps.setString(2, db.getTitulo());
		}
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			BibliotecaModel bmr = new BibliotecaModel();
			bmr.setTitulo(rs.getString("TITULO"));
			bmr.setAno(rs.getInt("ANO"));
			bmr.setGen(rs.getString("GEN"));
			bmr.setKey(rs.getString("JKEY"));
			jogols.add(bmr);
		}
		return jogols;
	}
}
