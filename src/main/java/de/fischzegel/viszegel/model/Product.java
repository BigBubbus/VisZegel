/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author tnowicki
 */
@Entity
@Table(name = "Produkt")
public class Product extends AbstractModel {

    /**
     * @return the shopi
     */
    public List<ShoppingItem> getShopi() {
        return shopi;
    }

    /**
     * @param shopi the shopi to set
     */
    public void setShopi(List<ShoppingItem> shopi) {
        this.shopi = shopi;
    }
    @Column(name = "product_id", unique = false, nullable = false)
    private int product_id;
    
    @Column(name = "Beschreibung")
    private String description;
    @Column(name = "Preis")
    private BigDecimal price;
    @Column(name = "Gültig")
    private boolean valid;
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "BTWKategorie")
    private String btwCategory;

    @Column(name = "Einheit")
    private String unitType;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    @JsonManagedReference
    private List<ShoppingItem> shopi = new ArrayList<>();

    /**
     * @return the product_id
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * @return the btwCategory
     */
    public String getBtwCategory() {
        return btwCategory;
    }

    /**
     * @param btwCategory the btwCategory to set
     */
    public void setBtwCategory(String btwCategory) {
        this.btwCategory = btwCategory;
    }

    /**
     * @return the unitType
     */
    public String getUnitType() {
        return unitType;
    }

    /**
     * @param unitType the unitType to set
     */
    public void setUnitType(String unitType) {
        this.unitType = unitType;
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
