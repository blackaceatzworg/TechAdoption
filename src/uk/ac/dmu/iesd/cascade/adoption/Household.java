package uk.ac.dmu.iesd.cascade.adoption;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.vividsolutions.jts.geom.Envelope;

import repast.simphony.context.Context;
import repast.simphony.context.space.gis.ContextGeography;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.essentials.RepastEssentials;
import repast.simphony.query.space.gis.GeographyWithin;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.gis.Geography;
import repast.simphony.util.ContextUtils;

public class Household {
	private Geography<Household> myGeography;
	private ArrayList<Household> myNeighboursCache;
	private int numCachedNeighbours;

	private Date nextCogniscentDate;

	private int numThoughts;

	/**
	 * a Household agent's ID This field is automatically assigned by
	 * constructor when the agent is created for the first time
	 * */
	protected long agentID = -1;

	/**
	 * This field is used for counting number of agents instantiated by
	 * descendants of this class
	 **/
	private static long agentIDCounter = 0;

	/**
	 * A prosumer agent's name This field can be <code>null</code>.
	 * */
	protected String agentName;

	private AdoptionContext mainContext;
	public int defraCategory;
	public double microgenPropensity;
	public double PVlikelihood;
	public double insulationPropensity;
	public double HEMSPropensity;
	public double EVPropensity;
	public double habit;
	double adoptionThreshold;

	private double economicSensitivity;
	private double decisionUrgency = 0.001;

	public boolean hasPV;

	public boolean getHasPV() {
		return hasPV;
	}
	
	public int getHasPVNum() {
		return hasPV?1:0;
	}

	public boolean hasSmartControl;

	public boolean hasElectricVehicle;
	private double potentialPVCapacity = 3.0;
	public double observedRadius;

	/**
	 * A prosumer agent's base name it can be reassigned (renamed) properly by
	 * descendants of this class
	 **/
	protected static String agentBaseName = "prosumer";

	@ScheduledMethod(start = 3, interval = 48, shuffle = true, priority = ScheduleParameters.FIRST_PRIORITY)
	public void checkTime() {
		if (mainContext == null) {
			Context testContext = ContextUtils.getContext(this);
			if (testContext instanceof AdoptionContext) {
				this.mainContext = (AdoptionContext) testContext;
			}
		}

		if (mainContext.getDateTime().getTime() > this.nextCogniscentDate.getTime()	&& mainContext.getDateTime().getTime() <= (this.nextCogniscentDate.getTime() + 24 * 60 * 60 * 1000)) {
			considerOptions();
			// this.myGeography.move(this, this.myGeography.getGeometry(this));
			numThoughts++;
			decisionUrgency = 1.0 / (double) (mainContext.dateToTick(mainContext.getTarriffAvailableUntil()) - mainContext.getTickcount());
			
			this.nextCogniscentDate.setTime(mainContext.getDateTime().getTime() + ((long) (RandomHelper.getPoisson().nextDouble() * 24 * 60 * 60 * 1000)));
		}
	}

	private void considerOptions() {
		PVlikelihood = microgenPropensity;
		mainContext.logger.trace(this.agentName
				+ " gathering information from baseline likelihood of "
				+ PVlikelihood + "...");
		observeNeighbours();
		checkTariffs();
		mainContext.logger.trace(this.agentName
				+ " making decision based on likelihood of " + PVlikelihood
				+ "...");
		makeDecision();
	}

	private void makeDecision() {
		mainContext.logger.trace(this.getAgentName()
				+ " has microgen propensity " + this.microgenPropensity
				+ " and PV adoption likelihood " + PVlikelihood);
		if (PVlikelihood > adoptionThreshold) {
			mainContext.logger.trace(this.agentName + " Adopted PV");
			this.hasPV = true;
		}

	}

	private void checkTariffs() {
		double PVTariffPence = ((double) mainContext
				.getPVTariff(this.potentialPVCapacity)) / 1000; // note tariffs
																// stored as
																// integer
																// tenths of
																// pence
		// Add the weight of the tariff influence to the adoption likelihood		
	
		PVlikelihood += this.economicSensitivity * PVTariffPence * (decisionUrgency*1000);
	}

	private void observeNeighbours() {
		mainContext.logger.trace("Observing neighbours");
		ArrayList<Household> neighbours = getNeighbours();
		int observedAdoption = 0;
		int observed = 0;
		for (Household h : neighbours) {
			mainContext.logger.trace("Into observation loop");
			boolean observe = (RandomHelper.nextDouble() > 0.5);
			// observe = true; // for testing

			if (observe) {
				if (h.getHasPV()) {
					observedAdoption++;
				}
				observed++;
			}

			// Likelihood of adopting now - based on observation alone
			// Note that the 0.5 is an arbitrary and tunable parameter.
			PVlikelihood += (0.4 * observedAdoption);
			mainContext.logger.trace("Adding likelihood to agent "
					+ this.getAgentName() + " based on " + observedAdoption
					+ " of " + numCachedNeighbours
					+ " neighbours observed to have PV (" + observed
					+ " observed this round)");

			if (RandomHelper.nextDouble() > habit) {
				// habit change
				microgenPropensity = PVlikelihood;
				mainContext.logger
						.trace("Updating propensity, i.e. changing habit, based on likelihood");
			}

		}
	}

	/*
	 * Note - OK to cache neighbours as static in this simulation. If households
	 * may move to different physical houses, this would have to change.
	 */
	private ArrayList<Household> getNeighbours() {

		if (this.myNeighboursCache == null) {
			GeographyWithin<Household> neighbourhood = new GeographyWithin<Household>(
					myGeography, observedRadius, this);
			this.myNeighboursCache = Utils.makeArrayList(neighbourhood.query());
			this.numCachedNeighbours = myNeighboursCache.size();
			mainContext.logger.trace(this.getAgentName()
					+ " found neighbours : " + myNeighboursCache.toString());
		}

		mainContext.logger.trace(this.getAgentName() + " has "
				+ this.numCachedNeighbours + " neighbours : "
				+ myNeighboursCache.toString());

		return this.myNeighboursCache;
	}

	private String getAgentName() {
		return this.agentName;
	}

	/**
	 * Constructs a prosumer agent with the context in which is created
	 * 
	 * @param context
	 *            the context in which this agent is situated
	 */
	public Household(AdoptionContext context) {
		this();
		this.mainContext = context;
		Date startTime = context.simStartDate;
		startTime.setTime(startTime.getTime() + ((long) (RandomHelper.getPoisson().nextDouble() * 24 * 60 * 60 * 1000)));
		this.nextCogniscentDate = startTime;
		context.logger.debug(this.agentName+" first thought at "+nextCogniscentDate.toGMTString());
	}

	/**
	 * Constructs a prosumer agent with the context in which is created.
	 * 
	 * Needs some work - note the hard coded time on which to base all next
	 * decision times.
	 * 
	 */
	public Household() {
		this.agentID = agentIDCounter++;
		this.agentName = "Household_" + this.agentID;
		Date startTime = null;
		try {
			startTime = (new SimpleDateFormat("dd/mm/yyyy"))
					.parse("01/04/2010");
		} catch (ParseException e) {
			System.err.println("Can't parse this");
		}
		startTime
				.setTime(startTime.getTime()
						+ ((long) (RandomHelper.getPoisson().nextDouble() * 24 * 60 * 60 * 1000)));
		this.nextCogniscentDate = startTime;
		this.economicSensitivity = RandomHelper.nextDouble();
	}

	public int getNumThoughts() {
		return numThoughts;
	}
	
	public int getDefraCategory()
	{
		return defraCategory;
	}

	public void setContext(AdoptionContext c) {
		this.mainContext = c;
	}

	public void setGeography(Geography g) {
		this.myGeography = g;
	}
}
