package de.uol.is.tat;

public interface ICSVReader {
    IField[][] convertCsvToFields(String path);
}
