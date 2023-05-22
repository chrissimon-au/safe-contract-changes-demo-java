package au.chrissimon.safecontractchangesdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/")
    public String GetHealthCheck() {
        return "Safe Contract Changes Demo";
    }
}
