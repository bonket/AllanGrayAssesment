import os
import sys
from optparse import OptionParser
from _winreg import *
import subprocess

class InvoiceGenerator:
	global invoiceItemFile
	location = os.path.dirname(__file__)
	invoiceItemFile = os.path.join(location, "../invoice/invoiceItems.txt")
	
	global vatUnit
	
	def __init__(self):
				
		print "Invoice Generator"
		print ""
		
		usage = "usage: %prog [options] arg1 arg2"
		parser = OptionParser(usage=usage)
		
		parser.add_option("-f", "--invoiceFile", type="string", dest="invoiceItemFile", \
							help="Invoice file to be loaded")
		parser.add_option("-v", "--vat", type="int", dest="vatUnit", default=14, \
							help="Set the location of the vat unit")
		(options, args) = parser.parse_args()
		
		if options.vatUnit:
			vatUnit = options.vatUnit
		
		if options.invoiceItemFile:
			location = os.path.dirname(__file__)
			invoiceItemFile = os.path.join(location, options.invoiceItemFile)
		subprocess.call(['java', '-version'])
		subprocess.call(['java', '-cp', 'invoice.InvoiceGenerator'])

invoiceGen = InvoiceGenerator()
