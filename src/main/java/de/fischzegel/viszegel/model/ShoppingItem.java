/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author tnowicki
 */
@Entity
@Table(name = "Rechnungsprodukt")
public class ShoppingItem extends AbstractModel{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "rechnungs_id")
    private Bill bill;
    
    @Column(name = "Liefertext")
    private String delivery_text;

    /**
     * @return the delivery_text
     */
    public String getDelivery_text() {
        return delivery_text;
    }

    /**
     * @param delivery_text the delivery_text to set
     */
    public void setDelivery_text(String delivery_text) {
        this.delivery_text = delivery_text;
    }

    /**
     * @return the bill
     */
    public Bill getBill() {
        return bill;
    }

    /**
     * @param bill the bill to set
     */
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
