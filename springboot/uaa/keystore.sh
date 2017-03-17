rm -rf src/main/resources/demo_jwt.jks

keytool -genkeypair -keyalg RSA -validity 20000 \
-dname "CN=kala, OU=free, O=51method, L=CD, ST=SC, C=CN" \
-alias jwt-key \
-keypass 111222 \
-storepass 111222 \
-keystore  src/main/resources/demo_jwt.jks