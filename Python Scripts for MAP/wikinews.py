########################################################################
#   Make a XML file out of WikiNews Dump without extracting	         
#   Include only countries and major cities			      	
#   Very Lenient matching from title to end of first paragraph
######################################################################## 
import sys
import os
import re
import bz2

# Set of Places to Match
places_set = set(place.rstrip().lower() for place in open('places.txt'))


found = 0
page_start = 0
str = ''
skip_page = 0

filename = 'enwikinews-20131129-pages-meta-current.xml.bz2'
newFile = open('/panasas/scratch/dbharadw/' + 'wikinewsdump.xml',"a")
newFile.write('<?xml version="1.0" encoding="UTF-8"?>\n')
newFile.write('<wikimedia>\n')
newFile.flush()
for line in bz2.BZ2File(filename, 'rb', 10000000):
	if ( line.find('<page>') != -1 and page_start == 0):
		page_start = 1
	if ( str.find('==') != -1 and skip_page == 0):
		page_start = 0
		skip_page = 1
	if ( page_start == 1 and skip_page == 0):
		if ( found == 0 ):
			str = str + line
			strneo = re.sub(r'[^A-Za-z0-9 ]', ' ', str)
			strneo = strneo.lower()
			for word in strneo.split():
				if ( word in places_set ):
					found = 1
					newFile.write(str)
					newFile.flush()
					str = ''
					line = ''
			strneo = ''	
	if ( found == 1 and skip_page == 0 ):
		if ( line.find('</page>') == -1):
			newFile.write(line)
			newFile.flush()
		if ( line.find('</page>') != -1):
			newFile.write(line)
			newFile.flush()
			found = 0
			page_start = 0
			skip_page =0
			str = ''
	if ( line.find('</page>') != -1 ):
		page_start = 0
		skip_page = 0
		str = ''
		found = 0
newFile.write('</wikimedia>')
newFile.flush()
newFile.close()
print 'Successful'
