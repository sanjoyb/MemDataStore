package org.mem.store.query.model.impl;

import org.mem.store.persistence.model.MemoryTuple;
import org.mem.store.query.exec.PredicateTreeVisitor;
import org.mem.store.query.model.BinaryOperator;
import org.mem.store.query.model.BinaryPredicate;
import org.mem.store.query.model.QueryExpression;

public abstract class BinaryPredicateImpl implements BinaryPredicate<MemoryTuple> {

	protected SimpleQueryExpression leftExpression;

    protected QueryExpression rightExpression;

    protected BinaryOperator op;

    protected BinaryPredicateImpl(SimpleQueryExpression leftExpression,
                                  QueryExpression rightExpression,
                                  BinaryOperator op) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.op = op;
    }

    @Override
	public BinaryOperator getBinaryOperator() {
		return op;
	}

    @Override
    public SimpleQueryExpression getLeftExpression() {
        return leftExpression;
    }

    @Override
    public QueryExpression getRightExpression() {
        return rightExpression;
    }

    @Override
    public <V extends PredicateTreeVisitor> void accept(V predicateTreeVisitor) {
        predicateTreeVisitor.visit(this);
    }

    public String toString(String pattern) {
        return String.format(pattern, leftExpression, rightExpression);
    }
}
