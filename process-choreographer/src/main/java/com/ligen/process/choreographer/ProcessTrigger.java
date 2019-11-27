package com.ligen.process.choreographer;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProcessTrigger {

    @Autowired
    private ProcessRegistry processRegistry;

    public void fire(String bizType, String bizCode, Object context) throws ProcessException{
        List<Node> nodes = processRegistry.findProcess(bizType, bizCode);
        if (nodes == null || nodes.size() == 0) {
            log.error("doesn't find any processes by bizCode: {}， biz_type: {}", bizCode, bizType);
            throw new ProcessException("doesn't find any processes by bizType or bizCode");
        }
        for (Node n : nodes) {
            try {
                n.execute(context);
            } catch (Exception e) {
                log.error("process execution error, biz_type: {}, biz_code: {}, nodeName: {}, err: {}",
                        bizType, bizCode, n.nodeName(), Throwables.getStackTraceAsString(e));
            }
        }
    }
}
