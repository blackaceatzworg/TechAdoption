package uk.ac.dmu.iesd.cascade.adoption;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.ShapefileLoader;
import uk.ac.dmu.iesd.cascade.io.CSVReader;
import cern.jet.random.Empirical;

public class HouseholdFactory {

	
	public ArrayList<Household> createDEFRAHouseholds(int number, String categoryFile, String profileFile)
	{
		ArrayList<Household> returnList = new ArrayList();

		CSVReader defraCategories = null;
		CSVReader defraProfiles = null;

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

		try
		{
			defraProfiles = new CSVReader(profileFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("ProsumerFactory: File containing average profiles for DEFRA types not found at "+profileFile);
			System.err.println("ProsumerFactory: Doesn't look like this will work, terminating");
			System.err.println(e.getMessage());
			System.exit(Consts.BAD_FILE_ERR_CODE);
		}

		defraCategories.parseByColumn();
		defraProfiles.parseByColumn();

		//Need to think about defining the column names as consts, or otherwise working out
		//How we import files, whether column names are pre-ordained, or arbitrary etc.

		Empirical myDist = RandomHelper.createEmpirical(ArrayUtils.convertStringArrayToDoubleArray(defraCategories.getColumn("Population_fraction")), Empirical.NO_INTERPOLATION);

		for (int j = 0; j < number; j++)
		{

			int custSegment = 0;
			double choiceVar = RandomHelper.nextDouble();
			int i = 0;
			while (custSegment < 1)
			{
				if (choiceVar < myDist.cdf(i))
				{
					custSegment = i;
				}
				i++;
			}

			if (Consts.DEBUG)
			{
				if (Consts.DEBUG) System.out.println("DEFRA Customer segment is" + custSegment);
			}

			Household prAgent = this.createHousehold(ArrayUtils.convertStringArrayToDoubleArray(defraProfiles.getColumn("demand" + (custSegment - 1))), true);

			prAgent.defraCategory = Integer.parseInt(defraCategories.getColumn("DEFRA_category")[custSegment - 1]);
			prAgent.microgenPropensity = Double.parseDouble(defraCategories.getColumn("Microgen_propensity")[custSegment - 1]);
			prAgent.insulationPropensity = Double.parseDouble(defraCategories.getColumn("Insulation_propensity")[custSegment - 1]);
			prAgent.HEMSPropensity = Double.parseDouble(defraCategories.getColumn("HEMS_propensity")[custSegment - 1]);
			prAgent.EVPropensity = Double.parseDouble(defraCategories.getColumn("EV_propensity")[custSegment - 1]);
			prAgent.habit = Double.parseDouble(defraCategories.getColumn("Habit_factor")[custSegment - 1]);

			returnList.add(prAgent);
		}

		return returnList;
	}
	
	public void createHouseholdsFromShapeFile(String dataPath, String fileName, Context context, Geography geography)
	{
		 File file = new File(dataPath+fileName);
	     ShapefileLoader loader = null;
	     try {
	       loader = new ShapefileLoader(Household.class, file.toURL(), geography, context);
	       loader.load();
	     } catch (MalformedURLException e) {
	       e.printStackTrace();
	     }
	}


	private Household createHousehold(double[] demand, boolean b) 
	{
		// TODO Auto-generated method stub
		return new Household();
	}
}
