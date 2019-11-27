package com.ligen.process.choreographer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ProcessRegister {

    private Map<String, Map<String, List<Node>>> processes = new HashMap<>();

    public void registerSingleProcess(String bizCode, String operation, List<Node> nodes) throws ProcessException {
        if (StringUtils.isEmpty(operation) || StringUtils.isEmpty(bizCode) || nodes == null || nodes.size() == 0) {
            throw new ProcessException("process definition is incomplete");
        }
        Map<String, List<Node>> bizCodeProcesses = processes.computeIfAbsent(bizCode, k -> new HashMap<>());
        List<Node> operationProcess = bizCodeProcesses.get(bizCode);
        if (operationProcess != null) {
            log.error("process is repeatedly defined， biz_code: {}, operation: {}, check your process definition", bizCode, operation);
            throw new ProcessException("process is repeatedly defined");
        }
        bizCodeProcesses.put(operation, nodes);
    }


    public List<Node> findProcess(String bizCode, String operation) throws ProcessException {
        if (StringUtils.isEmpty(operation) || StringUtils.isEmpty(bizCode)) {
            throw new ProcessException("null param");
        }
        Map<String, List<Node>> bizCodeProcesses = processes.get(bizCode);
        if (bizCodeProcesses == null) {
            return null;
        }
        return bizCodeProcesses.get(operation);
    }

}




