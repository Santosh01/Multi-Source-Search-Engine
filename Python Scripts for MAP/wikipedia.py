########################################################################
#   Make a XML file out of Wikipedia Dump without extracting	         
#   Include only countries and major cities			      	
#   Very Strict Matching only the mentioned cities & countries
########################################################################
 
import sys
import os
import re
import bz2

# Set of places to Match
places_set = set(place.strip().lower() for place in open('places.txt'))

adder = 0

filename = 'enwiki-20131104-pages-meta-current.xml.bz2'
newFile = open('/panasas/scratch/dbharadw/' + 'wikidump.xml',"a")
newFile.write('<?xml version="1.0" encoding="UTF-8"?>\n')
newFile.flush()
for line in bz2.BZ2File(filename, 'rb', 10000000):
	if ( line.find('<title>') != -1):
		title = re.sub(r'<[^>]+>', '', line)
		if ( title in places_set ):
			adder = 1
			newFile.write('<page>\n')
			newFile.flush()
	if ( adder == 1 and line.find('</page>') == -1):
		newFile.write(line)
		newFile.flush()
	if ( adder == 1 and line.find('</page>') != -1):
		newFile.write(line)
		newFile.flush()
		adder = 0
newFile.close()
print 'Successful'
