package com.runx.pattern.lottery;

import com.runx.pattern.Filter;
import com.runx.pattern.FilterChain;
import com.runx.pattern.RequestMessage;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SumFilter extends Rule implements Filter {
    @Override
    public void doFilter(RequestMessage message, FilterChain chain) {
        String[] nums = message.getMessage().split(" ");
        int sum = Arrays.stream(nums).mapToInt(Integer::valueOf).sum();
        int min = getRuleScope().getV().getMin();
        int max = getRuleScope().getV().getMax();
        if (sum > max || sum < min) {
            System.out.println("有杂质");
            message.setMessage(null);
            return;
        }
        chain.doFilter(message,chain);
    }
}
