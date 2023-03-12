package com.invoice;

import java.awt.Component;

public class DynamicArray
{   
private Component array[];   
public int count;   
public int sizeofarray;   
//creating a constructor of the class that initializes the values  
public DynamicArray()   
{   
array = new Component[1];   
count = 0;   
sizeofarray = 1;   
}   
//creating a function that appends an element at the end of the array  
public void addElement(Component a)   
{   
//compares if the number of elements is equal to the size of the array or not  
if (count == sizeofarray)   
{   
//invoking the growSize() method that creates an array of double size      
growSize();   
}   
//appens an element at the end of the array   
array[count] = a;   
count++;   
}   
//function that creates an array of double size  
public void growSize()   
{   
//declares a temp[] array      
Component temp[] = null;   
if (count == sizeofarray)   
{   
//initialize a double size array of array  
temp = new Component[(sizeofarray +1)];   
{   
for (int i = 0; i < sizeofarray; i++)   
{   
//copies all the elements of the old array  
temp[i] = array[i];   
}   
}   
}   
array = temp;   
sizeofarray= sizeofarray +1;   
}   
//creating a function that deletes an element at the specified index  
public void addElementAt(int index, Component a)
{   
//compare the size with the number of elements if not equal grows the array size  
if (count == sizeofarray)   
{   
//invoking growSize() method  
growSize();   
}   
for (int i = count - 1; i >= index; i--)   
{   
//shifting all the elements to the left from the specified index  
array[i + 1] = array[i];   
}   
//inserts an element at the specified index  
array[index] = a;   
count++;   
} 
}
