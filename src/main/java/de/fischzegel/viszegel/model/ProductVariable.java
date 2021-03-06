package de.fischzegel.viszegel.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity

@Table(name = "Produkt_Rechnung_Log")

/*
 * These are product constants set into the database by the Zegels.
 */
public class ProductVariable extends ProductBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7216038917701689934L;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval=true, mappedBy = "productVariable")
    @JsonManagedReference
    protected List<ShoppingItem> shopi = new ArrayList<>();
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
    @Column(name = "Anzahl")
    private double amount = 1;
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
