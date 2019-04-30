package SimonSays.Dependencies;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reads CSV Files.
 * @Author Afaq Anwar
 * @Version 12/14/2018
 */
public class CSVReader {
    // Private Fields.
    private ArrayList<String> csvData;
    private String[] headers;
    private int columns;

    /**
     * Main Constructor.
     * Parses the CSV data, and initializes all fields.
     * @param file Any CSV File.
     */
    public CSVReader(File file) {
        read(file);
    }

    /**
     * Scans each line of the CSV, and adds it to csvData.
     * This method also initializes the headers String Array, and the number of columns.
     * @param file Any CSV File.
     */
    private void read(File file) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            csvData = new ArrayList<>();
            String currentLine = reader.readLine();
            headers = currentLine.split(",");
            columns = headers.length;
            while ((currentLine = reader.readLine()) != null) {
                csvData.add(currentLine);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public List<String> getAllData() { return this.csvData; }

    /**
     * @return A List of Strings, that are the headers of the CSV File.
     */
    public List<String> getColumnHeaders() {
        List<String> headerList = new ArrayList<>();
        Collections.addAll(headerList, headers);
        return headerList;
    }

    /**
     * Narrows down that csvData to a single column.
     * @param column Integer representing the column of desired data.
     *               *column is intended to start at 1.
     * @return List of Strings containing all data inputs for the specified column.
     */
    public List<String> getDataString(int column) {
        List<String> dataFromColumn = new ArrayList<>();
        for (String str : csvData) {
            String[] splitStr = str.split(",");
            dataFromColumn.add(splitStr[column - 1]);
        }
        return dataFromColumn;
    }

    /**
     * Narrows down that csvData to a single column.
     * @param column Integer representing the column of desired data.
     *               *column is intended to start at 1.
     * @return List of Integers containing all data inputs for the specified column.
     *               *Will not work if the column has data other than Integers.
     */
    public List<Integer> getDataInt(int column) {
        List<Integer> dataFromColumn = new ArrayList<>();
        for (String str : csvData) {
            String[] splitStr = str.split(",");
            dataFromColumn.add(Integer.parseInt(splitStr[column - 1]));
        }
        return dataFromColumn;
    }

    /**
     * Narrows down that csvData to a single column.
     * @param column Integer representing the column of desired data.
     *               *column is intended to start at 1.
     * @return List of Doubles containing all data inputs for the specified column.
     *               *Will not work if the column has data other than Doubles.
     */
    public List<Double> getDataDouble(int column) {
        List<Double> dataFromColumn = new ArrayList<>();
        for (String str : csvData) {
            String[] splitStr = str.split(",");
            dataFromColumn.add(Double.parseDouble(splitStr[column - 1]));
        }
        return dataFromColumn;
    }
}