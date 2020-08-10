package com.service;

import com.google.gson.Gson;
import com.handler.Handler;
import com.model.*;
import com.repository.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class Transaction_service {

    String bank = "http://localhost:8082/bank/reqStatus";
    String merchant = "http://localhost:8082/merchant/reqStatus";


    SqlSession session = null;
    User_mapper user_mapper = null;
    Seller_mapper seller_mapper = null;
    Address_mapper address_mapper = null;
    Transaction_mapper trans_mapper = null;
    Product_mapper product_mapper = null;
    Location_mapper loc_mapper = null;
    Handler handler = new Handler();

    public void readConfig() throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(User_mapper.class);
        session.getConfiguration().addMapper(Seller_mapper.class);
        session.getConfiguration().addMapper(Address_mapper.class);
        session.getConfiguration().addMapper(Transaction_mapper.class);
        session.getConfiguration().addMapper(Product_mapper.class);
        session.getConfiguration().addMapper(Location_mapper.class);
        user_mapper = session.getMapper(User_mapper.class);
        address_mapper = session.getMapper(Address_mapper.class);
        seller_mapper = session.getMapper(Seller_mapper.class);
        trans_mapper = session.getMapper(Transaction_mapper.class);
        product_mapper = session.getMapper(Product_mapper.class);
        loc_mapper = session.getMapper(Location_mapper.class);
    }

    public String makeOrder(String email, String product_name, int qty, String address_name) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isProductNameExist(product_name)) {
                        if (!(handler.isProductFromOwnShop(email, product_name))) {
                            if (handler.isShopOfTheProductActive(product_name)) {
                                if (handler.isAddressNameExist(email, address_name)){
                                    if (handler.isSellerProductQtyEnough(product_name, qty)) {
                                        //calculate weight
                                        Product tempproduct = product_mapper.getByName(product_name);
                                        float weight = tempproduct.getProduct_weight()*qty;
                                        //calculate shipping cost
                                        Address tempaddress = new Address();
                                        tempaddress.setId_user(temp.getId_user());
                                        tempaddress.setAddress_name(address_name);
                                        Address tempaddress2 = address_mapper.getByName(tempaddress);
                                        Seller tempseller = seller_mapper.getByIdSeller(tempproduct.getId_seller());
                                        int shoplocid = loc_mapper.getByLocation(tempseller.getShop_location());
                                        String buyerloc = tempaddress2.getProvince();
                                        int buyerlocid = loc_mapper.getByLocation(buyerloc);
                                        int loc;
                                        if (shoplocid>=buyerlocid) {
                                            loc = shoplocid-buyerlocid+1;
                                        } else {
                                            loc = buyerlocid-shoplocid+1;
                                        }
                                        int weightcost = (int) Math.ceil(weight/1000);
                                        float shippingcost = loc*weightcost*9000;
                                        float productprice = tempproduct.getProduct_price()*qty;
                                        float totalcost = shippingcost+productprice;

                                        Transaction transaction = new Transaction(temp.getId_user(), tempproduct.getId_seller(), weightcost, shippingcost, totalcost);
                                        trans_mapper.inputTrans(transaction);
                                        Transaction temptrans = trans_mapper.getLastTrans();
                                        TransDetails transDetails = new TransDetails(temptrans.getId_trans(),tempproduct.getId_product(), qty);
                                        trans_mapper.inputTransDetails(transDetails);
                                        session.commit();
                                        session.close();
                                        return "Product waiting for payment. Your trans id is "+temptrans.getId_trans() + ". Please choose Payment method. ";
                                    } else {
                                        return "Seller's stock not enough";
                                    }
                                } else {
                                    return "Address invalid in this account";
                                }
                            } else {
                                return "Shop is not active. Cannot shop here";
                            }
                        } else {
                            return "Cannot shop from user's own shop!";
                        }
                    } else {
                        return "Product does not exist.";
                    }
                } else {
                    return "User is banned. No Profile to be shown. ";
                }
            } else {
                return "User not logged in. Please Login first. ";
            }
        } else {
            return "User not found. ";
        }
    }


    public String seeTransaction(String email, int id_trans) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isTransExistInUser(email, id_trans)) {
                        Transaction temptrans = trans_mapper.getById(id_trans);
                        String newstatus = "false";
                        //check payment status
                        if (temptrans.getPayment_method().equals("Bank Transfer")) {
                            Payment_service payment_service = new Payment_service();
                            newstatus = payment_service.executePost(bank, String.valueOf(id_trans));
                        }
                        if (temptrans.getPayment_method().equals("Bank VA")) {
                            Payment_service payment_service = new Payment_service();
                            newstatus = payment_service.executePost(bank, String.valueOf(id_trans));
                        }
                        if (temptrans.getPayment_method().equals("Merchant")) {
                            Payment_service payment_service = new Payment_service();
                            newstatus = payment_service.executePost(merchant, String.valueOf(id_trans));
                        }

                        if (newstatus.contains("t")) {
                            temptrans.setTrans_status("Payment Done");
                            trans_mapper.updateStatus(temptrans);
                            session.commit();
                            session.close();
                        }

                        Gson gson = new Gson();
                        String json = gson.toJson(temptrans);
                        return json;
                    } else {
                        return "There is no transaction with that id in this account.";
                    }
                } else {
                    return "User is banned. No Profile to be shown. ";
                }
            } else {
                return "User not logged in. Please Login first. ";
            }
        } else {
            return "User not found. ";
        }
    }


}


