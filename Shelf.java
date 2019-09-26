//syed hadi zia rizvi 260775855
package assignment2;
public class Shelf {
	
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength){
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear(){
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print(){
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b){
		//ADD YOUR CODE HERE
		if (lastBox==null) {
			lastBox=b;
			firstBox=lastBox;
			
		}else {
			b.previous=lastBox;
			lastBox.next=b;
			lastBox=b;
			
		}
		availableLength-=b.length;
	}
	
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier){
		//ADD YOUR CODE HERE
		//if list is empty
		if(firstBox == null) {
			return null;
		}
		//if list has only one box
		if(firstBox==lastBox) {
			if(firstBox.id==identifier) {
			Box top=firstBox;
			firstBox=lastBox=null;
			availableLength = availableLength + top.length;
			top.next=top.previous = null;
			return top;
			}
		}
		//if firstbox needs to be removed
		else if(identifier == firstBox.id) {
			Box top1 = firstBox;
			firstBox = firstBox.next;
			if(firstBox != null) {
				firstBox.previous = null;
			 }
			availableLength = availableLength + top1.length;
			top1.next=top1.previous = null;
			return top1;
		}
		//if lastBox needs to be removed
		else if(identifier==lastBox.id) {
			Box end=lastBox;
			end.previous.next=null;
			lastBox=lastBox.previous;
			end.next=end.previous=null;
			availableLength = availableLength + end.length;
			return end;
		}else {
			
		
		//if box is between first and last
		
		Box atm= firstBox;
		while(atm.id!=identifier && atm!=lastBox) {
			atm=atm.next;
		}
		if(atm.id!=identifier) {
			return null;
		}
		atm.previous.next=atm.next;
		if(atm.next != null) {
			atm.next.previous=atm.previous;
		}
		atm.next=atm.previous=null;
		availableLength = availableLength + atm.length;
		return atm;
		}
	
		return null;
	}
	
}