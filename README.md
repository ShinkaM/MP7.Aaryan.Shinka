# MP7.Aaryan.Shinka
## Description
This is written in Java, and takes in an input from the user, and encrypts it using the method of a Playfair Cipher.  It also comes with a 
decrypting feature.  
The key was made based on the sentence " THE QUICK BROWN FOX JUMPED OVER THE LAZY DOG" with duplicated letters and spaces removed.

# How-to:
1. A dialogue will prompt the user to input a message 
2. The user will input 1 or 2 to encode or decode, respectively
3. The program will print the encoded or decoded message

# Process
1. We first made the general structure of the code, so that it can make the key, convert the message into a string array, and encode the message
2. We then used the Scanner so that it can take in the input from the user
3. At this point we debugged the code, referencing the outputs it got from each of the methods
4. We added the decrypt feature
5. We debugged the code to ensure that it worked

# Technical difficulties
+ The key could only include 25 letters due to the methology of the Playfair cipher, and so it is missing 'Q'. This meant
that if the message included a 'Q', the code would return null. This was solved by replacing the letter with a 'U' if the message included a 'Q'.

# The key
 The key may be changed by directly chaning the content of the key variable.  This must be in all caps, no spaces, 25 letters, and missingg 'Q'.
