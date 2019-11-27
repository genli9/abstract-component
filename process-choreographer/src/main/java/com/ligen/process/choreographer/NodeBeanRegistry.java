package com.ligen.process.choreographer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class NodeBeanRegistry implements BeanPostProcessor {

    private Map<String, Node> nodeRegistry = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if(o instanceof Node){
            doRegister((Node)o);
        }
        return o;
    }

    private void doRegister(Node node) {
        String nodeName = node.nodeName();
        if (nodeRegistry.get(nodeName) != null) {
            throw new ProcessException("process node duplicate definition");
        }
        nodeRegistry.put(node.nodeName(), node);
    }

    public Node findNodeByName(String nodeName) {
        return nodeRegistry.get(nodeName);
    }

}
