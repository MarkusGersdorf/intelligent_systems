package de.uol.is.tat;

/**
 * This class represents a field
 *
 * @author Thomas Cwil
 * @version 0.1
 */
public class Field implements IField {
    protected IField.field_type fieldType;
    protected int borderLimit;

    /**
     * Simple constructor which assigns the passed FieldType to the FieldType variable.
     *
     * @param fieldType Describes what type the field is.
     */
    public Field(IField.field_type fieldType) {
        this.fieldType = fieldType;
    }

	@Override
	public field_type getFieldType() {
		return this.fieldType;
	}

	@Override
	public void setFieldType(field_type ft) {
		this.fieldType = ft;
	}

	@Override
	public int getBorderLimit() {
		return this.borderLimit;
	}

	@Override
	public void setBorderLimit(int l) {
		this.borderLimit = l;
	}
}
