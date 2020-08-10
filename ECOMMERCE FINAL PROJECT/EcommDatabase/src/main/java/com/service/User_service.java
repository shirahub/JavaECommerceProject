package com.service;

import com.google.gson.Gson;
import com.handler.Handler;
import com.model.*;
import com.repository.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class User_service {
    SqlSession session = null;
    User_mapper user_mapper = null;
    Seller_mapper seller_mapper = null;
    Address_mapper address_mapper = null;
    Product_mapper product_mapper = null;
    Transaction_mapper trans_mapper = null;
    Cart_mapper cart_mapper = null;
    Handler handler = new Handler();
    Gson gson = new Gson();


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



    public String accountConfirmation(String email) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        session.commit();
        if (!(handler.isUserExist(email))) {
            return "User not found. ";
        } else {
            if (handler.isUserStillLoggedIn(email)) {
                user_mapper.accountConfirmation(temp);
                session.commit();
                return "User account confirmed! Happy Shopping at Shopleen!!";
            } else {
                return "User not logged in. Please Login first. ";
            }
        }
    }

    //login
    public String userLogin(String email, String password) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (email.equals(temp.getEmail()) && password.equals(temp.getUser_pwd())) {
                user_mapper.login(temp);
                session.commit();
                session.close();
                return "Login: Success";
            } else {
                session.commit();
                session.close();
                return "Login: Failed - Wrong Password";
            }
        } else {
            session.commit();
            session.close();
            return "Login: Failed - User with email " + email + " not found.";
        }
    }

    //logout
    public String userLogout(String email) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            user_mapper.logout(temp);
            session.commit();
            session.close();
            return "Logout: Success";
        } else {
            session.commit();
            session.close();
            return "Logout: Failed - User with email " + email + " not found.";
        }
    }

    //showProfile
    public String showProfile(String email) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        session.commit();
        session.close();
        if (handler.isUserExist(email)) {
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    String json = gson.toJson(temp);
                    return json;
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

    //editProfile
    public String editPwd(String email, User user) throws IOException {
        readConfig();
        user.setEmail(email);
        if (handler.isUserExist(email)) {
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    user_mapper.update(user);
                    session.commit();
                    session.close();
                    return "Update Password: Success";
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


    //registration
    public String register(User user) throws IOException {
        readConfig();
        String report = "";
        // invalid email format and password check according to regex
        String regexu = "^(.+)@(.+)$";
        boolean emailformat = Pattern.compile(regexu).matcher(user.getEmail()).matches();
        if (!emailformat) {
            report += "Invalid email format. ";
        }
        String regexp = "((?=.*[a-z])(?=.*[@#$%])(?=.*[A-Z]).{8,})";
        boolean pwdformat = Pattern.compile(regexp).matcher(user.getUser_pwd()).matches();
        if (!pwdformat) {
            report += "Invalid password format (Please use 1 Capital Letter, 1 Special Char, minimum 8 char) ";
        }

        // check if user exists
        User temp = user_mapper.getByEmail(user.getEmail());
        User temp2 = user_mapper.getByPhone(user.getPhone_number());
        if (emailformat && pwdformat) {
            if (temp == null && temp2 == null) {
                user_mapper.inputToDB(user);
                report += "Registration Success! Please Login and confirm your account. ";
            } else {
                //check if email already used
                if (temp != null) {
                    report += "Email already registered, please use different email. ";
                }
                //check if phone number already used
                if (temp2 != null) {
                    report += "Phone number already registered, please use different phone number. ";
                }
            }
        }
        session.commit();
        session.close();
        return report;
    }

    public String makeShop(String email, String shop_name, String shop_location) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        if (!(handler.isUserExist(email))) {
            return "User not found. ";
        } else {
            if (handler.isUserStillLoggedIn(email)) {
                if (temp.getUser_status()) {
                    //check if seller already has a shop
                    if (temp.isSeller()) {
                        return "Already has a shop, cannot make a new one for this user account. ";
                    }
                    //check if shop_name already existed
                    Seller tempseller = seller_mapper.getByName(shop_name);
                    if (tempseller == null) {
                        //user has a shop variable set true
                        readConfig();
                        user_mapper.becomeSeller(temp);

                        //make a shop
                        int id_user = temp.getId_user();
                        Seller seller = new Seller(id_user, shop_name, shop_location);

                        seller_mapper.makeShop(seller);
                        session.commit();
                        session.close();
                        return "Shop " + shop_name + " has been created for user " + email + ". ";
                    } else {
                        return "Shop name already existed, please use different name.";
                    }
                } else {
                    return "User is banned. No Profile to be shown. ";
                }
            } else {
                return "User not logged in. Please Login first. ";
            }
        }
    }

    public String viewShopAsBuyer(String shop_name) throws IOException {
        readConfig();
        if (handler.isShopExist(shop_name)) {
            Seller temp = seller_mapper.getByName(shop_name);
            String json = gson.toJson(temp);
            return json;
        } else {
            return "No Shop found with the name " + shop_name;
        }

    }

    public String viewShopAsSeller(String email) throws IOException {
        readConfig();
        User temp = user_mapper.getByEmail(email);
        if (handler.isUserExist(email)) {
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (temp.isSeller()) {
                        Seller sellertemp = seller_mapper.getById(temp.getId_user());
                        session.commit();
                        session.close();
                        String json = gson.toJson(sellertemp);
                        return json;
                    } else {
                        return "This account has no shop opened yet. ";
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

    public String viewProduct(String product_name) throws IOException {
        readConfig();
        if (handler.isProductNameExist(product_name)) {
            Product temp = product_mapper.getByName(product_name);
            String json = gson.toJson(temp);
            return json;
        } else {
            return "Product not found.";
        }
    }

    public String inputAddress(String email, Address address) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            address.setId_user(temp.getId_user());
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isAddressNameExist(email, address.getAddress_name())) {
                        return "Address with this name already exists, please use different name. ";
                    } else {
                        address_mapper.inputAddress(address);
                        session.commit();
                        session.close();
                        return "Input new address success!";
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

    public String editAddress(String email, Address address) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            address.setId_user(temp.getId_user());
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isAddressExistInUser(email, address.getId_address())) {
                            address_mapper.updateAddress(address);
                            session.commit();
                            session.close();
                            return "Edit address success!";
                    } else {
                        return "The address not found on this account";
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

    public String showAllAddress(String email) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    ArrayList<Address> array = address_mapper.getById(temp.getId_user());
                    session.commit();
                    session.close();
                    if (array.size() == 0) {
                        return "This account has no address yet";
                    }
                    JSONArray jsonarray = new JSONArray();

                    for (Address a : array) {
                        String stringaddress = gson.toJson(a);
                        System.out.println(stringaddress);
                        jsonarray.add(stringaddress);
                    }
                    return jsonarray.toJSONString();
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


    public String deleteAddress(String email, Address address) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            address.setId_user(temp.getId_user());
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isAddressExistInUser(email, address.getId_address())) {
                        address_mapper.deleteAddress(address);
                        session.commit();
                        session.close();
                        return "Delete address success!";
                    } else {
                        return "The address not found on this account";
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


    public String addToCart(String email, String product_name, int qty) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isProductNameExist(product_name)) {
                        if (!(handler.isProductFromOwnShop(email, product_name))) {
                            if (handler.isShopOfTheProductActive(product_name)) {
                                Product tempproduct = product_mapper.getByName(product_name);
                                int id_user = temp.getId_user();
                                int id_product = tempproduct.getId_product();
                                Cart cart = new Cart(id_user, id_product,qty);
                                cart_mapper.inputtoCart(cart);
                                session.commit();
                                session.close();
                                return "Product added to cart!";
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



    public String viewCart(String email) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isCartExist(email)) {
                        ArrayList<Cart> tempcart = cart_mapper.getById(temp.getId_user());
                        float totalprice = 0f;
                        int product_qty = 0;
                        JSONObject jobj = new JSONObject();
                        JSONArray jarray = new JSONArray();
                        for (int i = 0; i<tempcart.size();i++) {
                            Product tempproduct = product_mapper.getByIdProduct(tempcart.get(i).getId_product());
                            tempproduct.setProduct_quantity(tempcart.get(i).getQuantity());
                            totalprice += tempproduct.getProduct_price()*tempcart.get(i).getQuantity();
                            product_qty += tempcart.get(i).getQuantity();
                            jarray.add(tempproduct);
                        }
                        jobj.put("products_in_cart", jarray);
                        jobj.put("total_qty", product_qty);
                        jobj.put("total_price",totalprice);
                        String json = gson.toJson(jobj);
                        return json;
                    } else {
                        return "Cart is empty";
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


    public String removeFromCart(String email, String product_name) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isCartExist(email)) {
                        if (handler.isProductExistinCart(email, product_name)) {
                            int id_user = temp.getId_user();
                            Product tempproduct = product_mapper.getByName(product_name);
                            int id_product = tempproduct.getId_product();
                            Cart tempcart = new Cart();
                            tempcart.setId_product(id_product);
                            tempcart.setId_user(id_user);
                            cart_mapper.deleteAProduct(tempcart);
                            session.commit();
                            session.close();
                            return "Product " + product_name + " has been removed from cart.";
                        } else {
                            return "Product not found in cart.";
                        }
                    } else {
                        return "Cart is empty";
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


    public String emptyCart(String email) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isCartExist(email)) {
                        cart_mapper.deleteCart(temp.getId_user());
                        session.commit();
                        session.close();
                        return "Cart has been emptied";
                    } else {
                        return "Cart is already empty";
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

    public String confirmOrderDone(String email, int id_trans) throws IOException {
        readConfig();
        if (handler.isUserExist(email)) {
            User temp = user_mapper.getByEmail(email);
            if (handler.isUserStillLoggedIn(email)) {
                if (handler.isUserActive(email)) {
                    if (handler.isTransExistInUser(email, id_trans)) {
                        Date today = new Date(System.currentTimeMillis());
                        Transaction temptrans = trans_mapper.getById(id_trans);
                        temptrans.setOrderConfirmed(today);
                        trans_mapper.updateOrderConfirmed(temptrans);
                        float money = temptrans.getTotal_cost();
                        Seller tempseller = seller_mapper.getByIdSeller(temptrans.getId_seller());
                        money = tempseller.getBalance() + money;
                        tempseller.setBalance(money);
                        int rating = tempseller.getShop_rating() + 1;
                        tempseller.setShop_rating(rating);
                        seller_mapper.updateBalance(tempseller);
                        session.commit();
                        session.close();
                        return "Order is complete. Balance forwarded to Seller. ";
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
