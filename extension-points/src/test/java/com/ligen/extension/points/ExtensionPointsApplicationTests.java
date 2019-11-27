package com.ligen.extension.points;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ExtensionPointsApplication.class)
@RunWith(SpringRunner.class)
class ExtensionPointsApplicationTests {

    @Autowired
    private ExtensionPointsLoader extensionPointsLoader;

    @Test
    void contextLoads() {
        TestAbstractExtPoints2 aep = extensionPointsLoader.getExtensionLoader(TestAbstractExtPoints2.class, "bizCode1");
        System.out.println(aep.test());

    }

}
