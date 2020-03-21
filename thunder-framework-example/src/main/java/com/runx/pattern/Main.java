package com.runx.pattern;

import com.runx.pattern.lottery.Rule;
import com.runx.pattern.lottery.SumFilter;

public class Main {
    public static void main(String[] args) {
        FilterChain chain = new FilterChain();
//        chain.add(new AuthFilter())
//        .add(new HtmlFilter());
//        RequestMessage message = new RequestMessage();
//        message.setMessage("<hedsfd>fdsfdsfds");
//        message.setUid(100L);
//        chain.doFilter(message,chain);
//        System.out.println(message);
        SumFilter sumFilter = new SumFilter();
        Rule.Expression expression = new Rule.Expression();
        Rule.RulePair<Rule.Metrix, Rule.Expression> pair = new Rule.RulePair(Rule.Metrix.SUM,expression);
        expression.setMetrix(Rule.Metrix.SUM)
                .setMax(100)
                .setMin(60);
        pair.setV(expression);
        sumFilter.setRuleScope(pair);
        chain.add(sumFilter);
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setMessage("20 11 18 16 33 22");
        chain.doFilter(requestMessage,chain);
    }

}
