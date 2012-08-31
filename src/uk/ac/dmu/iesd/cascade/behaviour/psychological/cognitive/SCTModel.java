/**
 * 
 */
package uk.ac.dmu.iesd.cascade.behaviour.psychological.cognitive;

import uk.ac.dmu.iesd.cascade.behaviour.psychological.Construct;
import uk.ac.dmu.iesd.cascade.behaviour.psychological.SimpleConstruct;
import uk.ac.dmu.iesd.cascade.behaviour.psychological.SimpleModel;
import uk.ac.dmu.iesd.cascade.behaviour.psychological.SimpleRelationship;

/**
 * @author jsnape
 *
 */
public class SCTModel extends SimpleModel {

	
	SCTModel() {
		super();
		
		Construct selfEfficacy = new SimpleConstruct("Self-efficacy",0.7f);
		Construct pOfO = new SimpleConstruct("Perception of Others",0.1f);
		Construct ssf = new SimpleConstruct("Socio structural facilitators",0.2f);
		Construct ssi = new SimpleConstruct("Socio structural inhibitors",0.2f);
		Construct oeP = new SimpleConstruct("Outcome Expectation physical",0.2f);
		Construct oeSoc = new SimpleConstruct("Outcome Expectation social",0.2f);
		Construct oeSE = new SimpleConstruct("Outcome Expectation self evaluation",0.2f);
		Construct goals = new SimpleConstruct("Goals",0.2f);
		Construct behaviour = new SimpleConstruct("Behaviour",0.0f);
		Construct outcome = new SimpleConstruct("Outcomes",0.0f);
		
		this.addConstruct(selfEfficacy);
		this.addConstruct(pOfO);
		this.addConstruct(ssf);
		this.addConstruct(ssi);
		this.addConstruct(oeP);
		this.addConstruct(oeSoc);
		this.addConstruct(oeSE);		
		this.addConstruct(goals);
		this.addConstruct(behaviour);
		this.addConstruct(outcome);
				
		this.addRelationship(new SimpleRelationship(selfEfficacy, oeP, 0.7f, true));
		this.addRelationship(new SimpleRelationship(selfEfficacy, oeSoc, 0.7f, true));
		this.addRelationship(new SimpleRelationship(selfEfficacy, oeSE, 0.7f, true));
		this.addRelationship(new SimpleRelationship(pOfO, ssf, 0.7f, false));
		this.addRelationship(new SimpleRelationship(pOfO, ssi, 0.7f, false));
		this.addRelationship(new SimpleRelationship(ssi, oeP, 0.7f, true));
		this.addRelationship(new SimpleRelationship(ssi, oeSoc, 0.7f, true));
		this.addRelationship(new SimpleRelationship(ssi, oeSE, 0.7f, true));	
		this.addRelationship(new SimpleRelationship(ssf, oeP, 0.7f, true));
		this.addRelationship(new SimpleRelationship(ssf, oeSoc, 0.7f, true));
		this.addRelationship(new SimpleRelationship(ssf, oeSE, 0.7f, true));

		this.addRelationship(new SimpleRelationship(selfEfficacy, goals, 0.7f, true));
		this.addRelationship(new SimpleRelationship(oeSoc, goals, 0.7f, true));
		this.addRelationship(new SimpleRelationship(oeP, goals, 0.7f, true));
		this.addRelationship(new SimpleRelationship(oeSE, goals, 0.7f, true));
		this.addRelationship(new SimpleRelationship(pOfO, goals, 0.7f, false));
		this.addRelationship(new SimpleRelationship(ssi, goals, 0.7f, true));	
		this.addRelationship(new SimpleRelationship(ssf, goals, 0.7f, true));
		
		this.addRelationship(new SimpleRelationship(selfEfficacy, behaviour, 0.7f, true));
		this.addRelationship(new SimpleRelationship(oeSoc, behaviour, 0.7f, true));
		this.addRelationship(new SimpleRelationship(oeP, behaviour, 0.7f, true));
		this.addRelationship(new SimpleRelationship(oeSE, behaviour, 0.7f, true));
		this.addRelationship(new SimpleRelationship(pOfO, behaviour, 0.7f, false));
		this.addRelationship(new SimpleRelationship(ssi, behaviour, 0.7f, true));	
		this.addRelationship(new SimpleRelationship(ssf, behaviour, 0.7f, true));
		this.addRelationship(new SimpleRelationship(goals, behaviour, 0.7f, true));
		
		this.addRelationship(new SimpleRelationship(behaviour, outcome, 0.7f, true));
		this.addRelationship(new SimpleRelationship(ssi, outcome, 0.7f, true));	
		this.addRelationship(new SimpleRelationship(ssf, outcome, 0.7f, true));

		//Dotted feedback lines in SCT model
		this.addRelationship(new SimpleRelationship(outcome, oeP, 0.7f, true));
		this.addRelationship(new SimpleRelationship(outcome, oeSoc, 0.7f, true));
		this.addRelationship(new SimpleRelationship(outcome, oeSE, 0.7f, true));
		this.addRelationship(new SimpleRelationship(outcome, selfEfficacy, 0.7f, true));
		this.addRelationship(new SimpleRelationship(outcome, ssi, 0.7f, true));
		this.addRelationship(new SimpleRelationship(outcome, ssf, 0.7f, true));
		
	}
}
