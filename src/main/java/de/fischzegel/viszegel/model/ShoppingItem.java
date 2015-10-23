/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author tnowicki
 */
@Entity
@Table(name = "Rechnungsprodukt")
public class ShoppingItem extends AbstractModel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5449445995914352882L;


	@Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    
    @ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "rechnungs_id")
    @JsonBackReference
    private Bill bill;
    
    @Column(name = "Liefertext")
    private String delivery_text;
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private ProductVariable productVariable;

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

    /**
     * @return the ProductBase
     */
    public ProductVariable getProduct() {
        return productVariable;
    }

    /**
     * @param ProductBase the ProductBase to set
     */
    public void setProduct(ProductVariable productVariable) {
        this.productVariable =  productVariable;
    }
}
