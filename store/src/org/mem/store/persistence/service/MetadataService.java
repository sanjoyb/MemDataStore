package org.mem.store.persistence.service;

import org.mem.store.persistence.descriptor.ColumnDescriptor;
import org.mem.store.persistence.descriptor.TableDescriptor;
import org.mem.store.persistence.exceptions.DuplicateColumnException;
import org.mem.store.persistence.exceptions.DuplicateTableException;
import org.mem.store.persistence.exceptions.NoSuchTableException;
import org.mem.store.persistence.model.listeners.MemoryTableListener;
import org.mem.store.query.model.Predicate;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: 4/12/13 Time: 3:55 PM
 * 
 * Query metadata like cardinality/index info of data in PS store.
 */
public interface MetadataService {

	/**
	 * Gets number of distinct values of a column in a table.
	 * 
	 * @param table
	 * @param columnName
	 * @return
	 */
	public int getCardinality(String table, String columnName);

	/**
	 * Returns true if a table column is indexed.
	 * 
	 * @param table
	 * @param columnName
	 * @return
	 */
	public boolean isIndexed(String table, String columnName);

	/**
	 * Get all index names on this table.
	 * 
	 * @param tableName
	 * @return
	 */
	public Iterator<String> getIndexNames(String tableName);


    /**
     * Create a table using a descriptor for it to allow
     * packing more attributes for table in future.
     * @param tableDescriptor
     * @throws org.mem.store.persistence.exceptions.DuplicateTableException if such a table
     * already exists in the store.
     */
    public <D extends TableDescriptor<?>> void createTable(D tableDescriptor) throws DuplicateTableException;

    /**
     * Create index for an existing table.
     * Call this only after table construction.
     *

     */
    public void createIndex(String tableName, ColumnDescriptor columnDescriptor) throws NoSuchTableException, DuplicateColumnException;

    /**
     * Add multiple column to an existing table.
     * Call this only after table construction.
     *
     */
    public void createIndexes(String tableName, Collection<ColumnDescriptor> columnDescriptors) throws NoSuchTableException, DuplicateColumnException;


    /**
     * Remove table from store and return true if successful
     */
    public boolean deleteTable(String tableName);

    /**
     * Create join tables from existing table to create a filtered table
     * based on the predicate passed.
     * <p>
     *     For efficient queries such indexed tables can be built
     *     upfront so that subsequent queries of same nature can
     *     simply use such a join table to lookup.
     * </p>
     */
   	public <P extends Predicate> void createJoinTable(String tableName, P predicate);
   	
   	/**
	 * if table is created or not
	 * 
	 * @param tableName
	 */
   	public boolean isTableExist(String tableName);
   	
	/**
	 * Register table listener on the table for getting notifications for tuple added/deleted, 
	 * table cleared etc 
	 * @param tableLisener
	 */
	public void registerTableListener(String tableName, MemoryTableListener tableLisener);

}
