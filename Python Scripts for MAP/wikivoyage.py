########################################################################
#   Make a XML file out of WikiVoyage Dump without extracting	         
#   Include only countries and major cities			      	
#   Moderately strict matching only titles
######################################################################## 

import sys
import os
import re
import bz2

# Set of places to Match
places_set = set(place.rstrip().lower() for place in open('places.txt'))

adder = 0

filename = 'enwikivoyage-20131115-pages-meta-current.xml.bz2'
newFile = open('/panasas/scratch/dbharadw/' + 'wikivoyagedump.xml',"a")
newFile.write('<?xml version="1.0" encoding="UTF-8"?>\n')
newFile.write('<wikimedia>\n')
newFile.flush()
for line in bz2.BZ2File(filename, 'rb', 10000000):
	if ( line.find('<title>') != -1):
		title = re.sub(r'<[^>]+>', '', line)
		title = title.lower()
		list_title = title.split()
		for word in list_title:
			if ( word in places_set ):
				adder = 1
				newFile.write('<page>\n')
				newFile.flush()
				break	
	if ( adder == 1 and line.find('</page>') == -1):
		newFile.write(line)
		newFile.flush()
	if ( adder == 1 and line.find('</page>') != -1):
		newFile.write(line)
		newFile.flush()
		adder = 0
newFile.write('</wikimedia>\n')
newFile.flush()
newFile.close()
print 'Successful'
