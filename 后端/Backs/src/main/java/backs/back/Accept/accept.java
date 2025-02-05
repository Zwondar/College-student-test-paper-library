package backs.back.Accept;

import backs.back.Entity.Sel;
import backs.back.Entity.Study;
import backs.back.Entity.User;
import backs.back.Sever.UsersSever;
import backs.back.disposition.AliOSSUtils;
import backs.back.disposition.JwtUtils;
import backs.back.disposition.Result;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class accept {
    //该方法用于创建验证密码
    @Autowired
    UsersSever userssever;
    AliOSSUtils aliOSSUtils=new AliOSSUtils();
@PostMapping("/detection/log")
    public Result Detection (@RequestBody User user){
    User u=userssever.login(user);

    if(u!=null){
        Map<String,Object> m=new HashMap<>();
        m.put("authority",u.getAuthority());
        String str=String.valueOf(u.getAuthority());
        String jwt= JwtUtils.generateJwt(m);
        return Result.success(jwt,str);
      }
    else
    {
        return Result.error("账号或密码错误");
    }

    }
@PostMapping("/Log/verify")
    public Result verify(HttpServletRequest request){
    String jwt= request.getHeader("Token");
    Claims claims=JwtUtils.parseJWT(jwt);
    Integer str=(Integer) claims.get("authority");
    return Result.success("str");
    }
    @PostMapping("/Sign")
public Result Sign(@RequestBody User user){
    User u=userssever.sign(user);
    if(u!=null) return Result.error("该账号已存在");
    userssever.signto(user);
    return Result.success("","创建账号成功");
 }
 @PostMapping("/Log/upload")
 public Result upload(String Data, MultipartFile File) throws IOException {

   String url=aliOSSUtils.upload(File);

    userssever.UpLoad(Data,url);
    return Result.success("数据创建成功");
 }
 @PostMapping("/Log/getData")
 public Result selData(@RequestBody Sel sel){
     List<Study> study=userssever.SelSever(sel);
     return Result.success(study);
 }
 @PostMapping("/Log/lookData")
public Result lookData(String name){
    String url=userssever.loo(name);
    return Result.success(url);
}
}
