/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

/**
 *
 * @author tnowicki
 */
public class AbstractDAO {

    protected Logger logger = Logger.getLogger(AbstractDAO.class);
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
