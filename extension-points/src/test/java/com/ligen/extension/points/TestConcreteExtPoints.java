package com.ligen.extension.points;

@ConcreteExtensionPoints(bizCode = "bizCode1")
public class TestConcreteExtPoints implements TestAbstractExtPoints {

    @Override
    public String test() {
        return "test";
    }
}
