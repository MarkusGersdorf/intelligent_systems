package de.uol.is.shopScheduling;

/**
 * This class represents one resource a machine for example
 *
 * @author Thomas Cwill, Markus Gersdorf
 * @version 1.0
 */
public class Resource {
    public Resource(String string, Long id2) {
		this.name = string;
		this.id = id2;
	}
	private String name;
    private long id;
	public long getId() {
		return this.id;
	}
}
