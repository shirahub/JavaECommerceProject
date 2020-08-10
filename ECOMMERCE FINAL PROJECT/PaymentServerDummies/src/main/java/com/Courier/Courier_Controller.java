package com.Courier;

import com.Bank.Bank;
import com.Bank.Bank_Repo;
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
@RequestMapping("/courier")
public class Courier_Controller {

    //THIS IS JUST A DUMMY which always returns true

        @RequestMapping(value = "/reqStatus", method = RequestMethod.POST)
        public boolean requestStatus() throws Exception {
            return true;
        }

}
