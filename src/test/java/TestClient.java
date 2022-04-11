import com.shy.rpc.test.IOrderService;
import com.shy.rpc.test.proxy.DefaultProxyFactory;
import com.shy.rpc.test.proxy.ProxyFactory;
import com.shy.rpc.test.register.SimpleRegisterCenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClient {
    @Test
    public void testClient(){
        ProxyFactory factory = new DefaultProxyFactory(new SimpleRegisterCenter());
        IOrderService orderService = factory.getProxy(IOrderService.class);
        int goodsId = 100;
        String order = orderService.createOrder(goodsId);
        System.out.println("订单号:" + order);
    }
}
