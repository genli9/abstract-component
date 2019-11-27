package com.ligen.process.choreographer;

import org.springframework.stereotype.Component;

@Component
public class TestNode2 implements Node<TestContext> {

    @Override
    public String nodeName() {
        return "node2";
    }

    @Override
    public void execute(TestContext context) {
        System.out.println("node2 execute !!!!!!!");
    }
}
