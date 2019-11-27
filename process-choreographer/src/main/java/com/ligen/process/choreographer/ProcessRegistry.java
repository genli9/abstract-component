package com.ligen.process.choreographer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ProcessRegistry {

    private Map<String, Map<String, List<Node>>> processes = new HashMap<>();

    public void registerSingleProcess(String bizType, String bizCode, List<Node> nodes) throws ProcessException {
        if (StringUtils.isEmpty(bizType) || StringUtils.isEmpty(bizCode) || nodes == null || nodes.size() == 0) {
            throw new ProcessException("process definition is incomplete");
        }
        Map<String, List<Node>> bizTypeProcesses = processes.computeIfAbsent(bizType, k -> new HashMap<>());
        List<Node> bizCodeProcess = bizTypeProcesses.get(bizCode);
        if (bizCodeProcess != null) {
            log.error("process is repeatedly defined， biz_type: {}, biz_code: {}, check your process definition", bizType, bizCode);
            throw new ProcessException("process is repeatedly defined");
        }
        bizTypeProcesses.put(bizCode, nodes);
    }


    public List<Node> findProcess(String bizType, String bizCode) throws ProcessException {
        if (StringUtils.isEmpty(bizType) || StringUtils.isEmpty(bizCode)) {
            throw new ProcessException("null param");
        }
        Map<String, List<Node>> bizTypeProcesses = processes.get(bizType);
        if (bizTypeProcesses == null) {
            return null;
        }
        return bizTypeProcesses.get(bizCode);
    }

}




