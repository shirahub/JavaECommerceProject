package com.service;

import com.handler.Handler;
import com.model.*;
import com.repository.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Payment_service {

    String bankPOST = "http://localhost:8082/bank/request";
    String emoneyPOST = "http://localhost:8082/emoney/pay";
    String merchantPOST = "http://localhost:8082/merchant/request";


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

    public String choosePayMethod(String email, int id_trans, String payment) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isTransExistInUser(email, id_trans)) {
                        if (handler.isStatusAwaitingPayment(id_trans)) {
                            if (!(handler.haveAnyPaymentMethodChosenYet(id_trans))) {
                                Transaction temptrans = trans_mapper.getById(id_trans);
                                switch (payment) {
                                    case "bank_transfer":
                                        int n3 = (int) Math.round(Math.random() * (999 - 100) + 100);
                                        float generateSum = temptrans.getTotal_cost() + n3;
                                        //tell the bank the sum and id_trans
                                        String post = generateSum + "=" + id_trans;
                                        String report = executePost(bankPOST, post);
                                        System.out.println(report);
                                        temptrans.setPayment_method("Bank Transfer");
                                        trans_mapper.updatePayMethod(temptrans);
                                        session.commit();
                                        return "Transfer Rp" + generateSum + " to BCA 31900031704 PT SHOPLEEN CERIA TBK.";
                                    case "bank_va":
                                        //generate virtual account from marketplace code and user's phone number
                                        String VAnumber = "7799" + temp.getPhone_number();
                                        //tell the bank the sum and id_trans
                                        generateSum = temptrans.getTotal_cost();
                                        post = generateSum + "=" + id_trans;
                                        report = executePost(bankPOST, post);
                                        System.out.println(report);
                                        temptrans.setPayment_method("Bank VA");
                                        trans_mapper.updatePayMethod(temptrans);
                                        session.commit();
                                        return "Transfer to your VAnumber: " + VAnumber + ".";
                                    case "emoney":
                                        generateSum = temptrans.getTotal_cost();
                                        int id_user = temp.getId_user();
                                        post = generateSum + "=" + id_user;
                                        //check user's account balance
                                        //auto pay if enough
                                        //reject pay if not enough
                                        report = executePost(emoneyPOST, post);
                                        if (report.contains("Success")) {
                                            temptrans.setPayment_method("Emoney");
                                            trans_mapper.updatePayMethod(temptrans);
                                            session.commit();
                                        }
                                        return report;
                                    case "merchant":
                                        generateSum = temptrans.getTotal_cost();
                                        post = generateSum + "=" + id_trans;
                                        report = executePost(merchantPOST, post);
                                        System.out.println(report);
                                        temptrans.setPayment_method("Merchant");
                                        trans_mapper.updatePayMethod(temptrans);
                                        session.commit();
                                        return "Pay to Merchant with Transaction id = " + id_trans + " Rp " + generateSum;
                                    //tell merchant the code and nominal
                                    default:
                                        return "Payment method not found.";
                                }
                            } else {
                                return "Cannot change payment method anymore.";
                            }
                        } else {
                            return "This transaction's status is not for choosing payment method.";
                        }
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

    public String executePost(String targetURL, String urlParameters)
    {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

}
