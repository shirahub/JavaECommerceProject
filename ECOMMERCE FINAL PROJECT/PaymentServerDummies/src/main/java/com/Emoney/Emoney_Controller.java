package com.Emoney;

import com.Emoney.Emoney;
import com.Emoney.Emoney_Repo;
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
@RequestMapping("/emoney")
public class Emoney_Controller {

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String requestTransfer(@RequestBody String fromEComm) throws Exception {

        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(Emoney_Repo.class);
        Emoney_Repo emoney_repo = session.getMapper(Emoney_Repo.class);

        String[] temp = fromEComm.split("[=]", 0);
        float bill = Float.parseFloat(temp[0]);
        int id_user = Integer.parseInt(temp[1]);

        Emoney tempmoney = emoney_repo.getById(id_user);
        if (tempmoney == null) {
            return "This user has no Emoney account";
        }

        if (tempmoney.getBalance() > bill) {
            float afterpay = tempmoney.getBalance() - bill;
            Emoney newtemp = new Emoney(id_user, afterpay);
            emoney_repo.updateBalance(newtemp);
            session.commit();
            session.commit();
            return "Payment with Emoney Success! Your balance has been deducted";
        } else {
            return "Insufficient balance in your Emoney account. ";
        }


    }
}