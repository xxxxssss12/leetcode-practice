package com.xs.other.dubbo.demo;

import com.xs.other.dubbo.demo.service.TestService;
import com.xs.other.dubbo.demo.service.TestServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import java.util.concurrent.CountDownLatch;

/**
 * @author xs
 * create time:2020-08-02 16:01
 **/
public class ProviderDemo {

    public static void main(String[] args) throws Exception {
        if (isClassic(args)) {
            startWithExport();
        } else {
            startWithBootstrap();
        }
    }

    private static boolean isClassic(String[] args) {
        return args.length > 0 && "classic".equalsIgnoreCase(args[0]);
    }

    private static void startWithBootstrap() {
        ServiceConfig<TestServiceImpl> service = new ServiceConfig<>();
        service.setInterface(TestService.class);
        service.setRef(new TestServiceImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("dubbo-demo-api-provider"))
                .registry(new RegistryConfig("redis://127.0.0.1:6379"))
                .service(service)
                .start()
                .await();
    }

    private static void startWithExport() throws InterruptedException {
        ServiceConfig<TestServiceImpl> service = new ServiceConfig<>();
        service.setInterface(TestService.class);
        service.setRef(new TestServiceImpl());
        service.setApplication(new ApplicationConfig("dubbo-demo-api-provider"));
        service.setRegistry(new RegistryConfig("redis://127.0.0.1:6379"));
        service.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
