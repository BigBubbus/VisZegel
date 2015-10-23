/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author tnowicki
 */
@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class ProductBase extends AbstractModel {

	private static final long serialVersionUID = -8636670412904181908L;


    @Column(name = "product_id", unique = false, nullable = false)
    protected int product_id;
    
    @Column(name = "Beschreibung")
    protected String description;
    @Column(name = "Preis")
    protected BigDecimal price;
    @Column(name = "Gültig")
    protected boolean valid;
    @Id
    
    @TableGenerator(name = "hibernate_sequences", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate_sequences")
    @Column(name = "id")
    protected int id;

    @Column(name = "BTWKategorie")
    protected String btwCategory;

    @Column(name = "Einheit")
    protected String unitType;


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
