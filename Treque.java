
import java.util.ArrayList;
import java.util.AbstractList;
import java.util.List;

/*
 *Requies the CircualarArrayStack class
 */

public class Treque<T> extends AbstractList<T> {
	CircularArrayStack<T> b1;
	CircularArrayStack<T> b2;

	public Treque(Class<T> t) {
		//initialize the two CircularArrayStacks
		//pass type t to init a factory of the called type in each one
		this.b1 = new CircularArrayStack<T>(t);
		this.b2 = new CircularArrayStack<T>(t);
	}

	public T get(int i) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		if(i<b1.size())
			return b1.get(b1.front+i);	//remember out get(i) requires front as ref
		else
			return b2.get(b2.front+(size()-1-i));
	}

	public T set(int i, T x) {
		if (i < 0 || i > size()) throw new IndexOutOfBoundsException();
		if(i<b1.size())
			b1.set(b1.front+i,x);		//remember out set(i,x) requires front as ref
		else
			b2.set(b2.front+(size()-1-i),x);
		return x;
	}

	public void add(int i, T x) {
		if (i < 0 || i > size()) throw new IndexOutOfBoundsException();

		/*
		* if we add to the mid, first ensure the stacks are balanced
		* if the fisr is smaller, add the the end of the first
		* othersize, add to the back ot the second
		*	this ensures the arrays stay balanced througout the midpending\
		*	just like the removal stays even
		*/
		if(i==size()/2 && i!=0) {
			if(Math.abs(b1.size()-b2.size())>1)
				balance();		//needs to start with balanced arrays, b1<=b2
			if(b1.size()<b2.size())
				b1.add(b1.size(),x);	//add to b1 at the back
			else
				b2.add(b2.size(),x);	//add to b2 at the back
			return;
		}

		if((b1.size()>b2.size()*2) || (b2.size()>b1.size()*2))	//check if resize required
			balance();			//when removing from the ends. avoid calling often

		if(i<b1.size() || i==0)	//want to get the first stack started
			b1.add(i,x);	//add takes the front into account, don't add f
		else
			b2.add(size()-i,x);
	}

	public T remove(int i) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		if(i>size()/4 && i<3*size()/4 && Math.abs(b1.size()-b2.size())>1000)
			balance();		//rezise as required if removing from the middle
		T ret;
		if(i<b1.size())
			ret = b1.remove(i);	//remove takes the front into account, don't add f
		else
			ret = b2.remove(size()-i-1);

		return ret;
	}

	public int size() {
		return (b1.size() + b2.size());		//size is # of elements
	}
	public void balance() {	//need to know the type
		Class<T> t = b1.fac.type();
		//double the size of each, to avoid resizing for a while
		CircularArrayStack<T> newb1 = new CircularArrayStack<T>(t,size()*2);
		CircularArrayStack<T> newb2 = new CircularArrayStack<T>(t,size()*2);
		for(int j=0; j<this.size()/2; j++)	//add half to the first array
			newb1.add(newb1.size(),this.get(j));	//add to the BACK of the first block
		for(int j=(this.size()/2); j<this.size(); j++)
			newb2.add(0,this.get(j));		//add to the FRONT of the second block
		this.b1 = newb1;
		this.b2 = newb2;		//update the blocks
	}

	public void printContents() {
		for(int i=0; i<size(); i++)
			System.out.print(get(i) + " ");
		System.out.println();
	}

	public static void main(String[] args) {
		//Treque<Integer> tr = new Treque<Integer>(Integer.class);


	}
}
