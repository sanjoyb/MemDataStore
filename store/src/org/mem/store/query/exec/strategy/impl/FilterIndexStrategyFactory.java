package org.mem.store.query.exec.strategy.impl;

import org.mem.store.query.exec.strategy.PredicateStrategy;
import org.mem.store.query.model.Predicate;
import org.mem.store.query.model.ResultStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 12/12/13
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class FilterIndexStrategyFactory {

    public static final FilterIndexStrategyFactory INSTANCE = new FilterIndexStrategyFactory();

    private FilterIndexStrategyFactory() {}

    public <R extends ResultStream> PredicateStrategy chooseStrategy(Predicate predicateToFilter) {
        //TODO make smarter choices based on predicate type
        return new SimplePipelineStrategy(predicateToFilter);
    }
}
