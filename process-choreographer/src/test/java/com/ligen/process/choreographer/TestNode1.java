package com.ligen.process.choreographer;

import org.springframework.stereotype.Component;

@Component
public class TestNode1 implements Node<TestContext> {

    @Override
    public String nodeName() {
        return "node1";
    }

    @Override
    public void execute(TestContext context) {
        System.out.println("node1 execute !!!!!!!!!");
    }
}
