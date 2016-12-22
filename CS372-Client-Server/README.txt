Karen Thrasher
CS 372
Project 2 - README

Compilation Instructions:

	Use bash shell to run compileall script 
	To compile ftserver.c run:

		compileall

	Note: May need to update permissions in order to run as listed below 

		To run on bash:
			chmod u+x compileall
			compileall
			Note: if ^M present in error when compiling run: dos2unix compileall

Program Startup Instructions:
	
	Server:
		ftserver [PORT NUMBER]

	Client:

		Determining server host:

			To determine server host enter on command line: hostname
			Enter corresponding host name 
			Ex:
				If flip1.engr.oregonstate.edu, then for server host enter "flip1"
				If flip2.engr.oregonstate.edt, then for server host enter "flip2"
				If flip3.engr.oregonstate.edt, then for server host enter "flip3"

		Get List:
			python ftclient.py [server host] [server port] -l [data port]

		Get File: 
			python ftclient.py [server host] [server port] -g [filename] [data port]


Program Exit Instructions:

	Server:
		CTRL C 

	Client:
		Exits upon completion of list or file get
