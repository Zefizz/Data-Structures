package comp2402a2;

import java.util.AbstractList;
import java.util.List;
import java.util.ArrayList;

/**
*As with a DAD, the RAD is a combination of two RootishArrayStacks
*The first being "inverted", and the second normal

*NOTE: This requires a  RootishArratStack class to work
				which has not been included


 */
public class RootishArrayDeque<T> extends AbstractList<T> {
	RootishArrayStack<T> r1;
	RootishArrayStack<T> r2;
	Factory<T> fac;

	public RootishArrayDeque(Class<T> t) {
		//create the RootishArrayStacks to be utizized
		r1 = new RootishArrayStack<T>(t);
		r2 = new RootishArrayStack<T>(t);
		fac = new Factory<T>(t);
	}

	public T get(int i) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		if(i<r1.size())
			return(r1.get(r1.size()-i-1));	//get from the first block (inverted)
		return(r2.get(i-r1.size()));			//get from the second block (normal)
	}

	public T set(int i, T x) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		if(i<r1.size())
			return r1.set(r1.size()-i-1,x);	//set to the first block (inverted)
		return r2.set(i-r1.size(),x);		//set to the second block (normal);

	}

	public void add(int i, T x) {
		if (i < 0 || i > size()) throw new IndexOutOfBoundsException();
		if(r1.size()*2<r2.size())
			balance();
		if(i<r1.size() || r1.size()==0)	//ensure we add at least once!
			r1.add(r1.size()-i,x);	//add to the first block at pos corresponding to i
		else
			r2.add(i-r1.size(),x);		//add to the second block at pos corresponding to i
	}

	public T remove(int i) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		if(r1.size()*3<r2.size() || r1.size()==0 || r2.size()==0)	//ensure elements are at the back
			balance();
		if(i<r1.size())
			return r1.remove(r1.size()-i-1);	//remove from first at pos corresponding to i
		return r2.remove(i-r1.size());	//remove from secon at pos corresponding to i
	}
	public void balance() {
		RootishArrayStack<T> new1 = new RootishArrayStack<T>(fac.type());
		RootishArrayStack<T> new2 = new RootishArrayStack<T>(fac.type());
		for(int i=size()/2; i>=0; i--)
			new1.add(new1.size(),get(i));	//append to the back of RAStack1, working backwards
		for(int i=(size()/2)+1; i<size(); i++)
			new2.add(new2.size(),get(i));	//append to the back of RAStack2
		r1 = new1;
		r2 = new2;
	}

	public int size() {
		return (r1.size() + r2.size());
	}

	public void print() {
		for(int i=0; i<size(); i++)
			System.out.print(get(i) + " ");
		System.out.println();
	}

	public static void main(String[] args) {
		//List<Integer> rad = new ArrayDeque<Integer>(Integer.class);
		RootishArrayDeque<Integer> rad = new RootishArrayDeque<Integer>(Integer.class);

}
