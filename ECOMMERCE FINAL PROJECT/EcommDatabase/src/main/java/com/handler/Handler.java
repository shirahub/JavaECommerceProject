package com.handler;

import com.model.*;
import com.repository.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class Handler {

    SqlSession session = null;
    User_mapper user_mapper = null;
    Seller_mapper seller_mapper = null;
    Address_mapper address_mapper = null;
    Product_mapper product_mapper = null;
    Cart_mapper cart_mapper = null;
    Transaction_mapper trans_mapper = null;

    public void readConfig() throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(User_mapper.class);
        session.getConfiguration().addMapper(Seller_mapper.class);
        session.getConfiguration().addMapper(Address_mapper.class);
        session.getConfiguration().addMapper(Product_mapper.class);
        session.getConfiguration().addMapper(Cart_mapper.class);
        session.getConfiguration().addMapper(Transaction_mapper.class);
        user_mapper = session.getMapper(User_mapper.class);
        address_mapper = session.getMapper(Address_mapper.class);
        seller_mapper = session.getMapper(Seller_mapper.class);
        product_mapper = session.getMapper(Product_mapper.class);
        cart_mapper = session.getMapper(Cart_mapper.class);
        trans_mapper = session.getMapper(Transaction_mapper.class);
    }

    //checkSessionActive
    public boolean isUserStillLoggedIn(String email) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        return temp.isSessionActive();
    }

    public boolean isUserActive(String email) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        return temp.getUser_status();
    }

    public boolean isUserExist(String email) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        return temp != null;
    }

    public boolean isShopExist(String shop_name) throws IOException {
        readConfig();
        Seller temp = seller_mapper.getByName(shop_name);
        return temp != null;
    }

    public boolean isAddressExist(String email, Address address) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        address.setId_user(temp.getId_user());
        Address tempaddress = address_mapper.getByIdAddress(address);
        return tempaddress != null;
    }

    public boolean isSellerProductQtyEnough(String product_name, int qty) {
        Product temp = product_mapper.getByName(product_name);
        return temp.getProduct_quantity() >= qty;
    }

    public boolean isAddressNameExist(String email, String address_name) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        Address tempaddress = new Address();
        tempaddress.setId_user(temp.getId_user());
        tempaddress.setAddress_name(address_name);
        Address tempaddress2 = address_mapper.getByName(tempaddress);
        return tempaddress2 != null;
    }

    public boolean isAddressExistInUser(String email, int id_address) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        Address tempaddress = new Address();
        tempaddress.setId_user(temp.getId_user());
        tempaddress.setId_address(id_address);
        Address tempaddress2 = address_mapper.getByIDUserAddress(tempaddress);
        return tempaddress2 != null;
    }



    public boolean isTransExistInUser(String email, int id_trans) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        Transaction temptrans = trans_mapper.getById(id_trans);
        if (temptrans == null) {
            return false;
        } else {
            return temp.getId_user() == temptrans.getId_user();
        }
    }

    public boolean isShopActive(String email) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        Seller tempseller = seller_mapper.getById(temp.getId_user());
        return tempseller.isShop_status();
    }

    public boolean isShopOfTheProductActive(String product_name) throws IOException {
        readConfig();
        Product tempproduct = product_mapper.getByName(product_name);
        Seller tempseller = seller_mapper.getByIdSeller(tempproduct.getId_seller());
        return tempseller.isShop_status();
    }

    public boolean isUserSeller(String email) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        return temp.isSeller();
    }

    public boolean isProductNameExist(String product_name) throws IOException {
        readConfig();
        Product tempproduct = product_mapper.getByName(product_name);
        return tempproduct != null;
    }

    public boolean isProductExistInTheSeller(String email, String product_name) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        Seller tempseller = seller_mapper.getById(temp.getId_user());
        Product tempproduct = product_mapper.getByName(product_name);
        if (tempproduct == null) {
            return false;
        } else {
            if (tempseller.getId_seller() == tempproduct.getId_seller()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isCartExist(String email) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        ArrayList<Cart> tempcart = cart_mapper.getById(temp.getId_user());
        if (tempcart.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isProductFromOwnShop(String email, String product_name) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        Seller tempseller = seller_mapper.getById(temp.getId_user());
        Product tempproduct = product_mapper.getByName(product_name);
        if (tempproduct == null || tempseller == null) {
            return false;
        } else {
            if (tempseller.getId_seller() == tempproduct.getId_seller()) {
                return true;
            } else {
                return false;
            }
        }
    }


    public boolean isProductExistinCart(String email, String product_name) throws IOException {
        readConfig();
        User tempuser = user_mapper.getByEmail(email);
        int id_user = tempuser.getId_user();
        Product tempproduct = product_mapper.getByName(product_name);
        int id_product = tempproduct.getId_product();
        Cart tempcart = new Cart();
        tempcart.setId_product(id_product);
        tempcart.setId_user(id_user);
        Cart tempcart2 = cart_mapper.getByIdProduct(tempcart);
        return tempcart2 != null;
    }


    public boolean isStatusAwaitingPayment(int id_trans) throws IOException {
        readConfig();
        Transaction temptrans = trans_mapper.getById(id_trans);
        return temptrans.getTrans_status().equals("Order Requested");
    }

    public boolean haveAnyPaymentMethodChosenYet(int id_trans) throws IOException {
        readConfig();
        Transaction temptrans = trans_mapper.getById(id_trans);
        return temptrans.getPayment_method() != null;
    }

    public boolean isTransExistInSeller(String email, int id_trans) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        Seller tempseller = seller_mapper.getById(temp.getId_user());
        if (tempseller == null) {
            return false;
        }
        Transaction temptrans = trans_mapper.getById(id_trans);
        if (temptrans == null) {
            return false;
        } else {
            return tempseller.getId_seller() == temptrans.getId_seller();
        }
    }

    public boolean isStatusPaymentDone(int id_trans) throws IOException {
        readConfig();
        Transaction temptrans = trans_mapper.getById(id_trans);
        return temptrans.getTrans_status().equals("Payment Done");
    }

    public boolean isStatusOrderReceived(int id_trans) throws IOException {
        readConfig();
        Transaction temptrans = trans_mapper.getById(id_trans);
        return temptrans.getTrans_status().equals("Order Received By Seller");
    }
}
