package invoice;

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
public class InvoiceItem {
	
	private String productID;
	private int count;
	private String description;
	private int vatUnit;
	private String unitPrice;
	private double lineTotal;
	private double vatTotal;
	
	
	/**
	 * Constructs a new <code>InvoiceItem</code> instance.
	 */
	public InvoiceItem() {
		this.setInvoiceItem("0", "", 14, "0.0");
	}

	/**
	 * @param newProductID
	 * @param newVatUnit
	 * @param newUnitPrice
	 */
	public void setInvoiceItem(String newProductID, String newDescription, int newVatUnit, String newUnitPrice) {
		this.setProductID(newProductID);
		this.setCount(0);
		this.setDescription(newDescription);
		this.setVatUnit(newVatUnit);
		this.setUnitPrice(newUnitPrice);
		this.setLineTotal(0.0);
		this.setVatTotal(0.0);
	}
	
	/**
	 * TODO
	 */
	public void calculateTotals() {
		double unitAmount = Double.parseDouble(this.getUnitPrice());
		double vatAmount = (unitAmount * this.getVatUnit()) /100;
		
		this.setLineTotal(unitAmount + vatAmount);
		if (this.getVatTotal().equals("0.00")) {
			this.setVatTotal(vatAmount);
		}
		
		this.incrementCount();
	}
	
	/**
	 * TODO
	 */
	public void incrementCount() {
		this.count = count + 1;
	}
	
	/**
	 * @return productID
	 */
	public String getProductID() {
		return this.productID;
	}
	
	/**
	 * @return lineTotal
	 */
	public String getLineTotal() {
		return String.format("%.2f", this.lineTotal);
	}
	
	/**
	 * @return vatTotal
	 */
	public String getVatTotal() {
		return String.format("%.2f", this.vatTotal);
	}
	
	/**
	 * @return
	 */
	private int getVatUnit()
	{
		return this.vatUnit;
	}

	/**
	 * @return unitPrice
	 */
	public String getUnitPrice() {
		return String.valueOf(this.unitPrice);
	}

	/**
	 * @param newProductID
	 */
	public void setProductID(String newProductID) {
		this.productID = newProductID;
	}
	
	/**
	 * @param newCount
	 */
	public void setCount(int newCount) {
		this.count = newCount;
	}

	/**
	 * @param newDescription
	 */
	public void setDescription(String newDescription) {
		if ((newDescription.length()) > 0 && (newDescription.charAt(0) == ' ')) {
			this.description = newDescription.substring(1);
		}
		else {
			this.description = newDescription;
		}
	}
	
	/**
	 * @param newVatUnit
	 */
	public void setVatUnit(int newVatUnit) {
		this.vatUnit = newVatUnit;
	}

	/**
	 * @param newUnitPrice
	 */
	public void setUnitPrice(String newUnitPrice) {
		this.unitPrice = newUnitPrice;
	}

	/**
	 * @param newLineTotal
	 */
	public void setLineTotal(double newLineTotal) {
		this.lineTotal += newLineTotal;
	}
	
	public void setVatTotal(double newVatTotal) {
		this.vatTotal = newVatTotal;
	}

	/**
	 * @return description
	 */ 
	public String getDescription()
	{
		return this.description;
	}

	/**
	 * @return count
	 */
	public String getCount()
	{
		return String.valueOf(count);
	}
}