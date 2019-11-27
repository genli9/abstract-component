# 流程编排

## 编排
按照域、职责对流程进行拆分。通过json文件定义具体流程，通过ProcessTrigger触发流程执行

## 流程定义
使用bizCode、operation作为流程的签名，唯一标识流程。
- bizCode: 业务类型，比如说普通订单、扫码购物
- operation: 对应某个业务类型下的某个具体业务操作，比如说，普通订单下的支付、订单查询等操作，可以与RestApi对应

## 流程串联
所有的Node通过方法入参-Context进行串联，所有的入参、出参都聚合在Context中

## 优点
通过单一职责原则将某个流程下的关键操作进行拆分，进而进行灵活的组装。最终的目的是，当operation比较多时，不同
bizCode下的node可以复用

## 缺点
- 在执行每个流程之前，都要组装一次Context。Context中的成员变量，除了出参，其余大部分是领域模型
- 缺少对Context的抽象，Context无法复用会导致：在同一BizCode下的不同Operation中，Node无法复用