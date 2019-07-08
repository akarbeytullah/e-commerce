# E-Commerce Shopping Cart

This project is designed webbased project for getting REST request as POST,GET.

### Features

  - Add CATEGORY
  - Add PRODUCT to CATEGORY
  - Add CAMPAIGN as Rate or Amount to CATEGORY
  - Add COUPON
  - Create a SHOPPING CART and add PRODUCT to in with quantity info.
  - Apply a coupon to SHOPPIN CART

You can also get value of :
 -  Total Amount After Discounts
 -  Coupon Discount
 -  Campaign Discount
 -  Delivery Cost.

### Project Configuration
 - You should have jdk 11 for this project. 
 - Project's listening port can be configured with `application.yaml`. And you can change the base context-path from this file.
    ```
    server:
      port: 8080
      servlet:
        context-path: /ecommerce
    ```
 - Spring and inmemory database as H2 can be configured with `application.properties` . So you can connect to h2 with http://127.0.0.1:8080/ecommerce/h2-console and login as `spring.datasource.username=quest` value with empty password. Also you can set the password like `spring.datasource.password=quest_password` if you want.
 
    ```
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=quest
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    spring.datasource.platform=h2-console
    spring.h2.console.enabled=true
    ```

 - In project, there is some constant parameter for calculating the delivery cost. Therefore `application.properties` file has constant value. You can configure before the application starts.

     ```
    deliver.costPerProduct=5.0
    deliver.costPerDelivery=2.0
    deliver.fixedCost=2.99
     ```

### Usage Of REST API
To use shopping cart's endpoint, logically if you wants to add shopping item as product to cart, you have to add category and then product to category. Campaign and Coupon is not mandatory property.

CATEGORY
 - POST  http://127.0.0.1:8080/ecommerce/category/add/{categoryName}
    - categoryName should be unique. If not you will get 400 Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/category/add/Food
    
PRODUCT
 - POST  http://127.0.0.1:8080/ecommerce/product/add/{categoryId}
    - categoryId should be mapped with added category's database id. If not , you will get Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/product/add/1
    - you should send the info about product from request body as JSON.
        `{"title": "Apple","price": "100"}` 

COUPON
 - POST http://127.0.0.1:8080/ecommerce/coupon/add
    - you should send the info about coupon from request body as JSON.
    `{"amount": "100","discount": "10"}` 

CAMPAIGN
 - POST for type of RATE http://127.0.0.1:8080/ecommerce/campaign/add/rate/{categoryId}
    - categoryId should be mapped with added category’s database id. If not , you will get Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/ecommerce/campaign/add/rate/1
    - you should send the info about campaign from request body as JSON.
    `{"discount": "20.0","quantity": "5"}` 

 - POST for type of AMOUNT http://127.0.0.1:8080/ecommerce/campaign/add/amount/{categoryId}
    - categoryId should be mapped with added category’s database id. If not , you will get Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/ecommerce/campaign/add/amount/1
    - you should send the info about campaign from request body as JSON.
    `{"discount": "20.0","quantity": "5"}` 
 

SHOPPIN CART
In this shopping cart, rest endpoints of this api need to customer name to match with related cart. Because of that the request of this should send the customer name with other infos.

 - POST  http://127.0.0.1:8080/ecommerce/cart/addItem/{productId}
    - productId should be mapped with added product's database id. If not , you will get Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/cart/addItem/1
    - you should send the info about product from request body as JSON.
        `{"quantity": "5","user": "QUEST"}` 

 - POST  http://127.0.0.1:8080/ecommerce/cart/applyCoupon/{couponId}?customerName=$customerName
    - couponId should be mapped with added coupon's database id. If not , you will get Bad Request Http Response.
    - If customerName value does not match with any shopping cart's user, you will get the Bad Request Http Response.
    - If coupon's amount value is not greater than shoppign cart's price after discounts, you will get Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/cart/add/1?customerName=QUEST

 - GET  http://127.0.0.1:8080/ecommerce/cart/getDeliveryCost?customerName=$customerName
    - If customerName value does not match with any shopping cart's user, you will get the Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/cart/getDeliveryCost?customerName=QUEST
    - After GET request you will get value of cost from body as JSON `{"result": "3.15"}`

 - GET  http://127.0.0.1:8080/ecommerce/cart/getCampaignDiscount?customerName=$customerName
    - If customerName value does not match with any shopping cart's user, you will get the Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/cart/getCampaignDiscount?customerName=QUEST
    - After GET request you will get value of cost from body as JSON `{"result": "20.87"}`

 - GET  http://127.0.0.1:8080/ecommerce/cart/getCouponDiscount?customerName=$customerName
    - If customerName value does not match with any shopping cart's user, you will get the Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/cart/getCouponDiscount?customerName=QUEST
    - After GET request you will get value of cost from body as JSON `{"result": "27.0"}`

 - GET  http://127.0.0.1:8080/ecommerce/cart/getTotalAmountAfterDiscounts?customerName=$customerName
    - If customerName value does not match with any shopping cart's user, you will get the Bad Request Http Response.
    - Example: http://127.0.0.1:8080/ecommerce/cart/getTotalAmountAfterDiscounts?customerName=QUEST
    - After GET request you will get value of cost from body as JSON `{"result": "199.99"}`
