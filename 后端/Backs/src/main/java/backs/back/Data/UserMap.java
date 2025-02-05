package backs.back.Data;

import backs.back.Entity.Sel;
import backs.back.Entity.Study;
import backs.back.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMap
{
    @Select("select authority from users where username=#{username} and password=#{password}")
    User datalogin(User user);
    @Select("select id from users where username=#{username}")
    User datasign(User user);
    @Insert("insert into users (username, password, authority) VALUE (#{username},#{password},1)")
    void datasignto(User user);
    void dataupload(String[] datas);
    @Select("SELECT * FROM study WHERE grade LIKE CONCAT('%', #{select}, '%') AND name LIKE CONCAT('%', #{input}, '%')")
    List<Study> dataSel(Sel sel);
    @Select("select study.url from study where name=#{name}")
    String datalook(String name);
}
