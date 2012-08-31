package uk.ac.dmu.iesd.cascade.adoption;

import java.util.ArrayList;
import java.util.Iterator;

public class Utils {
	
	@SuppressWarnings("rawtypes")
	public static int countIterable(Iterable i)
	{
		Iterator it = i.iterator();
		int c = 0;
		while (it.hasNext())
		{
			c+=1;
			it.next();
		}
		return c;
	}

	public static String displayIterableMembers(
			Iterable i) {
		String s = "";
		for (Object o : i)
		{
			s += o.toString()+"; ";
		}
		return s;
	}

	public static <T>ArrayList<T> makeArrayList(Iterable<T> i) {
		ArrayList<T> retList = new ArrayList<T>();
		for (T o : i)
		{
			retList.add(o);
		}
		return retList;
	}	
}
