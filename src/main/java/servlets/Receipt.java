package servlets;
import java.util.*;
public class Receipt {
    /* Instance Variables  */

    Vector items;
    Vector prices;
    Vector quantities;
    public static void main(String args[]) {
        /* Program statements go here. */
        Receipt myReceipt=new Receipt();

        System.out.println("Welcome to Vegetables Service Engine");
        myReceipt.receive();
        myReceipt.sort();
        myReceipt.print();
    }

    public Receipt(){
        items=new Vector();
        prices=new Vector();
        quantities=new Vector();
    }
    public void receive() {
        String item=" ";
        Float price;
        Integer quantity;
        Scanner keyboard = new Scanner(System.in);
        while (!item.equals("")) {
            System.out.print("Please enter Item: ");
            System Keyboard = null;
            item=Keyboard.in.readstring();
            if (!item.equals("")) {
                this.items.addElement(item);
                System.out.print("Please enter price: ");
                price=Keyboard.in.readFloat();
                this.prices.addElement(price);
                System.out.print("Please enter quantity: ");
                quantity=Keyboard.in.readInteger();
                this.quantities.addElement(quantity);
            }
        }
    }
    public void sort() {
        int i;
        int largeIndex;
        for (i=0; i< this.items.size(); i++) {
            largeIndex = this.getLargest(i);
            this.exchange(i, largeIndex);
        }
    }

    private int getLargest(int index){
        int		largestIndex;
        int		i;
        Float price1, price2;

        largestIndex = index;
        for (i = index + 1; i < this.prices.size(); i++){
            price1=(Float)this.prices.elementAt(i);
            price2=(Float)this.prices.elementAt(largestIndex);
            if (price1.floatValue() > price2.floatValue())
                largestIndex = i;
        }
        return largestIndex;
    }

    private void exchange(int a, int b) {
        Float		tempF;
        Integer		tempI;
        String		tempS;

        tempS = (String)this.items.elementAt(a);
        tempF = (Float)this.prices.elementAt(a);
        tempI = (Integer)this.quantities.elementAt(a);

        this.items.setElementAt((String)this.items.elementAt(b),a);
        this.items.setElementAt(tempS,b);

        this.prices.setElementAt((Float)this.prices.elementAt(b),a);
        this.prices.setElementAt(tempF,b);

        this.quantities.setElementAt((Integer)this.quantities.elementAt(b),a);
        this.quantities.setElementAt(tempI,b);

    }

    public void print() {
        Date today=new Date();
        String item=" ";
        Float price;
        final float discount=100.0f;
        Integer quantity;
        float itemtotal;
        float subTotal=0f;
        float tax;
        float theDiscount=0f;
        int i,j,maxSize;
        /*printing the header of the receipt*/
        System.out.println("======================");
        System.out.println("     MEGA  STORE");
        System.out.print(today.getDate()+"/");
        System.out.print((today.getMonth()+1)+"/");
        System.out.print((today.getYear()+1900)+" ");
        System.out.print(today.getHours()+":");
        System.out.println(today.getMinutes());
        System.out.println("======================");

        /*counting the size of the longest item*/
        item=(String)this.items.elementAt(0);
        maxSize=item.length();
        for (i=1;i<this.items.size();i++){
            item=(String)this.items.elementAt(i);
            if (maxSize<item.length()) {
                maxSize=item.length();
            }
        }
        /*printing the lines with items*/
        for (i=0;i<this.items.size();i++){
            item=(String)this.items.elementAt(i);
            price=(Float)this.prices.elementAt(i);
            quantity=(Integer)this.quantities.elementAt(i);
            System.out.print(item);
            /*adding spaces to match the longest item*/
            for (j=item.length();j<maxSize;j++){
                System.out.print(" ");
            }
            System.out.print("  "+price+"  "+quantity+"  ");
            itemtotal=price.floatValue()*quantity.intValue();
            subTotal=subTotal+itemtotal;
            System.out.println(itemtotal);
        }
        /*printing the footer of the receipt*/
        System.out.println("======================");
        tax=subTotal*0.07f;
        System.out.println("SubTotal= $"+subTotal);
        System.out.println("GST     = $"+tax);
        if (subTotal>=discount) {
            theDiscount=subTotal*0.05f;
            System.out.println("Discount= $"+theDiscount);
        }
        System.out.println("Total   = $"+(subTotal+tax-theDiscount));

        System.out.println("======================");

    }
}

