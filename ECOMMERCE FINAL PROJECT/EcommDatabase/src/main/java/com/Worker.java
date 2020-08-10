package com;

import com.broker.WorkerSend;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.Address;
import com.model.Product;
import com.model.Transaction;
import com.model.User;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.service.Payment_service;
import com.service.Seller_service;
import com.service.Transaction_service;
import com.service.User_service;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Worker {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        WorkerSend send = new WorkerSend();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //durable true
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                String report = executeString(message);
                send.sendToAPI(report);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        //autoack true
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    private static String executeString(String message) throws ParseException, IOException {
        User_service user_service = new User_service();
        Seller_service seller_service = new Seller_service();
        Transaction_service trans_service = new Transaction_service();
        Payment_service payment_service = new Payment_service();
        Gson gson = new Gson();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(message);
        JSONObject jsonInput = (JSONObject) obj;
        String report = "";

        String activity = (String) jsonInput.get("activity");
        String email;
        JSONObject temp;
        Address address;
        String shop_name;
        String shop_location;
        User user;
        Product product;
        String product_name;
        int product_quantity;
        String address_name;
        int id_trans;
        String payment;
        switch (activity) {
            case "login":
                email = (String) jsonInput.get("email");
                String pwd = (String) jsonInput.get("password");
                report = user_service.userLogin(email, pwd);
                System.out.println(report);
                break;
            case "register" :
                temp = (JSONObject) jsonInput.get("user");
                Gson gson1=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                user = gson1.fromJson(temp.toString(), User.class);
                report = user_service.register(user);
                break;
            case "confirm_account":
                email = (String) jsonInput.get("email");
                report = user_service.accountConfirmation(email);
                break;
            case "logout" :
                email = (String) jsonInput.get("email");
                report = user_service.userLogout(email);
                break;
            case "see_profile" :
                email = (String) jsonInput.get("email");
                report = user_service.showProfile(email);
                break;
            case "edit_password" :
                email = (String) jsonInput.get("email");
                temp = (JSONObject) jsonInput.get("user");
                user = gson.fromJson(temp.toString(), User.class);
                report = user_service.editPwd(email, user);
                break;
            case "input_address" :
                email = (String) jsonInput.get("email");
                temp = (JSONObject) jsonInput.get("address");
                address = gson.fromJson(temp.toString(), Address.class);
                report = user_service.inputAddress(email, address);
                break;
            case "edit_address" :
                email = (String) jsonInput.get("email");
                temp = (JSONObject) jsonInput.get("address");
                address = gson.fromJson(temp.toString(), Address.class);
                report = user_service.editAddress(email, address);
                break;
            case "show_all_address" :
                email = (String) jsonInput.get("email");
                report = user_service.showAllAddress(email);
                break;
            case "delete_address" :
                email = (String) jsonInput.get("email");
                temp = (JSONObject) jsonInput.get("address");
                address = gson.fromJson(temp.toString(), Address.class);
                report = user_service.deleteAddress(email, address);
                break;
            case "make_shop" :
                email = (String) jsonInput.get("email");
                shop_name = (String) jsonInput.get("shop_name");
                shop_location = (String) jsonInput.get("shop_location");
                report = user_service.makeShop(email, shop_name, shop_location);
                break;
            case "view_shop" :
                shop_name = (String) jsonInput.get("shop_name");
                report = user_service.viewShopAsBuyer(shop_name);
                break;
            case "view_shop_seller" :
                email = (String) jsonInput.get("email");
                report = user_service.viewShopAsSeller(email);
                break;
            case "input_product" :
                email = (String) jsonInput.get("email");
                temp = (JSONObject) jsonInput.get("product");
                product = gson.fromJson(temp.toString(), Product.class);
                report = seller_service.createProduct(email, product);
                break;
            case "edit_product":
                email = (String) jsonInput.get("email");
                temp = (JSONObject) jsonInput.get("product");
                product = gson.fromJson(temp.toString(), Product.class);
                report = seller_service.updateProduct(email, product);
                break;
            case "delete_product":
                email = (String) jsonInput.get("email");
                temp = (JSONObject) jsonInput.get("product");
                product = gson.fromJson(temp.toString(), Product.class);
                report = seller_service.deleteProduct(email, product);
                break;
            case "view_product" :
                product_name =  (String) jsonInput.get("product_name");
                report = user_service.viewProduct(product_name);
                break;
            case "add_to_cart" :
                email = (String) jsonInput.get("email");
                product_name =  (String) jsonInput.get("product_name");
                product_quantity = Integer.parseInt(String.valueOf(jsonInput.get("product_quantity")));
                report = user_service.addToCart(email, product_name, product_quantity);
                break;
            case "view_cart" :
                email = (String) jsonInput.get("email");
                report = user_service.viewCart(email);
                break;
            case "remove_product_from_cart" :
                email = (String) jsonInput.get("email");
                product_name =  (String) jsonInput.get("product_name");
                report = user_service.removeFromCart(email, product_name);
                break;
            case "empty_cart":
                email = (String) jsonInput.get("email");
                report = user_service.emptyCart(email);
                break;
            case "buy_product":
                email = (String) jsonInput.get("email");
                product_name = (String) jsonInput.get("product_name");
                product_quantity = Integer.parseInt(String.valueOf(jsonInput.get("product_quantity")));
                address_name = (String) jsonInput.get("address_name");
                report = trans_service.makeOrder(email,product_name,product_quantity,address_name);
                break;
            case "choose_payment_method":
                email = (String) jsonInput.get("email");
                id_trans = Integer.parseInt(String.valueOf(jsonInput.get("id_trans")));
                payment = (String) jsonInput.get("payment_method");
                report = payment_service.choosePayMethod(email, id_trans, payment);
                break;
            case "view_transaction":
                email = (String) jsonInput.get("email");
                id_trans = Integer.parseInt(String.valueOf(jsonInput.get("id_trans")));
                report = trans_service.seeTransaction(email, id_trans);
                break;
            case "confirm_order_received":
                email = (String) jsonInput.get("email");
                id_trans = Integer.parseInt(String.valueOf(jsonInput.get("id_trans")));
                report = seller_service.confirmOrderReceived(email, id_trans);
                break;
            case "confirm_order_shipped":
                email = (String) jsonInput.get("email");
                id_trans = Integer.parseInt(String.valueOf(jsonInput.get("id_trans")));
                String tracking = (String) jsonInput.get("tracking");
                report = seller_service.confirmOrderShipped(email, id_trans, tracking);
                break;
            case "check_order_arrived" :
                email = (String) jsonInput.get("email");
                id_trans = Integer.parseInt(String.valueOf(jsonInput.get("id_trans")));
                report = seller_service.checkOrderArrived(email, id_trans);
                break;
            case "confirm_order_done":
                email = (String) jsonInput.get("email");
                id_trans = Integer.parseInt(String.valueOf(jsonInput.get("id_trans")));
                report = user_service.confirmOrderDone(email,id_trans);
                break;
            default : report = "Please input the correct activity"; break;


        }
        return report;
    }

//    {
//        "activity" : "login",
//        "email" : "shirleen1@gmail.com",
//        "password" : "@G2academy"
//    }

//    {
//        "activity" : "register",
//        "user" : {
//            "email" : "adriana@gmail.com",
//            "user_pwd" : "@G2academy",
//            "fullname" : "Adriana Shirleen",
//            "sex" : "female",
//            "birth_date" : "1990-01-01",
//            "phone_number" : "08992227799"
//        }
//    }

}

//        if (activity.equals("login")) {
//
//        }
//        if (activity.equals("register")) {
//
//        }
//        if (activity.equals("logout")) {
//            String email = (String) jsonInput.get("email");
//            report = user_service.userLogout(email);
//        }
//        if (activity.equals("see_profile")) {
//            String email = (String) jsonInput.get("email");
//            report = user_service.showProfile(email);
//        }
//        if (activity.equals("input_address")) {
//            String email = (String) jsonInput.get("email");
//            JSONObject temp = (JSONObject) jsonInput.get("address");
//            Address address = gson.fromJson(temp.toString(), Address.class);
//            report = user_service.inputAddress(email, address);
//        }
//        if (activity.equals("edit_address")) {
//            String email = (String) jsonInput.get("email");
//            JSONObject temp = (JSONObject) jsonInput.get("address");
//            Address address = gson.fromJson(temp.toString(), Address.class);
//            report = user_service.editAddress(email, address);
//        }
//        if (activity.equals("show_all_address")) {
//            String email = (String) jsonInput.get("email");
//            report = user_service.showAllAddress(email);
//        }
//        if (activity.equals("delete_address")) {
//            String email = (String) jsonInput.get("email");
//            JSONObject temp = (JSONObject) jsonInput.get("address");
//            Address address = gson.fromJson(temp.toString(), Address.class);
//            report = user_service.deleteAddress(email, address.getAddress_name());
//        }
//        if (activity.equals("make_shop")) {
//            String email = (String) jsonInput.get("email");
//            String shop_name = (String) jsonInput.get("shop_name");
//            String shop_location = (String) jsonInput.get("shop_location");
//            report = user_service.makeShop(email, shop_name, shop_location);
//        }
//        if (activity.equals()) {
//            report = user_service.
//        }

//
//    public static void main(String[] args) throws ParseException, IOException {
//        User_service userService = new User_service();
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        User user = new User("shirleen1gmail.com","@G2academy", true, "Shirleen Adriana", "female", simpleDateFormat.parse("1994-03-26"), "08991117796" );
//        String hasil1 = "";
//        String hasil2 = "";
//        hasil1 = userService.userLogin("shirleen2@gmail.com", "@G2academy");
//        //hasil2 = userService.userLogout("shirleen@gmail.com");
//        //hasil = userService.register(user);
//        hasil2 = userService.showProfile("shirleen1@gmail.com");
//        System.out.println(hasil1);
//        System.out.println(hasil2);
//        String hasil3 = userService.makeShop("shirleen2@gmail.com", "myshopee", "jakarta");
//        System.out.println("make shop" + hasil3);
////        String hasil4 = userService.inputAddress("shirleen1@gmail.com", new Address("Rumah", "Elin", "089911177799","Jalan Pinus 1 no 66", "jakarta",15119 ));
////        System.out.println(hasil4);
////        String hasil5 = userService.inputAddress("shirleen1@gmail.com", new Address("Tetangga", "Elin", "089911177799","Jalan Pinus 1 no 66", "jakarta",15119 ));
////        System.out.println(hasil5);
////        String hasil6 = userService.inputAddress("shirleen1@gmail.com", new Address("Rumah 2", "Elin", "089911177799","Jalan Pinus 1 no 66", "jakarta",15119 ));
////        System.out.println(hasil6);
////        String hasil7 = userService.editAddress("shirleen1@gmail.com", new Address("Rumah 2", "LEEN", "089911177799","Jalan Pinus 1 no 66", "jakarta",15119 ));
////        System.out.println(hasil7);
////            String hasil8 = userService.showAllAddress("shirleen1@gmail.com");
////            System.out.println(hasil8);
//        String hasil8 = userService.deleteAddress("shirleen1@gmail.com","Rumah");
//        System.out.println(hasil8);
//    }
