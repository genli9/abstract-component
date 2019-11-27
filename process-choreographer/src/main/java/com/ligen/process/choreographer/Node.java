package com.ligen.process.choreographer;

public interface Node<T> {

    String nodeName();

    void execute(T context);
}
