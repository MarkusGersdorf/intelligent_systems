package de.uol.is.tat;

import lombok.Data;

/**
 * This class represents a field
 *
 * @author Thomas Cwil
 * @version 0.1
 */
@Data
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
}
