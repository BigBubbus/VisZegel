/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import java.math.BigInteger;

import javax.transaction.Transactional;

/**
 *
 * @author tnowicki
 */
@Transactional
public class HibernateSequenceDAO extends AbstractDAOImpl {

    public BigInteger getCurrentId() {
        return (BigInteger) this.sessionFactory.getCurrentSession().createSQLQuery("SELECT sequence_next_hi_value FROM hibernate_sequences WHERE sequence_name = 'Rechnungen'").uniqueResult();
    }


}
