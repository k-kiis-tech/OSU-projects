/* Karen Thrasher
 * CS 372
 * Project 2
 * ftserver.c - Implementation of server for text file transfer
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <dirent.h> //Directories
#include <sys/stat.h>
#include <errno.h>
#include <arpa/inet.h>


void handleConnection(int, int); /* Handles separate instance for each connection */
int compare_strings(char a[], char b[]); //Compares strings
void error(const char *msg) /*Error handling*/
{
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[])
{
     int sockfd, newsockfd, portno, pid;
     socklen_t clilen;
     struct sockaddr_in serv_addr, cli_addr;

     if (argc < 2) { /*Checks if port number provided, else error*/
         fprintf(stderr,"ERROR, no port provided\n");
         exit(1);
     }
     sockfd = socket(AF_INET, SOCK_STREAM, 0);
     if (sockfd < 0) 
        error("ERROR opening socket");
     bzero((char *) &serv_addr, sizeof(serv_addr));
     portno = atoi(argv[1]);
     serv_addr.sin_family = AF_INET;
     serv_addr.sin_addr.s_addr = INADDR_ANY;
     serv_addr.sin_port = htons(portno);
     if (bind(sockfd, (struct sockaddr *) &serv_addr,
              sizeof(serv_addr)) < 0) 
              error("ERROR on binding");
     listen(sockfd,5);
     clilen = sizeof(cli_addr);

     printf("Server open on %i.\n",portno); /*Confirmation that listening on port*/

     while (1) { //Loops until interrupted 
         newsockfd = accept(sockfd, 
               (struct sockaddr *) &cli_addr, &clilen);
         if (newsockfd < 0) 
             error("ERROR on accept");
         pid = fork();
         if (pid < 0)
             error("ERROR on fork");
         if (pid == 0)  {
             close(sockfd);
             handleConnection(newsockfd, portno);
             exit(0);
         }
         else close(newsockfd);
     } /* end of while */
     close(sockfd);
     return 0; /* we never get here */
}

/******** handleConnection() *********************
 There is a separate instance of this function 
 for each connection.  It handles all communication
 once a connection has been established.
 *****************************************/
void handleConnection (int sock, int originalPort)
{
   int n;
   char buffer[256];
   int compareInt;

   char commandList[256] = "-l"; //Command to match for list 
   char commandGet[256] = "-g"; //Command to match for get
   int dataPortNo = 0; //Data port number 
   int validCommand = 0; //Sentinel variable for valid command
      
   bzero(buffer,256);
//Read - command
   n = read(sock,buffer,255);
   if (n < 0) error("ERROR reading from socket");
   //printf("Command: %s\n",buffer);
   printf("Connection from flip2.\n");

//LIST REQUEST:
   compareInt = compare_strings(buffer, commandList); //Checks if command matches LIST
   if(compareInt == 0){
   		validCommand = 1; //Update sentinel value

	   //Write - confirm connection 
	   n = write(sock,"directory structure",19);
	   if (n < 0) error("ERROR writing to socket");

	//Read - data port
	   n = read(sock,buffer,255);
	   if (n < 0) error("ERROR reading from socket");
	   //printf("Data port: %s\n",buffer);
	   printf("List directory requested on port %s.\n",buffer);

	   	//Convert string to int for data port 
	   	uintmax_t num = strtoumax(buffer, NULL, 10);
		dataPortNo = num; 
		//printf("Data port number: %i\n", dataPortNo); 

		//Open new connection on data port 
		int sockfd, newsockfd, portno;
	     socklen_t clilen;
	     char buffer[256];
	     struct sockaddr_in serv_addr, cli_addr;
	     int n;

	     sockfd = socket(AF_INET, SOCK_STREAM, 0);
	     if (sockfd < 0) 
	        error("ERROR opening socket");
	     bzero((char *) &serv_addr, sizeof(serv_addr));
	     portno = dataPortNo;
	     serv_addr.sin_family = AF_INET;
	     serv_addr.sin_addr.s_addr = INADDR_ANY;
	     serv_addr.sin_port = htons(portno);
	     if (bind(sockfd, (struct sockaddr *) &serv_addr,
	              sizeof(serv_addr)) < 0) 
	              error("ERROR on binding");
	     listen(sockfd,5);
	     clilen = sizeof(cli_addr);
	     newsockfd = accept(sockfd, 
	                 (struct sockaddr *) &cli_addr, 
	                 &clilen);
	     if (newsockfd < 0) 
	          error("ERROR on accept");


	    bzero(buffer,256);
	    n = read(newsockfd,buffer,255);
	    if (n < 0) error("ERROR reading from socket");
	    printf("%s%i\n",buffer, dataPortNo);


	   	//Get list directory
			DIR *dp;
			struct dirent *ep;     
			dp = opendir ("./");

			char dirList[501];

			if (dp != NULL){ 
			    while (ep = readdir (dp)){
			      	//puts (ep->d_name); //Prints directory to screen
			      
			      	char fileBuffer[256];

			      	strcpy(fileBuffer, ep->d_name);

			      	size_t len = strlen(fileBuffer);

			      	//Write - directory list items
			      	n = write(newsockfd,fileBuffer,len);
		     	  	if (n < 0) error("ERROR writing to socket");

			    }
			    (void) closedir (dp);
			}
			else //Display error if not able to open directory
				perror ("Couldn't open the directory\n");

	     close(newsockfd);
	     close(sockfd);
	}
//GET FILE REQUEST:
	compareInt = compare_strings(buffer, commandGet); //Checks if command matches GET
   	if(compareInt == 0){
   		validCommand = 1; //Update sentinel value

   		char fileToSend[256];

   	//Write - confirm connection 
	   n = write(sock,"FILE NAME",9);
	   if (n < 0) error("ERROR writing to socket");

	//Read - data port
	    n = read(sock,buffer,255);
	    if (n < 0) error("ERROR reading from socket");

	//Write - confirm
	    n = write(sock,"FILE NAME",9);
	    if (n < 0) error("ERROR writing to socket");

	   	//Convert string to int for data port 
	   	uintmax_t num = strtoumax(buffer, NULL, 10);
		dataPortNo = num; 

	//Read - file name
		n = read(sock,buffer,255);
	    if (n < 0) error("ERROR reading from socket");

	    //Move file name to string 
	    strcpy(fileToSend, buffer); 

	    //Print confirmation message
	    printf("File \"%s\" requested on port %i.\n", fileToSend, dataPortNo );

	    struct stat sb; //Checking to see if file exists or not 
		if (stat(fileToSend, &sb) == 0 && S_ISREG(sb.st_mode)){
	//Write - file exists
		    printf("Sending \"%s\" to flip2: %i\n", fileToSend, dataPortNo);
		    n = write(sock,"1",1); //Write 1 to confirm existence of file
	    	if (n < 0) error("ERROR writing to socket");

		    	//File exists so open second connection to send file over 

				//Open new connection on data port 
				int sockfd, newsockfd, portno;
			     socklen_t clilen;
			     char buffer[256];
			     struct sockaddr_in serv_addr, cli_addr;
			     int n;

			     sockfd = socket(AF_INET, SOCK_STREAM, 0);
			     if (sockfd < 0) 
			        error("ERROR opening socket");
			     bzero((char *) &serv_addr, sizeof(serv_addr));
			     portno = dataPortNo;
			     serv_addr.sin_family = AF_INET;
			     serv_addr.sin_addr.s_addr = INADDR_ANY;
			     serv_addr.sin_port = htons(portno);
			     if (bind(sockfd, (struct sockaddr *) &serv_addr,
			              sizeof(serv_addr)) < 0) 
			              error("ERROR on binding");
			     listen(sockfd,5);
			     clilen = sizeof(cli_addr);
			     newsockfd = accept(sockfd, 
			                 (struct sockaddr *) &cli_addr, 
			                 &clilen);
			     if (newsockfd < 0) 
			          error("ERROR on accept");


			    bzero(buffer,256);
		//Read - conf message
			    n = read(newsockfd,buffer,255);
			    if (n < 0) error("ERROR reading from socket");
			    //printf("Message: %s\n",buffer);

		//Write - file
			    FILE *sendingFile;
				int size, read_size, stat, packet_index;
				char send_buffer[10240], read_buffer[256];
				packet_index = 1;

				sendingFile = fopen(fileToSend, "r");
   				//printf("Getting file Size\n");

   				if(sendingFile == NULL) {
        			printf("Error Opening File"); 
        		}

        		fseek(sendingFile, 0, SEEK_END);
			    size = ftell(sendingFile);
			    fseek(sendingFile, 0, SEEK_SET);
			    //printf("Total file size: %i\n",size);

			    //Send file as byte array
   				//printf("Sending file as Byte Array\n");

		   		while(!feof(sendingFile)) {
				   //while(packet_index = 1){
				      //Read from the file into our send buffer
				      read_size = fread(send_buffer, 1, sizeof(send_buffer)-1, sendingFile);

				      //Send data through our socket 
				      do{
				        stat = write(newsockfd, send_buffer, read_size);  
				      }while (stat < 0);

				      //Checking values
				      /*printf("Packet Number: %i\n",packet_index);
				      printf("Packet Size Sent: %i\n",read_size);     
				      printf(" \n");*/

				      packet_index++;  

				      //Zero out our send buffer
				      bzero(send_buffer, sizeof(send_buffer));
		     	}

			    close(newsockfd);
			    close(sockfd);
			     
		}
		else{
	//Write - file does not exist 
			printf("File not found. Sending error message to flip2: %i\n", originalPort);
			n = write(sock,"0",1); //Write 0 to confirm file does not exist
	    	if (n < 0) error("ERROR writing to socket");
		}

	}
	if(validCommand == 0){ //Not a valid command
		printf("Not a valid command\n");
		//Write - not a valid command
	   n = write(sock,"Not a valid command",19);
	   if (n < 0) error("ERROR writing to socket");

	}


}

//Function compares strings and returns 0 if they are equal
int compare_strings(char a[], char b[])
{
   int c = 0;
 
   while (a[c] == b[c]) {
      if (a[c] == '\0' || b[c] == '\0')
         break;
      c++;
   }
 
   if (a[c] == '\0' && b[c] == '\0')
      return 0;
   else
      return -1;
}


 /* Reference
    Beej's Guide
      http://beej.us/guide/bgnet/output/html/singlepage/bgnet.html

    CS344 Operating Systems sample server program

    Convert string to integet in C 
    	http://stackoverflow.com/questions/7021725/converting-string-to-integer-c

    Get directory in C 
    	http://stackoverflow.com/questions/12489/how-do-you-get-a-directory-listing-in-c

    Length of character array
    	http://stackoverflow.com/questions/4180818/finding-the-length-of-a-character-array-in-c

	Checking to see if a directory or file exists using stat
		http://stackoverflow.com/questions/3828192/checking-if-a-directory-exists-in-unix-system-call

	Sending a file through socket in linux
		http://stackoverflow.com/questions/15445207/sending-image-jpeg-through-socket-in-c-linux

 */
