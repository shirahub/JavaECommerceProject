package com.Bank;

import com.Merchant.Merchant;
import com.Merchant.Merchant_Repo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/merchant")
public class Merchant_Controller {

    @RequestMapping(value = "/reqStatus", method = RequestMethod.POST)
    public boolean requestStatus(@RequestBody String fromEComm) throws Exception {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(Merchant_Repo.class);
        Merchant_Repo merchant_repo = session.getMapper(Merchant_Repo.class);
        fromEComm = fromEComm.replace("=","");
        int id_trans = Integer.parseInt(fromEComm);

        Merchant merchant = merchant_repo.getById(id_trans);
        session.commit();
        session.close();

        return merchant.isStatus();
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String requestTransfer(@RequestBody String fromEComm) throws Exception {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(Merchant_Repo.class);
        Merchant_Repo merchant_repo = session.getMapper(Merchant_Repo.class);

        String[] temp = fromEComm.split("[=]", 0);
        float nominal = Float.parseFloat(temp[0]);
        int id_trans = Integer.parseInt(temp[1]);
        Merchant merchant = new Merchant(id_trans, nominal);

        merchant_repo.inputToMerchantDB(merchant);
        session.commit();
        session.close();
        return "Merchant : Payment Request has been accepted";
    }
}