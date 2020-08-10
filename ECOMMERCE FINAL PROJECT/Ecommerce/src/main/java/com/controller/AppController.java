package com.controller;

import com.broker.AppRecv;
import com.broker.AppSend;
import com.google.gson.Gson;
import com.model.*;
import com.util.CustomMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/shopleen")
public class AppController {

    @Autowired
    AppSend appSend;

    @Autowired
    AppRecv appRecv;

    Gson g = new Gson();

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<?> userProfile(@RequestBody JSONObject jobj) throws Exception {
        String activity = (String) jobj.get("activity");
        String result = "";
        if (Arrays.asList("register", "login", "logout", "confirm_account", "edit_password", "see_profile").contains(activity)) {
            appSend.sendToDB(jobj.toString());
            result = appRecv.receiveFromDB();
            if (activity.equals("see_profile") && result.charAt(0) == '{') {
                User p = g.fromJson(result, User.class);
                return (new ResponseEntity<>(p, HttpStatus.OK));
            }
        } else {
            result = "Wrong URL Address";
        }
        return (new ResponseEntity<>(new CustomMessage(result), HttpStatus.OK));
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public ResponseEntity<?> userAddress(@RequestBody JSONObject jobj) throws Exception {
        String activity = (String) jobj.get("activity");
        String result = "";
        if (Arrays.asList("input_address", "edit_address", "delete_address", "show_all_address").contains(activity)) {
            appSend.sendToDB(jobj.toString());
            result = appRecv.receiveFromDB();
            if (activity.equals("show_all_address") && result.charAt(0) == '[') {
                JSONParser parser = new JSONParser();
                JSONArray array = (JSONArray) parser.parse(result);
                ArrayList<Address> arrayadd = new ArrayList<>();
                String temp = null;
                for (int i = 0; i < array.size(); i++) {
                    temp = array.get(i).toString();
                    JSONObject address = (JSONObject) parser.parse(temp);
                    Address a = g.fromJson(String.valueOf(address), Address.class);
                    arrayadd.add(a);
                }
                return (new ResponseEntity<>(arrayadd, HttpStatus.OK));
            }
        } else {
            result = "Wrong URL Address";
        }
        return (new ResponseEntity<>(new CustomMessage(result), HttpStatus.OK));
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public ResponseEntity<?> viewCart(@RequestBody JSONObject jobj) throws Exception {
        String activity = (String) jobj.get("activity");
        String result = "";
        if (Arrays.asList("add_to_cart", "view_cart", "remove_product_from_cart", "empty_cart").contains(activity)) {
            appSend.sendToDB(jobj.toString());
            result = appRecv.receiveFromDB();
            if (activity.equals("view_cart") && result.charAt(0) == '{') {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(result);
                return (new ResponseEntity<>(jsonObject, HttpStatus.OK));
            }
        } else {
            result = "Wrong URL Address";
        }
        return (new ResponseEntity<>(new CustomMessage(result), HttpStatus.OK));
    }

    @RequestMapping(value = "/shop", method = RequestMethod.POST)
    public ResponseEntity<?> viewShop(@RequestBody JSONObject jobj) throws Exception {
        String activity = (String) jobj.get("activity");
        String result = "";
        if (Arrays.asList("view_shop", "view_product", "make_shop", "view_shop_seller", "input_product", "edit_product", "delete_product").contains(activity)) {
            appSend.sendToDB(jobj.toString());
            result = appRecv.receiveFromDB();
            if (activity.equals("view_shop_seller")) {
                if (result.contains("User")) {
                    return (new ResponseEntity<>(new CustomMessage(result), HttpStatus.OK));
                }
                Seller p = g.fromJson(result, Seller.class);
                return (new ResponseEntity<>(p, HttpStatus.OK));
            }
            if (activity.equals("view_shop")) {
                if (result.contains("Shop")) {
                    return (new ResponseEntity<>(new CustomMessage(result), HttpStatus.OK));
                }
                Shop p = g.fromJson(result, Shop.class);
                return (new ResponseEntity<>(p, HttpStatus.OK));
            }
            if (result.contains("product_name")) {
                Product p = g.fromJson(result, Product.class);
                return (new ResponseEntity<>(p, HttpStatus.OK));
            }
        } else {
            result = "Wrong URL Address";
        }
        return (new ResponseEntity<>(new CustomMessage(result), HttpStatus.OK));
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ResponseEntity<?> buyProduct(@RequestBody JSONObject jobj) throws Exception {
        String activity = (String) jobj.get("activity");
        String result = "";
        if (Arrays.asList("buy_product", "choose_payment_method").contains(activity)) {
            appSend.sendToDB(jobj.toString());
            result = appRecv.receiveFromDB();
        } else {
            result = "Wrong URL Address";
        }
        return (new ResponseEntity<>(new CustomMessage(result), HttpStatus.OK));
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public ResponseEntity<?> transaction(@RequestBody JSONObject jobj) throws Exception {
        String activity = (String) jobj.get("activity");
        String result = "";
        if (Arrays.asList("view_transaction", "confirm_order_received", "confirm_order_shipped", "check_order_arrived", "confirm_order_done").contains(activity)) {
            appSend.sendToDB(jobj.toString());
            result = appRecv.receiveFromDB();
            if (activity.equals("view_transaction") && result.charAt(0) == '{') {
                Transaction p = g.fromJson(result, Transaction.class);
                return (new ResponseEntity<>(p, HttpStatus.OK));
            }
        } else {
            result = "Wrong URL Address";
        }
        return (new ResponseEntity<>(new CustomMessage(result), HttpStatus.OK));
    }

}