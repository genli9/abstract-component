package com.ligen.process.choreographer;

/**
 * todo 后期Context可以加入限定类型，提供某些方法
 * @param <T>
 */
public interface Node<T extends AbstractProcessContext> {

  String nodeName();

  void execute(T context);
}