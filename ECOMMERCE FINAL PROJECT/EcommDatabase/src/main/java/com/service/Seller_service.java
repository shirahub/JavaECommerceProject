package com.service;

import com.handler.Handler;
import com.model.Product;
import com.model.Seller;
import com.model.Transaction;
import com.model.User;
import com.repository.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;

public class Seller_service {
    SqlSession session = null;
    Seller_mapper seller_mapper = null;
    Product_mapper product_mapper = null;
    User_mapper user_mapper = null;
    Transaction_mapper trans_mapper = null;
    Handler handler = new Handler();
    String courier = "http://localhost:8082/courier/reqStatus";

    public void readConfig() throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(Product_mapper.class);
        session.getConfiguration().addMapper(Seller_mapper.class);
        session.getConfiguration().addMapper(User_mapper.class);
        session.getConfiguration().addMapper(Transaction_mapper.class);
        product_mapper = session.getMapper(Product_mapper.class);
        seller_mapper = session.getMapper(Seller_mapper.class);
        user_mapper = session.getMapper(User_mapper.class);
        trans_mapper = session.getMapper(Transaction_mapper.class);
    }



    public String createProduct(String email, Product product) throws IOException {
        //no negative cases: same name is allowed
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isUserSeller(email)) {
                        if (handler.isShopActive(email)) {
                            if (handler.isProductNameExist(product.getProduct_name())) {
                                return "Please choose different name for the product.";
                            } else {
                                Seller tempseller = seller_mapper.getById(temp.getId_user());
                                product.setId_seller(tempseller.getId_seller());
                                product_mapper.inputProduct(product);
                                seller_mapper.updateProduct(tempseller);
                                session.commit();
                                session.close();
                                return "Product has been created";
                            }
                        } else {
                            return "This account's shop is banned. Cannot input product.";
                        }
                    } else {
                        return "This account has no shop opened yet. ";
                    }
                } else {
                    return "User is banned. No Profile to be shown. ";
                }
            } else {
                return  "User not logged in. Please Login first. ";
            }
        } else {
            return "User not found. ";
        }
    }

    public String deleteProduct(String email, Product product) throws IOException {
        //negative case: product not found
        //name and id cannot be updated because it has become link
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isUserSeller(email)) {
                        if (handler.isShopActive(email)) {
                            if (handler.isProductExistInTheSeller(email, product.getProduct_name())) {
                                Seller tempseller = seller_mapper.getById(temp.getId_user());
                                product.setId_seller(tempseller.getId_seller());
                                product_mapper.deleteProduct(product);
                                seller_mapper.UpdateProductDelete(tempseller);
                                session.commit();
                                session.close();
                                return "Product has been deleted";
                            } else {
                                return "Product does not belong to this account's shop";
                            }
                        } else {
                            return "This account's shop is banned. Cannot input product.";
                        }
                    } else {
                        return "This account has no shop opened yet. ";
                    }
                } else {
                    return "User is banned. No Profile to be shown. ";
                }
            } else {
                return  "User not logged in. Please Login first. ";
            }
        } else {
            return "User not found. ";
        }
    }

    public String updateProduct(String email, Product product) throws IOException {
        //negative case: product not found
        //name and id cannot be updated because it has become link
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isUserSeller(email)) {
                        if (handler.isShopActive(email)) {
                            if (handler.isProductExistInTheSeller(email, product.getProduct_name())) {
                                Seller tempseller = seller_mapper.getById(temp.getId_user());
                                product.setId_seller(tempseller.getId_seller());
                                product_mapper.updateProduct(product);
                                session.commit();
                                session.close();
                                return "Product has been updated";
                            } else {
                                return "Product does not belong to this account's shop";
                            }
                        } else {
                            return "This account's shop is banned. Cannot edit product.";
                        }
                    } else {
                        return "This account has no shop opened yet. ";
                    }
                } else {
                    return "User is banned. No Profile to be shown. ";
                }
            } else {
                return  "User not logged in. Please Login first. ";
            }
        } else {
            return "User not found. ";
        }
    }

    public String confirmOrderReceived(String email, int id_trans) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isTransExistInSeller(email, id_trans)) {
                        if (handler.isStatusPaymentDone(id_trans)) {
                            Date today = new Date(System.currentTimeMillis());
                            Transaction temptrans = trans_mapper.getById(id_trans);
                            temptrans.setOrderReceived(today);
                            trans_mapper.updateOrderReceived(temptrans);
                            session.commit();
                            session.close();
                            return "Order has been received in Seller.";
                        } else {
                            return "Payment has not been done by Buyer, cannot accept order";
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

    public String checkOrderArrived(String email, int id_trans) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isTransExistInSeller(email, id_trans)) {
                        boolean orderArrived = executePost(courier);
                        if (orderArrived) {
                            Date today = new Date(System.currentTimeMillis());
                            Transaction temptrans = trans_mapper.getById(id_trans);
                            temptrans.setOrderArrived(today);
                            trans_mapper.updateOrderArrived(temptrans);
                            session.commit();
                            session.close();
                            return "Order has arrived.";
                        } else {
                            return "Order has not arrived";
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



    public String confirmOrderShipped(String email, int id_trans, String tracking) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isTransExistInSeller(email, id_trans)) {
                        if (handler.isStatusOrderReceived(id_trans)) {
                            Date today = new Date(System.currentTimeMillis());
                            Transaction temptrans = trans_mapper.getById(id_trans);
                            temptrans.setOrderShipped(today);
                            temptrans.setOrderTrackingNumber(tracking);
                            trans_mapper.updateOrderShipped(temptrans);
                            session.commit();
                            session.close();
                            return "Order has been shipped by Seller.";
                        } else {
                            return "Order has not been received by Seller.";
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
    public boolean executePost(String targetURL)
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
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();

            String a = response.toString();
            if (a.equals("true")) {
                return true;
            } else {
                return  false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

}
