package uk.ac.dmu.iesd.cascade.styles;

import gov.nasa.worldwind.render.SurfacePolygon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.visualization.gis3D.GIS3DShapeFactory;
import repast.simphony.visualization.gis3D.Material;
import repast.simphony.visualization.gis3D.MaterialFactory;
import repast.simphony.visualization.gis3D.RenderableShape;
import repast.simphony.visualization.gis3D.style.StyleGIS3D;
import repast.simphony.visualization.gisnew.*;
import repast.simphony.visualization.gisnew.GeoShape.FeatureType;

/**
 * 
 * @author J Richard Snape
 *
 */
/*public class HouseholdStyle implements StyleGIS<Household>{

	public GeoShape getShape(Household agent) {
		// Render as spheres	
		GeoShape shape = new GeoShape();
		shape.setRenderable(GIS3DShapeFactory.createCone(10));//,1000,0,2);  


    return shape;		
	}

	public Material getMaterial(Household agent, Material material) {
		Color color = null;
		double solar = agent.getMiso() + agent.getMaso();
    	float yellowness = (float) solar;
		//System.out.println(agent.toString() + " has yellowness " + yellowness);
    	if (yellowness < 0)
    		yellowness = 0;
    	
    	if (yellowness > 10)
    		yellowness = 10;
		color = new Color(yellowness/10,yellowness/10,1-(yellowness/10));
		material = new Material(color,color,color,color,0);
		return MaterialFactory.setMaterialAppearance(material, color);

	}

	public float getScale(Household agent) {
		
		float [] scalefactors = new float[3];
		scalefactors[0] =  1f ; //1; //(float) (agent.getMiw() + agent.getMaw()) * 10f;
		scalefactors[1] = 1f ; //1; // (float) (agent.getMiw() + agent.getMaw()) * 10f;
		scalefactors[2] = (float) (agent.getMiw() + agent.getMaw()); // / 10;
		return scalefactors[2];
	}

	public boolean isScaled(Household object) {
		return true;
	}

	@Override
	public Color getFillColor(Household agent) {
		// TODO Auto-generated method stub
		Color color = null;
		double solar = agent.getMiso() + agent.getMaso();
    	float yellowness = (float) solar;
		//System.out.println(agent.toString() + " has yellowness " + yellowness);
    	if (yellowness < 0)
    		yellowness = 0;
    	
    	if (yellowness > 10)
    		yellowness = 10;
		color = new Color(yellowness/10,yellowness/10,1-(yellowness/10));
		return color;
	}


	@Override
	public FeatureType getFeatureType(Household obj) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double getFillOpacity(Household obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Color getBorderColor(Household obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getBorderOpacity(Household obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Stroke getBorder(Household obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRotation(Household obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLabel(Household obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getLabelColor(Household obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getLabelFont(Household obj) {
		// TODO Auto-generated method stub
		return null;
	}
}*/


import java.awt.Color;
import java.util.Iterator;

import repast.simphony.essentials.RepastEssentials;
import repast.simphony.visualization.gisnew.DefaultStyleGIS;
import repast.simphony.visualization.gisnew.GeoShape.FeatureType;
import uk.ac.dmu.iesd.cascade.adoption.Household;

/**
 * 
 * @author Richard Snape
 *
 */
public class HouseholdStyle implements StyleGIS<Household>{

	private static double maxCapacity = 0;
	
	@Override
	public FeatureType getFeatureType(Household agent) {
		return FeatureType.POINT;
	}
	
  @Override
  public Color getFillColor(Household agent) {
	    System.out.println("Been through household colour change method");
	  Color returnColor;
	  if (agent.getHasPV())
	  {
		  returnColor = new Color(255,255,0);
	  }
	  else
	  {
		  double thoughtProportion = 20d / agent.getNumThoughts();
		  if (thoughtProportion > 1) thoughtProportion = 1;
		  returnColor = new Color((int)(thoughtProportion * 255),0,(int)((1-thoughtProportion)*255));
	  }
	  	return returnColor;
	}
  
  @Override
  public float getScale(Household thisObj) {
	  
	  return 0;
  }
  
  @Override
  public boolean isScaled(Household thisObj) {
	  return false;
  }

@Override
public GeoShape getShape(Household obj) {
	// TODO Auto-generated method stub
	float r = 25;
	return GeoShapeFactory.createCircle(r);
/*	GeoShape polygon = GeoShapeFactory.createPolygon();
	SurfacePolygon thisShape = (SurfacePolygon) polygon.getRenderable();*/
	//Can do all kinds of fancy things with the shape here if you  get its attributes
	//return polygon;
}

public Stroke getBorder(Household obj) {
	return null;
}
public Color getBorderColor(Household obj) {
	return Color.BLACK; //getFillColor(obj);
}
public double getBorderOpacity(Household obj) {
	return 0;
}

public double getFillOpacity(Household obj) {
	return 1;
}
public String getLabel(Household obj) {
	return obj.toString();
}
public Color getLabelColor(Household obj) {
	return Color.BLACK;
}
public Font getLabelFont(Household obj) {
	return Font.decode("Arial");
}
public float getRotation(Household obj) {
	return 0;
}

}