package GameStore.GameStore.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model,HttpSession session) {
		session.setAttribute("rp", "index");
		return new ModelAndView("index");
	}
	@RequestMapping(name = "index", value="/index", method = RequestMethod.POST)
	public ModelAndView post(ModelMap model, @RequestParam Map<String,String>
	allParam) {
		return new ModelAndView("index");
	}
}
