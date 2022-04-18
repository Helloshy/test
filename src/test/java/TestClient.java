import com.shy.rpc.test.IOrderService;
import com.shy.rpc.test.protocal.ShyHeader;
import com.shy.rpc.test.proxy.DefaultProxyFactory;
import com.shy.rpc.test.proxy.ProxyFactory;
import com.shy.rpc.test.register.SimpleRegisterCenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


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

    @Test
    public void TestRpcHeader(){
        ShyHeader header = new ShyHeader();
        for (int i = 0; i < 10 ; i++) {
            int length =  i* 100;
            new Thread(() ->{
                header.setFlag(ShyHeader.REQUEST_FLAG);
                header.setRequestId(UUID.randomUUID().getLeastSignificantBits());
                header.setDataLength(length);
                Thread.currentThread().setName("线程id:"+length/100);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(header);
                    System.out.println("线程id:"+length/100+",header长度: "+byteArrayOutputStream.toByteArray().length);
                }catch (Exception e){
                }
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(10);
        }catch (Exception e){

        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10 ; i++) {
            int length =  i* 100;
            new Thread(() ->{
                ShyHeader header = new ShyHeader();
                header.setFlag(ShyHeader.REQUEST_FLAG);
                header.setRequestId(UUID.randomUUID().getLeastSignificantBits());
                header.setDataLength(length);
                Thread.currentThread().setName("线程id:"+length/100);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(header);
                    System.out.println("线程id:"+length/100+",header长度: "+byteArrayOutputStream.toByteArray().length);
                }catch (Exception e){
                }
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(10);
        }catch (Exception e){

        }
    }
}
