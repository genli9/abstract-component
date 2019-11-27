package com.ligen.process.choreographer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;


@SpringBootTest(classes = ProcessChoreographerApplication.class)
@RunWith(SpringRunner.class)
class ProcessChoreographerApplicationTests {

    @Autowired
    private ProcessTrigger processTrigger;

    @Test
    public void test1() {
        processTrigger.fire("code1", "operation1", new TestContext());
    }

}
