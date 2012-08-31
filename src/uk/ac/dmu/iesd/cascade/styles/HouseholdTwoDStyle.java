package uk.ac.dmu.iesd.cascade.styles;

import java.awt.Color;
import java.awt.Font;

import org.geotools.event.GTComponent;
import org.geotools.event.GTDelta;
import org.geotools.event.GTNote;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.StyleVisitor;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import repast.simphony.visualizationOGL2D.StyleOGL2D;
import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;
import uk.ac.dmu.iesd.cascade.adoption.Household;


/**
 * 
 * @author Richard Snape
 *
 */
public class HouseholdTwoDStyle implements StyleOGL2D<Household>, Style{

	private static double maxCapacity = 0;
	
/*	@Override
	public FeatureType getFeatureType(Household agent) {
		return FeatureType.POINT;
	}
	
  @Override
  public Color getFillColor(Household agent) {
	    
	  Color returnColor;
	  if (agent.hasPV)
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
	GeoShape polygon = GeoShapeFactory.createPolygon();
	SurfacePolygon thisShape = (SurfacePolygon) polygon.getRenderable();
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
}*/

	ShapeFactory2D  myFactory;
	
@Override
public void init(ShapeFactory2D factory) {
this.myFactory = factory;	
}

@Override
public VSpatial getVSpatial(Household object, VSpatial spatial) {
	  
		    if (spatial == null) {
		      spatial = myFactory.createCircle(40, 1);
		    }
		    return spatial;
		  }


@Override
public Color getColor(Household agent) {
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
public int getBorderSize(Household object) {
	return 0;
}

@Override
public float getLabelXOffset(Household object) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public float getLabelYOffset(Household object) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public Position getLabelPosition(Household object) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Color getBorderColor(Household object) {
	return null;
}

@Override
public float getRotation(Household object) {
	return 0;
}

@Override
public float getScale(Household object) {
	return 1;
}

@Override
public String getLabel(Household object) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Font getLabelFont(Household object) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Color getLabelColor(Household object) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void changed(GTDelta arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public GTNote getNote() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public GTComponent getParent() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void removed(GTDelta arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void setNote(GTNote arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void accept(StyleVisitor arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void addFeatureTypeStyle(FeatureTypeStyle arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public String getAbstract() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public FeatureTypeStyle[] getFeatureTypeStyles() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getName() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getTitle() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean isDefault() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void setAbstract(String arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void setDefault(boolean arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void setFeatureTypeStyles(FeatureTypeStyle[] arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void setName(String arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void setTitle(String arg0) {
	// TODO Auto-generated method stub
	
}

}