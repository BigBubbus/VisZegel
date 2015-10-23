package de.fischzegel.viszegel.daos.interfaces;

import java.io.Serializable;

public interface AbstractDAO <T, PK extends Serializable>	{
	/** Persist the newInstance object into database */
    PK create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    T read(PK id);

    /** Save changes made to a persistent object.  */
    void update(T transientObject);

    /** Remove an object from persistent storage in the database */
    void delete(T persistentObject);
}
