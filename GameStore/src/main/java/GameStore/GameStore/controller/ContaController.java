package GameStore.GameStore.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import GameStore.GameStore.model.ContaModel;
import GameStore.GameStore.persistence.DaoJogador;
import GameStore.GameStore.persistence.DaoPublicadora;

@Controller
public class ContaController {
	@Autowired
	DaoJogador dj;
	@Autowired
	DaoPublicadora dp;
	
	@RequestMapping(name = "logger", value = "/logger", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model,HttpSession session, @RequestParam Map<String,String>
	allParam) {
		String bt = allParam.get("bt");
		String op = "";
		if(bt != null) {
		if(bt.equalsIgnoreCase("Criar conta")) {
			op = "C";
			model.addAttribute("op", op);
		}
		if(bt.equalsIgnoreCase("Entrar")) {
			op = "E";
			model.addAttribute("op", op);
		}
		if(bt.equalsIgnoreCase("Manter Conta")) {
			op = "M";
			model.addAttribute("op", op);
		}
		if(bt.equalsIgnoreCase("Desconectar")) {
			session.setAttribute("usr", null);
		}
		if(bt.equalsIgnoreCase("voltar")) {
			String rp = session.getAttribute("rp").toString();
			session.setAttribute("rp", "index");
			return new ModelAndView(rp);
		}
		}
		return new ModelAndView("logger");
	}
	@RequestMapping(name = "logger", value="/logger", method = RequestMethod.POST)
	public ModelAndView post(ModelMap model, @RequestParam Map<String,String>
	allParam,HttpSession session) {
		String ap = allParam.get("bt");
		String name = allParam.get("ln");
		String email = allParam.get("lm");
		String senha = allParam.get("ls");
		String tipo = allParam.get("ltp");
		String err="";
		ContaModel cm = new ContaModel();
		try {
			if(ap != null && ap.equalsIgnoreCase("Loggar")) {
				cm.setNome(name);
				cm.setSenha(digest(senha));
				cm.setTipo(tipo);
				//cm = dg.ContaConf(cm);
				//----------------
				if(cm.getTipo().equalsIgnoreCase("P")) {
					cm = dp.ContaConf(cm);
				}else {
					cm = dj.ContaConf(cm);
				}
				//----------------
				err="ERRO AO REALIZAR LOGIN";
				session.setAttribute("usr",null);
				session.setAttribute("tpusr",null);
				if(cm.getNome() != null) {
					System.out.println("VALORES PASSANDO O USR E TPUSR");
					session.setAttribute("usr",cm.getNome());
					session.setAttribute("tpusr",cm.getTipo());
					System.out.println(session.getAttribute("usr"));
					System.out.println(session.getAttribute("tpusr"));
					err="LOGIN REALIZADO COM SUCESSO";
				}
			}
			if(ap.equalsIgnoreCase("Criar conta")
					|| ap.equalsIgnoreCase("Atualizar")) {
					int up=0;
					int lo=0;
					for(int i =0;i<senha.length();i++) {
						if(Character.isUpperCase(senha.charAt(i))) {
							up+=1;
						}
						if(Character.isLowerCase(senha.charAt(i))) {
							lo+=1;
						}
					}
					if(up == 0 || lo==0) {
						err = "SENHA SEM DIVERSIDADE NO TAMANHO DE CARACTERES";
						model.addAttribute("emsg", err);
						return new ModelAndView("logger");
					}
			}
			if(ap != null && ap.equalsIgnoreCase("Criar conta")) {
				cm.setNome(name);
				cm.setEmail(email);
				cm.setSenha(digest(senha));
				cm.setTipo(tipo);
				//String nv = dg.criarConta(cm);
				//--------------
				String nv ="";
				if(cm.getTipo().equalsIgnoreCase("P")) {
					nv = dp.criarConta(cm);
				}else {
					nv = dj.criarConta(cm);
				}
				//--------------
				err = nv;
				if(err.equalsIgnoreCase("CADASTRADO") && err != null) {
					session.setAttribute("usr",cm.getNome());
					session.setAttribute("tpusr",cm.getTipo());
				}
			}
			if(ap != null && (ap.equalsIgnoreCase("Atualizar")
					|| ap.equalsIgnoreCase("Excluir"))) {
				cm.setNome(session.getAttribute("usr").toString());
				cm.setEmail(email);
				cm.setSenha(digest(senha));
				cm.setTipo(session.getAttribute("tpusr").toString());
				String op = "A";
				if(ap.equalsIgnoreCase("Excluir")) {
					op = "D";
					//String nv = dg.upDelConta(cm, op);
					//--------------
					String nv = "";
					if(cm.getTipo().equalsIgnoreCase("P")) {
						nv = dp.upDelConta(cm, op);
					}else {
						nv = dj.upDelConta(cm, op);
					}
					//--------------
					err = nv;
					session.setAttribute("usr",null);
					session.setAttribute("tpusr",null);
				}
				else {
					//String nv = dg.upDelConta(cm, op);
					String nv = "";
					if(cm.getTipo().equalsIgnoreCase("P")) {
						nv = dp.upDelConta(cm, op);
					}else {
						nv = dj.upDelConta(cm, op);
					}
					err = nv;
				}
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			model.addAttribute("emsg", err);
		}
		if(ap.equalsIgnoreCase("Excluir")) {
			return new ModelAndView("index");
		}
		return new ModelAndView("logger");
	}
	public String digest(String pass) {
		String hex="";
		try {
			 MessageDigest algorithm = MessageDigest.getInstance("MD5");
			 byte messageDigest[] = algorithm.digest(pass.getBytes("UTF-8"));
			 StringBuffer sb1 = new StringBuffer();
			 for (byte b : messageDigest) {
	              sb1.append(String.format("%02X", 0xFF & b));
			 }
			 hex = sb1.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hex;
	}
}
