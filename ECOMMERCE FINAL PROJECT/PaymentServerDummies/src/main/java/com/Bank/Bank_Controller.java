package com.Bank;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Reader;

@RestController
@RequestMapping("/bank")
public class Bank_Controller {

    @RequestMapping(value = "/reqStatus", method = RequestMethod.POST)
    public boolean requestStatus(@RequestBody String fromEComm) throws Exception {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(Bank_Repo.class);
        Bank_Repo bank_repo = session.getMapper(Bank_Repo.class);
        fromEComm = fromEComm.replace("=","");

        int id_trans = Integer.parseInt(fromEComm);

        Bank bank = bank_repo.getById(id_trans);
        session.commit();
        session.close();
        return bank.isStatus();
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String getTransfer(@RequestBody String fromEComm) throws Exception {

        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(Bank_Repo.class);
        Bank_Repo bank_repo = session.getMapper(Bank_Repo.class);

        String[] temp = fromEComm.split("[=]", 0);
        float nominal = Float.parseFloat(temp[0]);
        int id_trans = Integer.parseInt(temp[1]);
        Bank bank = new Bank(id_trans, nominal);

        bank_repo.inputToBankDB(bank);
        session.commit();
        session.close();
        return "BANK : Payment Request has been accepted";
    }
}
