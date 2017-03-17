rm -rf src/main/resource/pubkey.cert
echo "111222" | keytool -list -rfc --keystore ../uaa/src/main/resources/demo_jwt.jks \
 | openssl x509 -inform pem -pubkey \
| sed '/BEGIN CERTIFICATE/,$d' >  src/main/resources/pubkey.cert