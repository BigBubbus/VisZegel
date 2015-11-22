package de.fischzegel.viszegel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Entity
@Table(name = "Kunden")
public class Customer extends AbstractModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3365576486812426843L;
	@Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    private String name;
    private String extra_rules;
    private String adress;
    private int house_number;
    private String postcode;
    private String location;
    private String country;
    private String email;
    private String website;
    private String btw_number;
    private String btw_number_final;
    /*
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true, mappedBy = "cus_bill")
    @JsonManagedReference
    private List<Bill> bills = new ArrayList<>();
*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtra_rules() {
        return extra_rules;
    }

    public void setExtra_rules(String extra_rules) {
        this.extra_rules = extra_rules;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getHouse_number() {
        return house_number;
    }

    public void setHouse_number(int house_number) {
        this.house_number = house_number;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBtw_number() {
        return btw_number;
    }

    public void setBtw_number(String btw_number) {
        this.btw_number = btw_number;
    }

    public String getBtw_number_final() {
        return btw_number_final;
    }

    public void setBtw_number_final(String btw_number_final) {
        this.btw_number_final = btw_number_final;
    }

    /**
     * @return the bills
    
    public List<Bill> getBills() {
        return bills;
    }

    /**
     * @param bills the bills to set
    
    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
     */

}
