package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.Kexie.dao.UserDao;
import com.example.Kexie.domain.Data;
import com.example.Kexie.domain.Result;
import com.example.Kexie.domain.User;
import jakarta.servlet.http.HttpSession;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Transactional
public class UserController {

    @Autowired
    UserDao userDao;
    ExpiringMap<String, String> map = ExpiringMap.builder()
            .expiration(300, TimeUnit.SECONDS)
            .variableExpiration().expirationPolicy(ExpirationPolicy.CREATED).build();
    @PostMapping("/register")
    public Result add (@RequestBody User user)
    {
        Result result = new Result();
        if (user.getUserName()!=null&&user.getPassword()!=null)
        {
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getUserName,user.getUserName());
            System.out.println("条件设置成功");
            User user1 =userDao.selectOne(lqw);
            System.out.println("查询成功");
            if(user1==null)
            {
                user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
                userDao.insert(user);
                result.setStatus("registerSucceed");
            }
            else {
                result.setStatus("renameFail");
            }
        }
        else {
            result.setStatus("nullAgainst");
        }
        return result;
    };
    @PostMapping("/login")
    public Result login (@RequestBody User user, HttpSession httpSession)
    {
        Result result = new Result();
        String userName = user.getUserName();
        String password = user.getPassword();
        User user1 = userDao.selectOne( new LambdaQueryWrapper<User>().eq(User::getUserName,userName));
        if(user1 == null)
        {
            result.setStatus("UserNotExist");
        }
        else if (user1.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            result.setStatus("loginSucceed");
            httpSession.setAttribute("userId",user1.getId());
        }
        else {
            result.setStatus("PasswordWrong");
        }
        return result;
    };
    @PostMapping("/changePassword")
    public Result changeRequest(@RequestBody Data data) throws EmailException {
        Result result = new Result();
        StringBuffer sb=new StringBuffer();
        String userName = data.getUserName();
        if (userDao.selectOne( new LambdaQueryWrapper<User>().eq(User::getUserName,userName))==null){
            result.setStatus("UserNotExist");
        }
        else if(data.getType().equals("changePasswordRequest"))
        {
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getUserName,data.getUserName());
            User user=userDao.selectOne(lqw);
            String E_mail =user.getEmail();
            HtmlEmail email=new HtmlEmail();//创建一个HtmlEmail实例对象
            email.setHostName("smtp.qq.com");//邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
            email.setCharset("utf-8");//设置发送的字符类型
            email.addTo(E_mail);//设置收件人
            email.setFrom("1944148429@qq.com","YourBrother");//发送人的邮箱为自己的，用户名可以随便填
            email.setAuthentication("1944148429@qq.com","ceaptvakhswcbjed");//设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
            email.setSubject("测试");//设置发送主题
            String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random random=new Random();
            for(int i=0;i<5;i++){
                int number=random.nextInt(62);
                sb.append(str.charAt(number));
            }
            map.put(data.getUserName(),sb.toString());
            String code =map.get(data.getUserName());
            System.out.println("map中验证码是"+code);
            System.out.println("map中的数据数是"+map.size());
            email.setMsg("验证码是"+sb+"。300秒后过期");//设置发送内容
            email.send();//进行发送
            result.setStatus("emailSented");
        }
        else if(data.getType().equals("changePasswordTest"))
        {
            String code =map.get(data.getUserName());
            System.out.println("再次获取map中的数据数是"+map.size());
            System.out.println("再次获取map中的验证码是"+code);
            if(code==null)
            {
                result.setStatus("changePasswordFail");
            }
            else if(data.getCode().equals(code))
            {
                User user = new User();
                user.setPassword(DigestUtils.md5DigestAsHex(data.getPassword().getBytes()));
                LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
                lqw.eq(User::getUserName,data.getUserName());
                userDao.update(user,lqw);
                result.setStatus("changePasswordSucceed");
            }
        }
        return result;
    }
}
