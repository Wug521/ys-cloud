package ys.office.monitor.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class YsMonitorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YsMonitorAdminApplication.class, args);
    }

}
