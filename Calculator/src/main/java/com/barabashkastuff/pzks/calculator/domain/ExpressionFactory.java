package com.barabashkastuff.pzks.calculator.domain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * ExpressionFactory Class
 *
 * @author barabashka
 * @version 26-Oct-14
 */
@Component
public class ExpressionFactory implements ObjectFactory<Expression>{

    @Override
    public Expression getObject() throws BeansException {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Expression.class);
//        return context.getBean();
        return null;
    }
}
