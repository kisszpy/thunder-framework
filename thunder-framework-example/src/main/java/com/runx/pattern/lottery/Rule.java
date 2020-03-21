package com.runx.pattern.lottery;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class Rule {

    private RulePair<Metrix,Expression> ruleScope;


    public enum Metrix {
        SUM
    }

    @Data
    @Accessors(chain = true)
    public static class Expression {
        Metrix metrix;
        int min;
        int max;
    }

    @Data
    public static class RulePair<K,V> {
        K k;
        V v;

        public RulePair(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

}
