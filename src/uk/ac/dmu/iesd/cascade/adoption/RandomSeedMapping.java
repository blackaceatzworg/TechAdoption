package uk.ac.dmu.iesd.cascade.adoption;

import repast.simphony.data.logging.LoggingInitializable;
import repast.simphony.data.logging.gather.AbstractMapping;
import repast.simphony.data.logging.gather.AggregateDataMapping;
import repast.simphony.engine.environment.RunInfo;

/**
 * A data mapping that returns the current run number from its {@link #getColumnValue(Object)}
 * method. This loads the run number through the {@link #initialize(RunInfo)} method.
 * 
 * @see #initialize(RunInfo)
 * 
 * @author Jerry Vos
 */
public class RandomSeedMapping extends AbstractMapping<Integer, Object> implements
		LoggingInitializable, AggregateDataMapping<Integer, Object> {
	public static final String RUN_NUMBER_COL_NAME = "Run Number";

	private Integer runNumber;

	/**
	 * Constructs this mapping with the default column name.
	 * 
	 * @see #RUN_NUMBER_COL_NAME
	 */
	public RandomSeedMapping() {
		super();
	}

	/**
	 * Returns the run number (set through the constructor or {@link #setRunNumber(Integer)}.
	 * 
	 * @param valueSource
	 *            ignored
	 * @return the current run number
	 */
	public Integer getColumnValue(Object valueSource) {
		return runNumber;
	}

	/**
	 * Returns the run number (set through the constructor or {@link #setRunNumber(Integer)}.
	 * 
	 * @param valueSource
	 *            ignored
	 * @return the current run number
	 */
	public Integer getColumnValue(Iterable<Object> valueSource) {
		return runNumber;
	}

	/**
	 * Grabs the run number from the given {@link RunInfo}.
	 */
	public void initialize(RunInfo runInfo) {
		this.runNumber = runInfo.getRunNumber();
	}
}