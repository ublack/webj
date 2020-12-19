package com.webj.util;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class Uel {

    public static void main(String[] args)  {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        Expression exp = spelExpressionParser.parseExpression("#aa eq #aa");
//        Expression exp = spelExpressionParser.parseExpression("new java.text.DecimalFormat(\"#0.00%\").format(#aa)");
        StandardEvaluationContext txt = new StandardEvaluationContext();
        txt.setVariable("aa", 3.14159);
        System.out.println(exp.getValue(txt));
    }
}
