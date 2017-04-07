

import java.util.List;
import java.util.AbstractList;
import java.util.ArrayList;

public class CircularArrayStack<T>{
  T[] arr;
  int n;                //Current # of elements
  int front; int back;  //front and back index
  Factory<T> fac;       //factory used to create generic arrays
  //use .length to get the array size

  public CircularArrayStack(Class<T> t) {
    fac = new Factory<T>(t);
    arr = fac.newArray(64);   //64 is the default starting value
    front = back = n = 0;
  }
  public CircularArrayStack(Class<T> t, int size) {
    fac = new Factory<T>(t);
    arr = fac.newArray(size);   //64 is the default starting value
    front = back = n = 0;
  }
  public T get(int i) {   //retun item at index i, carefull of negatives
    return arr[(i+arr.length)%arr.length];
  }
  public int size() {     //return current # of elements
    return n;
  }
  public void set(int i, T x) { //set item at index i, carefull of negatives
    arr[(i+arr.length)%arr.length] = x;
  }
  public void resize() {
    T[] newArr = fac.newArray(arr.length*2);   //new array twice the old size
    int j = front;
    int i = 0;
    while(j!=back) {  //copy elements to new array, starting at 0
      newArr[i] = get(j);
      j = (j+1)%arr.length;
      i++;
    }
    newArr[i] = get(back);  //don't forget the last element!
    front = 0;    //finally, don't forget to update indices!
    back = i;
    arr = newArr; //now the new array is the one under our class
  }
  public void add(int i, T x) {   //add at position i in relation to the front
    if(this.size()>arr.length-1)
      resize();
    if(n==0) {
      arr[front] = x;
      n++;
      return;
    }
    if(i<this.size()/2) {  //add to front, shifting elements left
      front = (front+arr.length-1)%arr.length;  //decrement front by 1
      int j = front;
      while(j!=(front+i)%arr.length) {          //shift elements to the left
        set(j,get(j+1));
        j = (j+1)%arr.length;                   //increment j by 1
      }
      set(j,x);
    }
    else {  //add to back, shifting elements right
      back = (back+arr.length+1)%arr.length;  //increment back by 1
      int j = back;
      while (j!=(front+i)%arr.length) {       //shift elements to the right
        set(j,get(j-1));
        j = (j+arr.length-1)%arr.length;      //decrement j by 1
      }
      set(j,x);
    }
    n++;
  }
  public T remove(int i) {
    if(this.size()==0)    //ensure there are elements in the array
      return null;
    T ret = get(front+i);
    if(i<this.size()/2) {
      int j = (front+i)%arr.length;     //start at the ith element
      while(j!=front) {
        set(j,get(j-1));                //shift elements right
        j = (j-1+arr.length)%arr.length;//decrement j
      }
      set(front,null);                  //set front to null
      n--;
      if(front==back)
        return ret;  //ensure bounds dont pass when final element removed
      front = (front+1)%arr.length;     //increment the front
    }
    else {
      int j = (front+i)%arr.length;           //start at the ith element
      while(j!=back) {
        set(j,get(j+1));                      //shift elements left
        j = (j+1)%arr.length;                 //increment j
      }
      set(back,null);                         //set end to null
      n--;
      if(front==back)
        return ret;  //ensure bounds dont pass when final element removed
      back = (back-1+arr.length)%arr.length;  //decrement the back
    }
    return ret;
  }
  public void printContents() {
    if(size()==0) {
      System.out.println("List empty!");
      return;
    }
    for(int i=0; i<size(); i++)
      System.out.print(get(front+i) + " ");
    System.out.println();
  }
  public static void main(String[] args) {
    CircularArrayStack<Integer> carr = new CircularArrayStack<Integer>(Integer.class);
    for(int i=0; i<3; i++)
      carr.add(0,i);

    carr.printContents();
    carr.remove(0);
    carr.printContents();
    carr.remove(0);
    carr.printContents();
    carr.remove(0);
    carr.printContents();

  }
}
