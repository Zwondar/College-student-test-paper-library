package backs.back.Sever;

import backs.back.Entity.Sel;
import backs.back.Entity.Study;
import backs.back.Entity.User;

import java.util.List;

public interface UsersSever {
    public User login(User user);

    public User sign(User user);

    public void signto(User user);

    public void UpLoad(String data,String url);

    public List<Study> SelSever(Sel sel);

    public String loo(String name);

    public boolean SelectRedis(String name,String result);

    public void Insert(String name,String value);
}
