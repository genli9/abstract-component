package com.ligen.process.choreographer;

import com.ligen.process.choreographer.definition.ProcessDefinition;
import com.ligen.process.choreographer.definition.ProcessDefinitionParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProcessBuilder implements CommandLineRunner {

    @Autowired
    private ProcessDefinitionParser processDefinitionParser;

    @Autowired
    private ProcessRegistry processRegistry;

    @Autowired
    private NodeBeanRegistry nodeBeanRegistry;

    @Override
    public void run(String... strings) throws Exception {
        List<ProcessDefinition> definitions = processDefinitionParser.parse();
        if (definitions == null || definitions.size() == 0){
            log.info("no process definition found, skip building process");
            return;
        }
        for (ProcessDefinition definition : definitions) {
            List<Node> nodes = new ArrayList<>(definition.getNodeNames().size());
            for (String nodeName : definition.getNodeNames()) {
                Node node = nodeBeanRegistry.findNodeByName(nodeName);
                if (node == null) {
                    log.error("no node named : {}", nodeName);
                    throw new ProcessException("doesn't get any nodeObjects by name");
                }
                nodes.add(node);
            }
            processRegistry.registerSingleProcess(definition.getBizType(), definition.getBizCode(), nodes);
        }
    }
}
