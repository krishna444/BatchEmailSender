# BatchEmailSender
A Sample Java Program to send emails to receivers stored in CSV file. I tried to optimize to send email in parallel to millions of receivers stored in the file.

This java application was developed using Eclipse IDE, so it can be direcly imported to eclipse and run it. 

**Note** 
Email sending programm is not implemented, you have to use java email sender or run any other email sending program externally. That is your choice. For simulation purpose, I have alloted 500ms time for each email sending task. 



## Using this Program
### Sample.csv
This is samle file where we store receiver information including email address as command separated csv file. 
### RunProgram.sh 
We can run this program to directly execute build files. Or you can run it by building in your system.
### bin 
The build files (class files) are stored in this folder.
### src 
The "raw" java source files. 
