package invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 Copyright (c) 2010 - 2030 by ACI Worldwide Inc.
 All rights reserved.
 
 This software is the confidential and proprietary information
 of ACI Worldwide Inc ("Confidential Information").  You shall
 not disclose such Confidential Information and shall use it
 only in accordance with the terms of the license agreement
 you entered with ACI Worldwide Inc.
 */

/**
 * TODO
 *
 */
public class InvoiceGenerator {
	private String invoiceItemFile;
	private ArrayList<String> invoiceItems;
	private HashMap<String, InvoiceItem> generatedInvoice;
	private int vatUnit;
	private double total;
	private double vatTotal;
	
	/**
	 * 
	 * Constructs a new <code>InvoiceGenerator</code> instance.
	 */
	public InvoiceGenerator() {
		this.invoiceGenerator("../invoice/invoiceItems.txt", 14, 0.0, 0.0);
	}
	
	/**
	 * @param newInvoiceItemFile
	 * @param newVatUnit
	 * @param newTotal
	 */
	public void invoiceGenerator(String newInvoiceItemFile, int newVatUnit, double newTotal, double newVatTotal) {
		this.invoiceItems = new ArrayList<String>();
		this.generatedInvoice = new HashMap<String, InvoiceItem>();
		this.setInvoiceItemFile(newInvoiceItemFile);
		this.setVatUnit(newVatUnit);
		this.setTotal(newTotal);
		this.setVatTotal(newVatTotal);
	}
	
	/**
	 * @throws IOException
	 */
	public void retrieveInvoiceItems() throws IOException {
		
		System.out.println("Reading the invoiceItems file...");
		
		try {
			FileReader invoiceFile  = new FileReader(new File(this.getInvoiceItemFile()).getCanonicalPath());
			BufferedReader bufferReader = new BufferedReader(invoiceFile);
			
			String line = null;
			while ((line = bufferReader.readLine()) != null) {
				this.invoiceItems.add(line);
		}
		bufferReader.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 */
	public void calculateInvoice() {
		System.out.println("Calcuating invoice...");
		
		for(int i = 1; i < this.invoiceItems.size(); i++) {
			String[] row = this.invoiceItems.get(i).split(",");
			
			if(this.generatedInvoice.get(row[0]) == null) {
				InvoiceItem invoiceItem = new InvoiceItem();
				invoiceItem.setInvoiceItem(row[0], row[2], this.getVatUnit(), row[1]);
				invoiceItem.calculateTotals();
				this.generatedInvoice.put(invoiceItem.getProductID(), invoiceItem);
			}
			else {
				InvoiceItem invoiceItem = this.generatedInvoice.get(row[0]);
				invoiceItem.calculateTotals();
			}
		}
	}
	
	/**
	 * TODO
	 */
	public void incrementTotal(double vatAmount, double lineTotal) {
		this.vatTotal += vatAmount;
		this.total += lineTotal;
	}
	
	/**
	 * TODO
	 */
	public void displayHeader() {
		this.displayRow("Product Id", "Description", "Count", "Unit Price", "Vat", "Line Total");
	}
	
	/**
	 * TODO
	 */
	public void displayItem() {
		SortedSet<String> keys = new TreeSet<String>(this.generatedInvoice.keySet());
		
		for (String key : keys) {
			InvoiceItem invoiceItem = this.generatedInvoice.get(key);
			this.incrementTotal(Double.parseDouble(invoiceItem.getVatTotal()), Double.parseDouble(invoiceItem.getLineTotal()));
			this.displayRow(invoiceItem.getProductID(), 
								invoiceItem.getDescription(), 
								invoiceItem.getCount(), 
								insertRandSign(invoiceItem.getUnitPrice()), 
								insertRandSign(invoiceItem.getVatTotal()), 
								insertRandSign(invoiceItem.getLineTotal()));
		}
	}
	
	public void diplayTotal() {
		System.out.println("");
		System.out.println("============================================================================");
		System.out.println("");
		this.displayRow("Total", "", this.insertRandSign(this.getvatTotal()), "", "", this.insertRandSign(this.getTotal()));
	}
	
	/**
	 * @param value
	 * @return value
	 */
	public String insertRandSign(String value) {
		return "R " + value;
	}
	
	/**
	 * @param column1
	 * @param column2
	 * @param column3
	 * @param column4
	 * @param column5
	 * @param column6
	 */
	public void displayRow(String column1, String column2, String column3, String column4, String column5, String column6) {
		String line = "" + this.padColumn(column1, 12) + " "; 
		line = line + this.padColumn(column2, 25)+" ";
		line = line + this.padColumn(column3, 10) + " ";
		line = line + this.padColumn(column4, 10) + " ";
		line = line + this.padColumn(column5, 10) + " "; 
		line = line + this.padColumn(column6, 10) + " "; 
		System.out.println(line);
	}
	
	/**
	 * @param column
	 * @param width
	 * @return column, 
	 */
	public String padColumn(String column, int width) {
		return String.format("%-"+ width + "s", column);
	}

	/**
	 * @return total
	 */
	public String getTotal() {
		return String.format("%.2f", this.total);
	}
	
	/**
	 * @return vatTotal
	 */
	public String getvatTotal() {
		return String.format("%.2f", this.vatTotal);
	}
	
	/**
	 * @return
	 */
	private String getInvoiceItemFile()
	{
		return this.invoiceItemFile;
	}
	
	/**
	 * @return
	 */
	private int getVatUnit() {
		return this.vatUnit;
	}

	/**
	 * @param newInvoiceItemFile
	 */
	public void setInvoiceItemFile(String newInvoiceItemFile) {
		this.invoiceItemFile = newInvoiceItemFile;
	}
	
	/**
	 * @param newVatUnit
	 */
	public void setVatUnit(int newVatUnit) {
		this.vatUnit = newVatUnit;
	}

	/**
	 * @param newTotal
	 */
	public void setTotal(double newTotal) {
		this.total = newTotal;
	}
	
	public void setVatTotal(double newVatTotal) {
		this.vatTotal = newVatTotal;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
		try
		{
			invoiceGenerator.retrieveInvoiceItems();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			System.exit(0);
		}
		
		invoiceGenerator.calculateInvoice();
		
		invoiceGenerator.displayHeader();
		invoiceGenerator.displayItem();
		invoiceGenerator.diplayTotal();
	}
}