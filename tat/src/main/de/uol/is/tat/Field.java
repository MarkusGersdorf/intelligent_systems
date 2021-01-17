package de.uol.is.tat;

/**
 * This class represents a field
 *
 * @author Thomas Cwill
 * @version 0.1
 */
public class Field implements IField {
    protected IField.field_type fieldType;
    protected int borderLimit;

    public Field(IField.field_type fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public field_type getFieldType() {
        return this.fieldType;
    }

    @Override
    public void setFieldType(IField.field_type fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public IField[][] initializeField(IField[][] fields) {
        // TODO: Whats up here? Is it right?
        int x = fields.length;
        return fields;
    }

    @Override
    public int getBorderLimit() {
        return this.borderLimit;
    }

    @Override
    public void setBorderLimit(int boarderLimit) {
        this.borderLimit = boarderLimit;
    }
}
