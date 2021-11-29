# Computer Security - Synchronized File Exchanger

## Quickstard

```
git clone git@github.com:simonpicard/synchronized-file-exchanger.git
cd synchronized-file-exchanger/src
make
java Main
```

## Introduction

The prupose of the project is to implement a secure synchronized file
exchanger between two computers with the possibility to send or receive
a file. In this project we implemented a custom protocol and we used AES
and RSA algorithm to encrypt and decrypt data in order to securely send
informations. We also use SHA3 hash generate signature and thus verify
the identity of the correspondant.

## Implementation

The project is coded in Java.  
The generation of the key was not to be handle by the project but using
openssl.  
Here are the commands that lead to the required files by the project :

    openssl req -x509 -newkey rsa:2048 -keyout private_key.pem -out public_certificate.pem -days 365
    openssl pkcs8 -topk8 -nocrypt -in private_key.pem -inform PEM -out private_key.der -outform DER
    openssl x509 -in public_certificate.pem -inform PEM –out public_certificate.der -outform DER

First we generate the private key and the public certificate and the we
convert them to DER files that Java can read.  
  
AES and RSA implementation were handle by the Java native library
javax.crypto.  
SHA3 implementation were found on the internet.  
RSA signature with SHA3 was not implemented so it is done as follow :
generate SHA3 hash and then encrypt it with private key.  
The verification wass to hash the same data, decrypt the signature usign
the public key and compare the hashes.  
  
The GUI was done using SWING.

## Code description

There is two possible action send or receive a file. The receiver is the
server and the sender is the client, once the socket are opened the
protocol can begin. First the sender, called Alice, send his public
certificate and the receiver, called Bob, do the same, after that the
Alice generate a nonce by concatenating a 32 bits random number and a
timestamp. Then Alice send a session key wich is a random 128 bits
number prefixed by his IP and Bob’s IP. This message is encrypted in RSA
using Bob’s public key. Once the session key is sent, Alice send the
next message which is the file wrapped by the IPs and the nonce is
encrypted in AES using the session key. Then Alice send her signature
and Bob verify that it is correct usign Alice’s public key. Finally Bob
sent a hash to Alice as an acknowledge.
