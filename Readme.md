### Generate certificate for single sign-out

```
saml-okta-demo$ openssl req -newkey rsa:2048 -nodes -keyout local.key -x509 -days 365 -out local.crt
```

