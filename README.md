The main features of the application:

1. Implemented file encryption and decryption
2. Implemented file compression and decompression
3. Implemented file encryption & compression, decompression & decryption



The instructions for running the programme:

Step 1: Place the ds.war in $TOMCAT_HOME\webapps\
Step 2: Place the ds.jar in any directory
        eg. C:\ds.jar
Step 3: Executing the command from a console: java -cp ./ds.jar ie.gmit.AsyncService
        eg. C:\>java -cp ./ds.jar ie.gmit.AsyncService
Step 4: Start up Tomcat
Step 5: Open your browser, enter "http://localhost:8080/ds/" in search bar, then it will display the JSP page
Step 6: Now we see the JSP page
        (1) select a operation you want
            eg. Encrypt and Compress
        (2) Enter the absolute path of the file you want to process (Note: first you should have the file in local disk)
            eg. d:/a.txt
        (3) Enter the directory where you want save the processed file to (Note: the input should not include file name)
            eg. d:/
        (4) Enter the key (If you didn't enter a key, the system have a default key "Ru")
            eg. 123   
        (5) Click the submit button, then it will turn to another page, click Check Result,
            then you can know if the process succeed of failed.
Step 7: Now check the directory on your local disk (The directory that you set in Step 6 (3))
        You can find there increased two file in the directory
        eg. a.txt.aes   a.txt.aes.gz
	.aes file is the file after encryption
	.gz file is the compressed file

Now you can delete the files a.txt and a.txt.aes, only left the file a.txt.aes.gz
Then back to the Step 6:
        (1) select a operation: Decompress And Decrypt
        (2) Enter full path of file: d:/a.txt.aes.gz
        (3) Enter save directory: d:/
        (4) Enter key: 123
        (5) Submit
You can find there increased two file in the directory (The directory that you set in Step 6 (3)) :
a.txt.aes and a.txt
view the a.txt, the content is the same to the original one

  


         


      

