# Keys

## generate rsa key
```
openssl genrsa -out pri.pem 2048
openssl rsa -in pri.pem -out pub.pem -outform PEM -pubout
```