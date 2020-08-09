package com.xs.other.dubbo.demo;

import com.xs.other.dubbo.demo.service.TestService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.concurrent.CountDownLatch;

/**
 * @author xs
 * create time:2020-08-02 16:00
 **/
public class ConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        if (isClassic(args)) {
            runWithRefer();
        } else {
            runWithBootstrap();
        }
    }

    private static boolean isClassic(String[] args) {
        return args.length > 0 && "classic".equalsIgnoreCase(args[0]);
    }

    private static void runWithBootstrap() throws InterruptedException {
        ReferenceConfig<TestService> reference = new ReferenceConfig<>();
        reference.setInterface(TestService.class);
        reference.setGeneric("true");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("dubbo-demo-api-consumer"))
                .registry(new RegistryConfig("redis://127.0.0.1:6379"))
                .reference(reference)
                .start();

        TestService demoService = ReferenceConfigCache.getCache().get(reference);
        int addResult = demoService.add(1,3);
        System.out.println(addResult);

        // generic invoke
        GenericService genericService = (GenericService) demoService;
        Object genericInvokeResult = genericService.$invoke("print", new String[] { String.class.getName() },
                new Object[] { "dubbo generic invoke" });
        System.out.println(genericInvokeResult);
        System.out.println(demoService.getClass().getName() + ":" + demoService.hashCode() + "....");
        new CountDownLatch(1).await();
    }

    private static void runWithRefer() {
        ReferenceConfig<TestService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("dubbo-demo-api-consumer"));
        reference.setRegistry(new RegistryConfig("redis://127.0.0.1:6379"));
        reference.setInterface(TestService.class);
        TestService service = reference.get();
        String message = service.print("dubbo");
        System.out.println(message);
    }
}
