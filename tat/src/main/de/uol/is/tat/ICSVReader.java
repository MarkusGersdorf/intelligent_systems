package de.uol.is.tat;

public interface ICSVReader {
    IField[][] convert_csv_to_fields(String path);
}
