package uk.ac.dmu.iesd.cascade.adoption;

import java.util.List;

import repast.simphony.engine.controller.NullAbstractControllerAction;
import repast.simphony.engine.environment.GUIRegistry;
import repast.simphony.engine.environment.RunEnvironmentBuilder;
import repast.simphony.engine.environment.RunState;
import repast.simphony.scenario.ModelInitializer;
import repast.simphony.scenario.Scenario;
import repast.simphony.ui.RSApplication;
import repast.simphony.ui.RSGUIConstants;
import repast.simphony.ui.RSGui;
import repast.simphony.visualization.IDisplay;
import repast.simphony.visualizationOGL2D.DisplayOGL2D;
import uk.ac.dmu.iesd.cascade.styles.HouseholdTwoDStyle;

public class Initializer implements ModelInitializer {

	@Override
	public void initialize(Scenario scen, RunEnvironmentBuilder builder) {
		System.out.println("Been through the initializer");
		scen.addMasterControllerAction(new NullAbstractControllerAction());

	}

}
