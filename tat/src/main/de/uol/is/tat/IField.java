package de.uol.is.tat;

public interface IField {
    enum field_type {
        BORDER, EMPTY, TREE, TENT, BLOCKED
    }

    field_type get_field_type();

    void set_field_type(field_type ft);

    IField[][] initializeField(IField[][] f);
}
