To start the app run "mvn spring-boot:run" command , this starts the app which runs on port 8080

The application makes use of swagger via http://localhost:8080/swagger-ui/index.html


Test

To run the test kindly run  "mvn test"




The application.properties folder contains the config


To add a package you might need a currency . Pls find the list of acceptable currencies below
"AUD","Australian Dollar";
"BGN","Bulgarian Lev";
"BRL","Brazilian Real";
"CAD","Canadian Dollar";
"CHF","Swiss Franc";
"CNY","Chinese Renminbi Yuan";
"CZK","Czech Koruna";
"DKK","Danish Krone";
"USD","United States Dollar";
"EUR","Euro";
"GBP","British Pound";

Only the code is needed for example if you want to make add a package and you want to use Euro as your currency, the request will be

http://localhost:8080/packages
{
"name": "Mega Plan3",
"description": "This is my mega plan",
"productIds": [
"VqKb4tyj9V6i","DXSQpv6XVeJm"
],
"currency": "EUR"
}
