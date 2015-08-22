/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.configuration;

import de.fischzegel.viszegel.controller.AuthenticationController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author tnowicki
 */
public class SecurityConfigTest {

    private MockMvc mockMvc;

    public SecurityConfigTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void loginTest() throws Exception {
        String user = "VisuserT";
        String password = "testingVis";
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");        
        mockMvc = standaloneSetup(new AuthenticationController()).setViewResolvers(viewResolver)
                .build();
        mockMvc.perform(get("/login").param("username",user).param("password",password))
                .andExpect(status().isOk())
                .andExpect(model().attribute("loggedin", false));
    }

}
