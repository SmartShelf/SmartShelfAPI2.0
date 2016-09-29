package com.sogeti.smartshelf.web;

import com.sogeti.smartshelf.service.DataService;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author fabdin
 */
@Controller
@RequestMapping("main")
public class MainController {
    
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity isAuthenticated(HttpSession session) {

        System.out.println("login");
        if (true) {
            return new ResponseEntity("Welcome to Sogeti IoT SmartShelf",HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    
        @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ResponseEntity getUserInfo(HttpSession session) throws MalformedURLException {
        
        
        DataService test = new DataService();
        test.testConnection();
        

        System.out.println("UserInfo");
        
        User user=new User();
        user.setName("Sogeti Guy");
        user.setUsername("abdc");
        
        return new ResponseEntity(user, HttpStatus.IM_USED);
    }
        
}
