package com.model;

public class Address {
    int id_user;
    String address_name;
    String receiver_name;
    String phone_number;
    String address;
    String province;
    int zipcode;
    int id_address;

    public Address() {
    }

    public int getId_address() {
        return id_address;
    }

    public void setId_address(int id_address) {
        this.id_address = id_address;
    }

//    @Override
//    public String toString() {
//        return "Address{" +
//                "id_user=" + id_user +
//                ", address_name='" + address_name + '\'' +
//                ", receiver_name='" + receiver_name + '\'' +
//                ", phone_number='" + phone_number + '\'' +
//                ", address='" + address + '\'' +
//                ", province='" + province + '\'' +
//                ", zipcode=" + zipcode +
//                '}';
//    }

    public Address(int id_user, String address_name) {
        this.id_user = id_user;
        this.address_name = address_name;
    }

    public Address(String address_name, String receiver_name, String phone_number, String address, String province, int zipcode) {
        this.address_name = address_name;
        this.receiver_name = receiver_name;
        this.phone_number = phone_number;
        this.address = address;
        this.province = province;
        this.zipcode = zipcode;
    }

    public Address(int id_user, String address_name, String receiver_name, String phone_number, String address, String province, int zipcode, int id_address) {
        this.id_user = id_user;
        this.address_name = address_name;
        this.receiver_name = receiver_name;
        this.phone_number = phone_number;
        this.address = address;
        this.province = province;
        this.zipcode = zipcode;
        this.id_address = id_address;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
}
