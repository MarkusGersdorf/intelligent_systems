package de.uol.is.tat;

/**
 * Interface for a Field class
 */
public interface IField {
    enum field_type {
        BORDER, EMPTY, TREE, TENT, BLOCKED
    }

    field_type getFieldType();

    void setFieldType(field_type ft);

    int getBorderLimit();

    void setBorderLimit(int l);
}
