package com.javarush.task.task37.task3710.decorators;

import com.javarush.task.task37.task3710.shapes.Shape;

/**
 * Created by 123 on 16.12.2017.
 */
public class RedShapeDecorator extends ShapeDecorator {
    private Shape decoratedShape;
    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
        this.decoratedShape=decoratedShape;
    }

    private void setBorderColor(Shape shape){
        System.out.println("Setting border color for "+shape.getClass().getSimpleName()+" to red.");
    }
    @Override
    public void draw() {
        setBorderColor(decoratedShape);
        decoratedShape.draw();
    }
}
