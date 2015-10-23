package de.fischzegel.viszegel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Produkt")

/*
 * These are product constants set into the database by the Zegels.
 */
public class ProductConstant extends ProductBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7747450847117833931L;


}
