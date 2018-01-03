import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * Created by dllo on 18/1/2.
 */
public class test {

    @Test
    public void test1() {

//        首先构建securityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
//        创建securityManager对象
        SecurityManager securityManager = factory.getInstance();
//        配置securityManager到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
//        创建一个subject
        Subject subject = SecurityUtils.getSubject();
//        创建一个token(令牌)，记录要进行认证的用户名和密码
        System.out.println(subject);
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "1234");

//        登录认证
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("认证失败");
        }
        boolean result = subject.isAuthenticated();
        System.out.println("用户认证状态：" + result);


//        权限验证
//        1.判断用户是否拥有某个角色
//        方法一
        System.out.println("是否拥有某个角色：" + subject.hasRole("CEO"));


//        方法四：check系列方法
//        如果匹配，什么都不会发生；如果不匹配，系统抛出异常
//        subject.checkRole("CEO");

//        2.判断用户是否拥有某个权限
        System.out.println("是否拥有某个权限："+subject.isPermitted("user:query"));
        subject.checkPermission("user:create");


//        登出
        subject.logout();
        result = subject.isAuthenticated();
        System.out.println("用户认证状态：" + result);
    }


    /**
     * 认证的执行流程：
     *
     * 1.创建token，token中包含用户提交的认证信息：账号和密码
     * 2.执行subject.login(token)，最终由securityManager通过Authentication进行认证。
     * 3.Authentication的某个实现类调用realm从ini配置文件中提取用户信息，（IniRealm）
     * 4.IniRealm先根据token中的账号去ini中找该账号，如果找不到抛出异常，如果找到再去找密码，都匹配成功则认证通过。
     *
     * 常见的异常：UnknownAccountException，IncorrectCredentialsException
     */


    @Test
    public void test2() {
//        散列方法，生成一段文本的摘要信息，散列逆，常用语对密码进行散列。
//        常用的散列算法：MD5，SHA-1
        String password = "1234";
        String password_md5 = new Md5Hash(password).toString();
        System.out.println(password_md5);
//        加盐（salt）
//        加盐目的是保护密码信息的安全性

        String password_nd5_salt = new Md5Hash(password_md5, "asdf").toString();
        System.out.println(new Md5Hash(password_md5, "qwer").toString());

//        加盐所需要的信息：盐和加盐的次数在每次修改密码的时候随机生成，保存在用户密码中

        System.out.println(new Md5Hash(password, "asdfqwer").toString());
    }


    // 方法二：注解
    @RequiresRoles("CEO")
    public void test3(){

    }
    //f方法三：jsp标签
    /**
     *
     * <shiro:hasRole name="CEO">
     *     有权限
     * </shiro:hasRole>
     *
     */
}
