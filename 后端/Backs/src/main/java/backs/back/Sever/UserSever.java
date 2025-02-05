package backs.back.Sever;

import backs.back.Data.UserMap;
import backs.back.Entity.Sel;
import backs.back.Entity.Study;
import backs.back.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserSever implements UsersSever{
    @Autowired
    UserMap usermap;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public User login(User user) {
        User u=usermap.datalogin(user);
        return u;
    }

    @Override
    public User sign(User user) {
        User u= usermap.datasign(user);
        return u;
    }

    @Override
    public void signto(User user) {
        usermap.datasignto(user);
    }

    @Override
    public void UpLoad(String data,String url) {
        data=data+","+url;
        String datas[]=data.split(",");

        usermap.dataupload(datas);
    }

    @Override
    public List<Study> SelSever(Sel sel) {
        List <Study>s=usermap.dataSel(sel);
        return s;
    }

    @Override
    public String loo(String name) {
        String result="";
      boolean isThen= this.SelectRedis(name,result);
      if(isThen)
      {
          String s=usermap.datalook(name);
          this.Insert(name,s);
          return s;
      }
      return result;
    }
  public boolean SelectRedis(String name,String result)
  {
      Object obj=redisTemplate.opsForValue().get(name);
      if(obj==null)
      {
          return true;
      }
     result=(String)obj;

    return false;
  }
   public void Insert (String name,String value)
   {
       redisTemplate.opsForValue().set(name,value);
   }
}
