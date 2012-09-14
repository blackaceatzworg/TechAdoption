package uk.ac.dmu.iesd.cascade.adoption;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import cern.jet.random.Empirical;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import repast.simphony.context.Context;
import repast.simphony.context.space.gis.ContextGeography;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.GUIRegistry;
import repast.simphony.engine.environment.RunState;
import repast.simphony.essentials.RepastEssentials;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.gis.DefaultGeography;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;
import repast.simphony.space.gis.ShapefileLoader;
import repast.simphony.space.gis.ShapefileWriter;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;
import repast.simphony.space.grid.StickyBorders;
import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.visualization.IDisplay;
import repast.simphony.visualizationOGL2D.DisplayOGL2D;
import uk.ac.dmu.iesd.cascade.io.CSVReader;
import uk.ac.dmu.iesd.cascade.styles.HouseholdTwoDStyle;

public class AdoptionContextBuilder implements ContextBuilder<Household> {
	
	boolean writeInitialShapefile = false;

	@SuppressWarnings("unchecked")
	@Override
	public Context build(Context context) 
	{	
		AdoptionContext myContext = new AdoptionContext("01/07/2009");
		myContext.logger.debug("Got into the context builder");
		myContext.logger.debug("Random seed is " + RepastEssentials.GetParameter("randomSeed").toString());

		if (!context.isEmpty())
		{
			myContext.logger.warn("Context contains some stuff before the build!!");
		}
		
		RandomHelper.createPoisson(30); // Create a poisson distribution to allocate next cogniscent
										// dates with mean 30 days.
		myContext.logger.debug("Created Poisson distribution, ID " + RandomHelper.getPoisson().toString());
		myContext.setId("SmartGridTechnologyAdoption");
		myContext.setTypeID("SmartGridTechnologyAdoption");
		/*myContext.setNorthLim(52.62);
		myContext.setSouthLin(52.58);
		myContext.setEastLim(-1.05);
		myContext.setWestLim(-1.1);*/
		myContext.PVFITs = new TreeMap<Integer,Integer>();
		myContext.PVFITs.put(4, 370);
		myContext.PVFITs.put(10, 310);
		myContext.PVFITs.put(100, 280);
		myContext.PVFITs.put(5000, 260);		
		
		DefaultGeography<Household> leicesterGeography = new DefaultGeography<Household>("Leicester");
		
		myContext.addProjection(leicesterGeography);

	    /*
	    File file = new File("/Users/nick/tmp/another.shp");
	    ShapefileLoader loader = null;
	    try {
	      loader = new ShapefileLoader(GisAgent.class, file.toURL(), geography, context);
	      loader.load();
	    } catch (MalformedURLException e) {
	      e.printStackTrace(); 
	    }
	    */ 
		CSVReader defraCategories = null;
		CSVReader defraProfiles = null;
		//String categoryFile = "/SmartGridTechnologyAdoption/dataFiles/DEFRA_pro_env_categories.csv";
		//String profileFile = "/SmartGridTechnologyAdoption/dataFiles/200profiles.csv";
		String categoryFile = RepastEssentials.GetParameter("RootDir").toString() + "/dataFiles/DEFRA_pro_env_categories.csv";
		String profileFile = RepastEssentials.GetParameter("RootDir").toString() + "/dataFiles/200profiles.csv";


		try
		{
			defraCategories = new CSVReader(categoryFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("ProsumerFactory: File containing DEFRA types not found at "+categoryFile);
			System.err.println("ProsumerFactory: Doesn't look like this will work, terminating");
			System.err.println(e.getMessage());
			System.exit(Consts.BAD_FILE_ERR_CODE);
		}

/*		try
		{
			defraProfiles = new CSVReader(profileFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("ProsumerFactory: File containing average profiles for DEFRA types not found at "+profileFile);
			System.err.println("ProsumerFactory: Doesn't look like this will work, terminating");
			System.err.println(e.getMessage());
			System.exit(Consts.BAD_FILE_ERR_CODE);
		}*/

		defraCategories.parseByColumn();
	//	defraProfiles.parseByColumn();
		Empirical myDist = RandomHelper.createEmpirical(ArrayUtils.convertStringArrayToDoubleArray(defraCategories.getColumn("Population_fraction")), Empirical.NO_INTERPOLATION);

		myContext.logger.trace("Empirical distribution set up to assign DEFRA categories by population fraction");
		
		ArrayList<Household> households;
		
//		households = new ArrayList<Household>();
//		int numHouseholds = 230;		
//		for (int i = 0; i < numHouseholds; i++)
//		{
//			thisHousehold = new Household()
//			households.add(thisHousehold);
//			if (!myContext.add(thisHousehold))
//			{
//				System.err.println("Adding household to context failed");
//			}
//			else
//			{
//				thisHousehold.setContext(myContext);
//			}
//		
//			double lon = 52.58 + 0.04 * RandomHelper.nextDouble();
//			double lat = -1.05 - 0.05 * RandomHelper.nextDouble();
//			Coordinate coord = new Coordinate(lat,lon);
//			GeometryFactory fac = new GeometryFactory();
//			Point geom = fac.createPoint(coord);
//			leicesterGeography.move(thisHousehold, geom);
//			thisHousehold.setGeography(leicesterGeography);
//		}
		
		File file = new File(RepastEssentials.GetParameter("RootDir").toString() + "/dataFiles/probDomesticLE2.shp");
	    ShapefileLoader loader = null;
	    try {
	    	loader = new ShapefileLoader(Household.class, file.toURL(), leicesterGeography, myContext);
	    	System.out.println("have shapefile initialised with " + file.toURL().toString());
	      	loader.load();
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    }
	     
	    
	    households = Utils.makeArrayList(myContext.getObjects(Household.class));
	    double observedDistanceMean = (Double) RepastEssentials.GetParameter("ObservedRadiusMean");
	    double observedDistanceStd = (Double) RepastEssentials.GetParameter("ObservedRadiusStd");
	    RandomHelper.createNormal(observedDistanceMean, observedDistanceStd);

		int tmp = 0;
		for (Household thisHousehold : households)
		{
			thisHousehold.setGeography(leicesterGeography);
			//Need to think about defining the column names as consts, or otherwise working out
			//How we import files, whether column names are pre-ordained, or arbitrary etc.

			int custSegment = 0;
			double choiceVar = RandomHelper.nextDouble();
			int j = 0;
			while (custSegment < 1)
			{
				if (choiceVar < myDist.cdf(j))
				{
					custSegment = j;
				}
				j++;
			}

			myContext.logger.trace(thisHousehold.agentName + "DEFRA Customer segment is" + custSegment);

			thisHousehold.defraCategory = Integer.parseInt(defraCategories.getColumn("DEFRA_category")[custSegment - 1]);
			thisHousehold.microgenPropensity = Double.parseDouble(defraCategories.getColumn("Microgen_propensity")[custSegment - 1]);
			thisHousehold.insulationPropensity = Double.parseDouble(defraCategories.getColumn("Insulation_propensity")[custSegment - 1]);
			thisHousehold.HEMSPropensity = Double.parseDouble(defraCategories.getColumn("HEMS_propensity")[custSegment - 1]);
			thisHousehold.EVPropensity = Double.parseDouble(defraCategories.getColumn("EV_propensity")[custSegment - 1]);
			thisHousehold.habit = Double.parseDouble(defraCategories.getColumn("Habit_factor")[custSegment - 1]);
			thisHousehold.hasPV = (RandomHelper.nextDouble() < thisHousehold.microgenPropensity);
			thisHousehold.hasSmartControl = false; //(RandomHelper.nextDouble() < thisHousehold.HEMSPropensity);
			thisHousehold.hasElectricVehicle = (RandomHelper.nextDouble() < thisHousehold.EVPropensity);
			thisHousehold.adoptionThreshold = 0.95;
			//thisHousehold.observedRadius = (RandomHelper.nextDouble() * 15);
			thisHousehold.observedRadius = RandomHelper.getNormal().nextDouble();
			myContext.logger.debug(thisHousehold.agentName + " observes " + thisHousehold.observedRadius);
			myContext.logger.trace(thisHousehold.agentName + " has " + thisHousehold.microgenPropensity + " and pre-assigned PV = "+thisHousehold.getHasPV());
			tmp += thisHousehold.hasPV ? 1 : 0;
		}		
		
		myContext.logger.debug(tmp+" households out of "+ households.size() + " have PV");
		myContext.logger.info(households.size() + " Households initialized and added to context and geography");
		
		if (writeInitialShapefile) {
			myContext.logger.trace("Writing shapefile of initial conditions");

			ShapefileWriter testWriter = new ShapefileWriter(leicesterGeography);
			File outFile = new File(
					"C:/RepastSimphony-2.0-beta/workspace/SmartGridTechnologyAdoption/output/shapeFileInitialisedSeed"
							+ RepastEssentials.GetParameter("randomSeed")
									.toString() + ".shp");
			try {
				testWriter.write(leicesterGeography.getLayer(Household.class)
						.getName(), outFile.toURL());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return myContext;
	}
}
