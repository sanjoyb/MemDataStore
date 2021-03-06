package org.mem.store.persistence.model.listeners;

import org.mem.store.persistence.model.MemoryTuple;
import org.mem.store.persistence.util.ObjectSizeCalculator;

import java.util.Set;

public class MemoryTableChangeObserver implements MemoryTableListener {
	
	private long tableSizeInBytes;
	@Override
	public void onTupleAdded(TupleAddedEvent e) {
		MemoryTuple tuple = (MemoryTuple) e.getSource();
		tableSizeInBytes += getTupleSizeInBytes(tuple);		
	}
	
	@Override
	public void onTupleRemoved(TupleRemovedEvent e) {
		MemoryTuple tuple = (MemoryTuple) e.getSource();
		tableSizeInBytes -= getTupleSizeInBytes(tuple);
	}
	
	private long getTupleSizeInBytes(MemoryTuple tuple) {
		long tupleSizeInBytes = 0;
		Set<String> attrNames = (Set<String>) tuple.getAttributeNames();
		for(String attrName:attrNames) {
			Object attrValue = tuple.getAttributeValue(attrName);
			tupleSizeInBytes += ObjectSizeCalculator.getSizeInBytes(attrName) + ObjectSizeCalculator.getSizeInBytes(attrValue);
			tupleSizeInBytes += ObjectSizeCalculator.getSizeInBytes(tuple.getMemoryKey().getWrappedObject());
		}
		return tupleSizeInBytes;
	}
	
	@Override
	public long getTableSizeInBytes() {
		return tableSizeInBytes;
	}

	@Override
	public void onTableCleared() {
		tableSizeInBytes = 0;
	}
}