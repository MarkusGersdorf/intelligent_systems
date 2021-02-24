package de.uol.is.tat;

/**
 * Interface for a ICSVReader class
 */
public interface ICSVReader {
    IField[][] convertCsvToFields(String path);
}
