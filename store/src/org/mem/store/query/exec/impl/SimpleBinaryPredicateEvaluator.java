package org.mem.store.query.exec.impl;

import org.mem.store.query.exec.AbstractSimplePredicateEvaluator;
import org.mem.store.query.exec.BinaryPredicateEvaluator;
import org.mem.store.query.exec.PredicateEvaluator;
import org.mem.store.query.exec.util.StreamUtils;
import org.mem.store.query.model.LogicalPredicate;
import org.mem.store.query.model.ResultStream;
import org.mem.store.query.model.impl.MutableResultStream;
import org.mem.store.query.model.impl.OrPredicate;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/12/13
 * Time: 10:20 AM
 *
 * Predicate evaluator which works on joins/unions and basically requires a Logical Predicate.
 */
public class SimpleBinaryPredicateEvaluator<P extends PredicateEvaluator> extends AbstractSimplePredicateEvaluator<LogicalPredicate> implements BinaryPredicateEvaluator<P> {

    private static final int NUM_OPERANDS = 2;

    protected PredicateEvaluatorContainer<PredicateEvaluator> predicateEvaluatorContainer;

    public SimpleBinaryPredicateEvaluator(LogicalPredicate wrappedPredicate) {
        super(wrappedPredicate);
        predicateEvaluatorContainer = new PredicateEvaluatorContainer<PredicateEvaluator>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addChildPredicateEvaluator(PredicateEvaluator childPredicateEvaluator) {
        return predicateEvaluatorContainer.addChildPredicateEvaluator(childPredicateEvaluator);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends ResultStream> R eval(R... inputResultStreams) {
        if (wrappedPredicate instanceof OrPredicate) {
            return StreamUtils.join(inputResultStreams[0], inputResultStreams[1]);
        }
        return (R) new MutableResultStream();
    }

    /**
     * Return true is both left and right child are non-null.
     * @return
     */
    public boolean isFull() {
        return predicateEvaluatorContainer.isFull();
    }

    @SuppressWarnings("unchecked")
    public P getLeftChild() {
        return (P) predicateEvaluatorContainer.getLeftChild();
    }

    @SuppressWarnings("unchecked")
    public P getRightChild() {
        return (P) predicateEvaluatorContainer.getRightChild();
    }

    @Override
    public int getNumOperands() {
        return NUM_OPERANDS;
    }
}
