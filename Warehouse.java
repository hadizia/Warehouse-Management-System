//syed hadi zia rizvi 260775855
package assignment2;

public class Warehouse{

	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";
	
	public Warehouse(int n, int[] heights, int[] lengths){
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++){
			this.storage[i]= new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}
	
	public String printShipping(){
		Box b = toShip;
		String result = "not urgent : ";
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
 	public String print(){
 		String result = "";
		for (int i = 0; i < nbShelves; i++){
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}
	
 	public void clear(){
 		toShip = null;
 		toShipUrgently = null;
 		for (int i = 0; i < nbShelves ; i++){
 			storage[i].clear();
 		}
 	}
 	
 	/**
 	 * initiate the merge sort algorithm
 	 */
	public void sort(){
		mergeSort(0, nbShelves -1);
	}
	
	/**
	 * performs the induction step of the merge sort algorithm
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end)
	{

		if( start<end) {
		int mid = start+(end-start)/2;
		mergeSort(start, mid);
		mergeSort(mid+1, end);
		merge(start, mid, end);
	}
	}
	/**
	 * performs the merge part of the merge sort algorithm
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end)
	{
		Shelf left[] = new Shelf[mid-start+1];
		Shelf right[] = new Shelf[end-mid];
		for (int i=0; i<left.length; i++)
		{
			left[i]=storage[start+i];
		}
		
		for (int j=0; j<right.length; j++)
		{
			right[j] = storage[mid+1+j];
		}
		
		int i=0, j=0,k=start;
		while(i < left.length && j < right.length) {
			if (left[i].height<=right[j].height)
			{
				storage[k]=left[i];
				k++;
				i++;
			}	
			else {
				storage[k] = right[j];
				k++;
				j++;
			}

		}
		while (i < left.length) {
			storage[k] = left[i];
			k++;
			i++;
		}
		while(j < right.length) {
			storage[k] = right[j];
			k++;
			j++;
		}
		
	}

	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves >0)
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox (Box b){
		
		if(b.height>storage[(nbShelves)-1].height) {
			return problem;
		}
		boolean added = false;
		
		for(int i=0; (i<nbShelves) && added==false; i++) {
			if(b.height<=storage[i].height && b.length<=storage[i].availableLength) {
				storage[i].addBox(b);
				added=true;
			}
		    
		}if(added==true) {
			return noProblem;
		}else return problem;
		
		
	}

	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip (Box b){
		//ADD YOUR CODE HERE
		if(b instanceof UrgentBox){
			//add to beginning of toShipUrgently
			if(toShipUrgently==null) {
			    toShipUrgently=(UrgentBox) b;
			    toShipUrgently.next=toShipUrgently.previous=null;
			    
			}else{
				
				toShipUrgently.previous=b;
				b.next=toShipUrgently;
				b.previous=null;
				toShipUrgently=(UrgentBox) b;
				
			}
			return noProblem;
		}
		else if (b instanceof Box ) {
			//add to beginning of toShip list
			if(toShip==null) {
			    toShip= b;
			    toShip.next=toShip.previous=null;
			    
			}else {
				//Box current= toShip;
				toShip.previous=b;
				b.next=toShip;
				b.previous=null;
				toShip=b;
				
			}
			return noProblem;
		}
		
	    return problem;
	}
	
	/**
	 * Find a box with the identifier (if it exists)
	 * Remove the box from its corresponding shelf
	 * Add it to its corresponding shipping list
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox (String identifier){
		
	    int j=0;
	    Box ToShip=null;
	    Boolean notfound = true;
	    while(j<nbShelves && notfound) {
	    		ToShip = storage[j].removeBox(identifier);
	    		
	    		if(ToShip!=null) {
	    			if(ToShip.id==identifier) {
	    				notfound=false;
	    			}
	    		}
	    		j++;
	     }
	  
	    if(ToShip==null) {
	    	  return problem;
	    	 
	    }else {
	    	  return addToShip(ToShip);
	    }
		
		
	}
	
	/**
	 * if there is a better shelf for the box, moves the box to the optimal shelf.
	 * If there are none, do not do anything
	 * @param b
	 * @param position
	 */
	public void moveOneBox (Box b, int position){
		//ADD YOUR CODE HERE
		Boolean change= false;
		for(int x=0; (x<position && change==false); x++) {
			if((storage[x].height >= b.height) && (storage[x].availableLength >= b.length)) {
				storage[x].addBox(b);
				storage[position].removeBox(b.id);
				change=true;
			}
		}
	}
	
	/**
	 * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
	 */
	public void reorganize (){
		//ADD YOUR CODE HERE
		Box now=storage[0].firstBox;
		for(int i=0; i < nbShelves ; i++) {
			now= storage[i].firstBox;
			if(now!=null) {
				while(now!=null) {
					Box nextToNow=now.next;
			    			moveOneBox(now,i);
			    			now=nextToNow;		
			    }
			}
			
	   }
	}
}