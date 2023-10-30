package GameStore.GameStore.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import GameStore.GameStore.model.BibliotecaModel;
import GameStore.GameStore.persistence.DaoBiblioteca;

@Controller
public class BibliotecaController {
	@Autowired
	DaoBiblioteca db;
	@RequestMapping(name = "biblioteca", value = "/biblioteca", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model,HttpSession session) {
		//session.setAttribute("rp", "index");
		return new ModelAndView("biblioteca");
	}
	@RequestMapping(name = "biblioteca", value="/biblioteca", method = RequestMethod.POST)
	public ModelAndView post(ModelMap model, @RequestParam Map<String,String>
	allParam,HttpSession session) {
		String op = allParam.get("bt");
		String title = allParam.get("title");
		BibliotecaModel bm = new BibliotecaModel();
		List<BibliotecaModel> lstbm = new ArrayList<>();
		try {
			if(op.equalsIgnoreCase("Buscar")) {
				bm.setUsr(session.getAttribute("usr").toString());
				bm.setTitulo(title);
				op = "B";
				lstbm = db.BLBiblioteca(bm, op);
			}
			if(op.equalsIgnoreCase("Listar")) {
				bm.setUsr(session.getAttribute("usr").toString());
				bm.setTitulo(title);
				op = "L";
				lstbm = db.BLBiblioteca(bm, op);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			model.addAttribute("lstJ",lstbm);
		}
		return new ModelAndView("biblioteca");
	}
}
