package de.uol.is.tat;

public class Field implements IField {
    protected IField.field_type field_type;
    protected int border_limit;

    public Field(IField.field_type ft)
    {
        this.field_type = ft;
    }

    @Override
    public field_type get_field_type() {
        return this.field_type;
    }

    @Override
    public void set_field_type(IField.field_type ft) {
        this.field_type = ft;
    }

    @Override
    public IField[][] initializeField(IField[][] f) {
        int x = f.length;
        return f;
    }

    @Override
    public void set_border_limit(int l) {
        border_limit = l;
    }

    @Override
    public int get_border_limit() {
        return border_limit;
    }
}
