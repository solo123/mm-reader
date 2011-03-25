
package com.caimeng.uilibray.utils;


public class ArrayList
{

    private Object storedObjects[];
    private int growthFactor;
    private int size;

    public ArrayList()
    {
        this(25,50);
    }

    public ArrayList(int i)
    {
        this(i, 75);
    }

    public ArrayList(int i, int j)
    {
        storedObjects = new Object[i];
        growthFactor = j;
    }

    public int size()
    {
        return size;
    }

    public boolean contains(Object obj)
    {
        if(obj == null) {
			throw new IllegalArgumentException("ArrayList cannot contain a null element.");
		}
        for(int i = 0; i < size; i++)
        {
            Object obj1 = storedObjects[i];
            if(obj1.equals(obj)) {
				return true;
			}
        }

        return false;
    }

    public int indexOf(Object obj)
    {
        if(obj == null) {
			throw new IllegalArgumentException("ArrayList cannot contain a null element.");
		}
        for(int i = 0; i < size; i++)
        {
            Object obj1 = storedObjects[i];
            if(obj1.equals(obj)) {
				return i;
			}
        }

        return -1;
    }

    public Object get(int i)
    {
        if((i < 0) || (i >= size)) {
			throw new IndexOutOfBoundsException("the index [" + i + "] is not valid for this list with the size [" + size + "].");
		} else {
			return storedObjects[i];
		}
    }

    public Object remove(int i)
    {
        if((i < 0) || (i >= size)) {
			throw new IndexOutOfBoundsException("the index [" + i + "] is not valid for this list with the size [" + size + "].");
		}
        Object obj = storedObjects[i];
        for(int j = i + 1; j < size; j++) {
			storedObjects[j - 1] = storedObjects[j];
		}

        size--;
        return obj;
    }

    public boolean remove(Object obj)
    {
        if(obj == null) {
			throw new IllegalArgumentException("ArrayList cannot contain null.");
		}
        int i = -1;
        int j = 0;
        do
        {
            if(j >= size) {
				break;
			}
            Object obj1 = storedObjects[j];
            if(obj1.equals(obj))
            {
                i = j;
                break;
            }
            j++;
        } while(true);
        if(i == -1) {
			return false;
		}
        for(int k = i + 1; k < size; k++) {
			storedObjects[k - 1] = storedObjects[k];
		}

        size--;
        return true;
    }

    public void clear()
    {
        for(int i = 0; i < size; i++) {
			storedObjects[i] = null;
		}
        size = 0;
    }

    public void add(Object obj)
    {
        if(obj == null) {
			throw new IllegalArgumentException("ArrayList cannot contain null.");
		}
        if(size >= storedObjects.length) {
			increaseCapacity();
		}
        storedObjects[size] = obj;
        size++;
    }

    public void add(int i, Object obj)
    {
        if(obj == null) {
			throw new IllegalArgumentException("ArrayList cannot contain null.");
		}
        if((i < 0) || (i >= size)) {
			throw new IndexOutOfBoundsException("the index [" + i + "] is not valid for this list with the size [" + size + "].");
		}
        if(size >= storedObjects.length) {
			increaseCapacity();
		}
        for(int j = size; j > i; j--) {
			storedObjects[j] = storedObjects[j - 1];
		}

        storedObjects[i] = obj;
        size++;
    }

    public Object set(int i, Object obj)
    {
        if((i < 0) || (i >= size))
        {
            throw new IndexOutOfBoundsException("the index [" + i + "] is not valid for this list with the size [" + size + "].");
        } else
        {
            Object obj1 = storedObjects[i];
            storedObjects[i] = obj;
            return obj1;
        }
    }

    public Object[] toArray()
    {
        Object aobj[] = new Object[size];
        System.arraycopy(((storedObjects)), 0, ((aobj)), 0, size);
        return aobj;
    }

    public Object[] toArray(Object aobj[])
    {
        System.arraycopy(((storedObjects)), 0, ((aobj)), 0, size);
        return aobj;
    }

    public void trimToSize()
    {
        if(storedObjects.length != size)
        {
            Object aobj[] = new Object[size];
            System.arraycopy(((storedObjects)), 0, ((aobj)), 0, size);
            storedObjects = aobj;
        }
    }

    private void increaseCapacity()
    {
        int i = storedObjects.length;
        int j = i + (i * growthFactor) / 100;
        if(j == i) {
			j++;
		}
        Object aobj[] = new Object[j];
        System.arraycopy(((storedObjects)), 0, ((aobj)), 0, size);
        storedObjects = aobj;
    }
}