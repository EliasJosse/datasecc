Assignment 1.4

OTPInputstream

A class which implements one time pad encryption technique and extends InputStream. It takes two inputStreams, message and key. It is tested mainly using read(), readXOR and setEncrypt(), given that the streams are of the same size.

including is a main function for testing

void setEncrypt(boolean)
sets the encrypt to true/false. if true, encryption, if false, decryption. true by default 

int read()

returns the ascii value of the decrypted/encrypted character. Exptects ascii values of capital alphabetical letters.

test by switching values on line 79 and 80


int readXOR()
encrypts/decrypts arbitrary data by the byte


test by switching values on line 141 and 142

