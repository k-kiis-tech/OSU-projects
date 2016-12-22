"""
	Karen Thrasher
	CS 372
	Project 2
	ftclient.py - Implementation of client for text file transfer
"""
from __future__ import print_function #Allows printing on one line

import sys
import struct
import time
import os

from socket import *

#----- Command Line Arguments -----
#Option 1 - GET LIST
if len(sys.argv) == 5: #If 5 arguments then fits format for list request 

	#arg 1 - server name
	serverName = str(sys.argv[1]) #Update server name 
	if serverName == 'flip1':
		serverName = 'flip1.engr.oregonstate.edu'
	if serverName == 'flip2':
		serverName = 'flip2.engr.oregonstate.edu'
	if serverName == 'flip3':
		serverName = 'flip2.engr.oregonstate.edu'

	#arg 2 - server port
	serverPort= int(sys.argv[2]) #Gets serverPort number from command line argument

	#arg 3 - command 
	serverCommand = str(sys.argv[3])

	#arg 4 - data port
	dataPort= int(sys.argv[4]) 

#Option 2 - GET FILE
if len(sys.argv) == 6: #If 6 arguments then fits format for file request 
	#arg 1 - server name
	serverName = str(sys.argv[1]) #Update server name 
	if serverName == 'flip1':
		serverName = 'flip1.engr.oregonstate.edu'
	if serverName == 'flip2':
		serverName = 'flip2.engr.oregonstate.edu'
	if serverName == 'flip3':
		serverName = 'flip2.engr.oregonstate.edu'

	#arg 2 - server port
	serverPort= int(sys.argv[2]) #Gets serverPort number from command line argument

	#arg 3 - command
	serverCommand = str(sys.argv[3])

	#arg 4 - file name
	serverFile = str(sys.argv[4])

	#arg 5 - data port
	dataPort= int(sys.argv[5]) 


#----- Start Up Socket -----
#serverName = 'flip2.engr.oregonstate.edu'
#serverPort gotten from command line
clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((serverName,serverPort))

validSentinel = 0 #Sentinel for valid command

#----- Send Request  -----
	#sentence = raw_input('Input lowercase sentence:')
#Write - command
clientSocket.send(serverCommand.encode())

#GET LIST
#if len(sys.argv) == 5: #If 5 arguments then fits format for list request 
if serverCommand == "-l":

	validSentinel = 1 #Update sentinel value as valid command

	#Read - confirm connection
	modifiedSentence = clientSocket.recv(1024)
	print("Receiving", modifiedSentence.decode(), "from flip1:", dataPort)

	#Write - port number
	clientSocket.send(str(dataPort).encode()) #Change dataPort to str to be able to send

	#Start up 2nd connection on data port 
	clientSocket2 = socket(AF_INET, SOCK_STREAM)
	clientSocket2.connect((serverName,dataPort))

	#Receive list 
	#Write - 2nd connection
	confSentence = 'Sending directory contents to flip2: '
	clientSocket2.send(confSentence.encode())

	#Read - 2nd connection
	#modifiedSentence = clientSocket2.recv(1024)
	#print('From Server: ', modifiedSentence.decode())

	def recv_timeout(the_socket,timeout=2): #Receive data until there is no more to receive / timeout
	    #make socket non blocking
	    the_socket.setblocking(0)
	     
	    #total data partwise in an array
	    total_data=[];
	    data='';
	     
	    #beginning time
	    begin=time.time()
	    while 1:
	        #if you got some data, then break after timeout
	        if total_data and time.time()-begin > timeout:
	            break
	         
	        #if you got no data at all, wait a little longer, twice the timeout
	        elif time.time()-begin > timeout*2:
	            break
	         
	        #recv something
	        try:
	            data = the_socket.recv(8192)
	            if data:
	                #total_data.append(data)
	                total_data.append("%s\n" % (data)) #Append newline character 
	                #change the beginning time for measurement
	                begin=time.time()
	            else:
	                #sleep for sometime to indicate a gap
	                time.sleep(0.1)
	        except:
	            pass
	     
	    #join all parts to make final string
	    return ''.join(total_data)
	 
	#get reply and print
	print (recv_timeout(clientSocket2)) #Displays list to console screen 
	#End new code

	clientSocket2.close() #Close 2nd connection

#GET FILE
#if len(sys.argv) == 6: #If 5 arguments then fits format for list request 
if serverCommand == "-g":
	#Read - confirm connection
	validSentinel = 1 #Update sentinel value as valid command

	modifiedSentence = clientSocket.recv(1024)
	#print("Receiving", modifiedSentence.decode(), "from flip1:", dataPort)

	#Write - port number
	clientSocket.send(str(dataPort).encode()) #Change dataPort to str to be able to send

	#Read - confirmation 
	modifiedSentence = clientSocket.recv(1024)

	#Write - file name
	clientSocket.send(serverFile.encode())

	#Read - error msg or OK to proceed
	fileExists = clientSocket.recv(1024)

	if fileExists == "0":
		print("flip1:", serverPort,"says FILE NOT FOUND")

	if fileExists == "1":
		print("Receiving \"", serverFile, "\" from flip1:", dataPort,".")

		#Start up 2nd connection on data port 
		clientSocket2 = socket(AF_INET, SOCK_STREAM)
		clientSocket2.connect((serverName,dataPort))

		#Receive list 
		#Write - 2nd connection
		confSentence = '2nd connection open'
		clientSocket2.send(confSentence.encode())

		#Read - receive file
		def recv_timeout(the_socket,timeout=2):
		    #make socket non blocking
		    the_socket.setblocking(0)
		     
		    #total data partwise in an array
		    total_data=[];
		    data='';
		     
		    #beginning time
		    begin=time.time()
		    while 1:
		        #if you got some data, then break after timeout
		        if total_data and time.time()-begin > timeout:
		            break
		         
		        #if you got no data at all, wait a little longer, twice the timeout
		        elif time.time()-begin > timeout*2:
		            break
		         
		        #recv something
		        try:
		            data = the_socket.recv(8192)
		            if data:
		                total_data.append(data)
		                #total_data.append("%s\n" % (data)) #Append newline character 
		                #change the beginning time for measurement
		                begin=time.time()
		            else:
		                #sleep for sometime to indicate a gap
		                time.sleep(0.1)
		        except:
		            pass
		     
		    #join all parts to make final string
		    return ''.join(total_data)
		 
		#get reply and print
		#print (recv_timeout(clientSocket2))

		#Write to file
		if os.path.exists(serverFile): #If file already exists append "new_" to file name
			#print("File already exists\n")
			serverFile = "new_"+serverFile

		#Write file 
		file_ = open(serverFile, 'w')
		file_.write(recv_timeout(clientSocket2))
		file_.close()

		print("File transfer complete.")
		
		clientSocket2.close() #Close 2nd connection

if validSentinel == 0: #Not a valid command
	errorMsg = clientSocket.recv(1024)
	print(errorMsg)

	
clientSocket.close() #Close control connection

"""
Reference:
Section 2.7 of text Computer Networking, Kurose & Ross 

Python command line arguments
	http://stackoverflow.com/questions/4033723/how-do-i-access-command-line-arguments-in-python
	http://www.cyberciti.biz/faq/python-command-line-arguments-argv-example/
	http://stackoverflow.com/questions/22975618/how-to-read-integer-command-line-arguments-in-python

Python print_function
	http://stackoverflow.com/questions/493386/how-to-print-in-python-without-newline-or-space
	http://stackoverflow.com/questions/11266068/python-avoid-new-line-with-print-command

Number of command line Arguments
	https://www.tutorialspoint.com/python/python_command_line_arguments.htm

Send integet over a socket
	http://stackoverflow.com/questions/17035697/trouble-sending-integer-over-socket-server

recv() until all received 
	http://www.binarytides.com/receive-full-data-with-the-recv-socket-function-in-python/

Append newline character to total_data.append() function
	http://stackoverflow.com/questions/5429064/how-to-write-list-of-strings-to-file-adding-newlines

Write to file
	http://stackoverflow.com/questions/9536714/python-save-to-file

Checking to see if file with same name exists
	http://stackoverflow.com/questions/33691187/how-to-save-the-file-with-different-name-and-not-overwriting-existing-one

"""