/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author tnowicki
 */
@Entity
@Table(name = "Rechnungen")
public class Bill extends AbstractModel {
    public static enum paymentMethods {

        PayPal,Kreditkarte
    }
    @Id
    @GeneratedValue
    @Column(name = "bill_id", unique = true, nullable = false)
    private int bill_id;

    @Column(name = "Bezahlart")
    private paymentMethods payment_method;
    
    
    @Column(name = "Datum")
    private String date;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bill")
    private List<ShoppingItem> shopping_items = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "kunden_id")
    private Customer cus_bill;

    /**
     * @return the payment_method
     */
    public paymentMethods getPayment_method() {
        return payment_method;
    }

    /**
     * @param payment_method the payment_method to set
     */
    public void setPayment_method(paymentMethods payment_method) {
        this.payment_method = payment_method;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the shopping_items
     */
    public List<ShoppingItem> getShopping_items() {
        return shopping_items;
    }

    /**
     * @param shopping_items the shopping_items to set
     */
    public void setShopping_items(List shopping_items) {
        this.shopping_items = shopping_items;
    }

    /**
     * @return the bill_id
     */
    public int getBill_id() {
        return bill_id;
    }

    /**
     * @param bill_id the bill_id to set
     */
    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    /**
     * @return the cus_bill
     */
    public Customer getCus_bill() {
        return cus_bill;
    }

    /**
     * @param cus_bill the cus_bill to set
     */
    public void setCus_bill(Customer cus_bill) {
        this.cus_bill = cus_bill;
    }
}