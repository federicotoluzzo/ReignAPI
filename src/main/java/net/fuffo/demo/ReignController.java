package net.fuffo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReignController {

    @GetMapping("/reign")
    public String getReignInfo() {
        return new Test("test", 420).toString();
    }

    private class Test{
        public String name;
        public int age;
        public Test(String name, int age){
            this.name = name;
            this.age = age;
        }
    }
}