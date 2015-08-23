/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tnowicki
 *
 * This Controller settles everything required for authentication
 */
@Controller
public class AuthenticationController {

    private static final Logger logger = Logger.getLogger(AuthenticationController.class);

    @RequestMapping(value = "/protected**", method = RequestMethod.GET)
    public ModelAndView protectedPage() {
        logger.debug("PROTECTED");
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security 3.2.3 Hello World");
        model.addObject("message", "This is protected page - Only for Administrators !");
        model.setViewName("protected");
        return model;

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String superAdminPage(HttpServletRequest request, @RequestParam(value = "username", required = false) String user, @RequestParam(value = "password", required = false) String password, Model model) {
        logger.debug("LOGINNNNNN");
        logger.debug(user);
        logger.debug(password);
        if (user == null && password == null) {
            return "login";
        }
        model.addAttribute("username", user);
        model.addAttribute("studentid", password);
        model.addAttribute("loggedin", false);
        return "login";

    }


}


