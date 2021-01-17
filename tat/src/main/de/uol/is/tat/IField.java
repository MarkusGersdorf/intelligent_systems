package de.uol.is.tat;

public interface IField {
    enum field_type {
        BORDER, EMPTY, TREE, TENT, BLOCKED
    }

    field_type getFieldType();

    void setFieldType(field_type ft);

    IField[][] initializeField(IField[][] f);

    int getBorderLimit();

    void setBorderLimit(int l);
}
