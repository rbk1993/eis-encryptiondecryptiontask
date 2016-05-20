package order;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import encryption.*;
import mail.*;

@Controller
public class OrderController {

	SensitiveFields sensitiveFields = new SensitiveFields();
	Integer keyCode = 0;
	Integer transID = 1000;
	
    @RequestMapping(value="/order", method=RequestMethod.GET)
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "order";
    }

    @RequestMapping(value="/order", method=RequestMethod.POST)
    public String orderSubmit(@ModelAttribute Order order, Model model) throws FileNotFoundException {
        model.addAttribute("order", order);
        orderEncrypt(order);
        orderStamp(order);
        String compositeMessage = orderWrapInTransactionFile(order);
        
        String mailBody =
        		"Dear "+order.getName()+", \n"
        		+ "\n"
        		+ "Your order has ben received \n"
        		+ "Please keep the following ticket: \n"
        		+ "\n"
        		+ "------------------ \n"
        		+ " - - - \n"
        		+ "ID: "+order.getTransid()+"\n"
        		+ "Date: "+order.getTransdate()+"\n"
        		+ " - - - \n"
        		+ "Shipping address: "+order.getAddr1()+"/"+order.getAddr2()+"/"+order.getAddr3()+"\n"
        		+ "Postal code: "+order.getPost()+"\n"
        		+ " - - - \n"
        		+ "Credit Card no (Crypted): "+sensitiveFields.getCcno()+ "\n"
        		+ "Card holder name (Crypted): "+sensitiveFields.getCcname()+ "\n"
        		+ "Amount paid (Crypted): "+sensitiveFields.getTotalPrice()+ "\n"
        		+ "------------------ \n"
        		+ "\n"
        		+ "Composite message:\n"+compositeMessage+"\n"
        		+ "\n"
        		+ "Thank you, \n"
        		+ "Your RedBenk Groceries Team";
        
        sendOrderViaMail(order, mailBody);
        return "result";
    }

    public void orderStamp(Order order) {
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    	order.setTransdate(sdf.format(date));
    	order.setTransid(String.valueOf(++transID));
    }
    
    public String orderWrapInTransactionFile(Order order) throws FileNotFoundException {
    	
    	String compositeMessage = "";
    	String separator = "$";
    	compositeMessage +=  order.getName()+separator+order.getAddr1()+separator+order.getAddr2()+separator
    						+order.getAddr3()+separator+order.getPost()+separator+order.getTransid()+separator
    						+order.getTransdate()+separator;
    	
    	if(order.getQty1()>0) {
    		compositeMessage += order.getItem1()+separator
    							+sensitiveFields.getQty1()+separator
    							+sensitiveFields.getPr1()+separator;
    	} else {
    		compositeMessage += "nochoice";
    	}
    	
    	if(order.getQty2()>0) {
    		compositeMessage += order.getItem2()+separator
    						 	+sensitiveFields.getQty2()+separator
    						 	+sensitiveFields.getPr2()+separator;
    	} else {
    		compositeMessage += "nochoice";
    	}
    	
    	if(order.getQty3()>0) {
    		compositeMessage += order.getItem3()+separator
    							+sensitiveFields.getQty3()+separator
    							+sensitiveFields.getPr3()+separator;
    	} else {	
    		compositeMessage += "nochoice";
    	}
    	
    	compositeMessage += order.getKeyCode()+separator
    						+sensitiveFields.getCcno()+separator
    						+sensitiveFields.getExpiry()+separator
    						+sensitiveFields.getCcname()+separator
    						+sensitiveFields.getSecurity()+separator
    						+sensitiveFields.getTotalPrice();
    						
    	System.out.println("Composite Message "+compositeMessage);
    	
    	PrintWriter out = new PrintWriter("./transaction/"+order.getTransid()+".trans");
    	out.println(compositeMessage);
    	out.close();
    	
    	return compositeMessage;
    }

    public void orderEncrypt(Order order) {
    	
		/* Create a cipher using the first 16 bytes of the passphrase */
		TEA tea = new TEA(keyStringSelector().getBytes());
		byte[] original = order.getName().getBytes();
		
		/* Test : Run it through the cipher... and back */
		byte[] crypt = tea.encrypt(original);
		byte[] result = tea.decrypt(crypt);
		String testcrypt = new String(crypt); //Crypted Name (For test)
		String testdecrypt = new String(result); //Decrypted Name (For test)
		System.out.println("Crypted name = "+testcrypt);
		System.out.println("Decrypted name = "+testdecrypt);
		
		/* Set keystring and Total price fields */
		order.setKeyCode(keyCode);
		order.setTotalPrice(	 order.getPr1()*order.getQty1()
								+order.getPr2()*order.getQty2()
								+order.getPr3()*order.getQty3()
						   );
		
		/* Encrypt the desired fields */
		
		sensitiveFields.setPr1(new String(tea.encrypt(String.valueOf(order.getPr1()).getBytes())));
		sensitiveFields.setPr2(new String(tea.encrypt(order.getPr2().toString().getBytes())));
		sensitiveFields.setPr3(new String(tea.encrypt(order.getPr3().toString().getBytes())));
		sensitiveFields.setQty1(new String(tea.encrypt(order.getQty1().toString().getBytes())));
		sensitiveFields.setQty2(new String(tea.encrypt(order.getQty2().toString().getBytes())));
		sensitiveFields.setQty2(new String(tea.encrypt(order.getQty3().toString().getBytes())));
		sensitiveFields.setCcno(new String(tea.encrypt(String.valueOf(order.getCcno()).getBytes())));
		sensitiveFields.setSecurity(new String(tea.encrypt(order.getSecurity().toString().getBytes())));
		sensitiveFields.setTotalPrice(new String(tea.encrypt(order.getTotalPrice().toString().getBytes())));
		sensitiveFields.setCcname(new String(tea.encrypt(order.getCcname().getBytes())));
		sensitiveFields.setExpiry(new String(tea.encrypt(order.getExpiry().getBytes())));
    }
    
    public String keyStringSelector(){
    	Calendar c = Calendar.getInstance();
    	long now = c.getTimeInMillis();
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	long passed = now - c.getTimeInMillis();
    	long secondsPassed = passed / 1000;
    	System.out.println("keyStringSelector: Seconds passed since midnight = "+secondsPassed);
    	
    	KeyStringSharedLibrary ksLib = new KeyStringSharedLibrary();
    	
    	keyCode = (int)secondsPassed%255;
    	
    	
    	return ksLib.getKeyString((int)secondsPassed%255);
    }
   
    public void sendOrderViaMail(Order order, String mailBody) {
    	SendEmail.sendMail(RetailerCredentials.email,
    					   order.getEmail(),
    					   RetailerCredentials.password,
    					   "Your Order Crypted Receipt ID = "+order.getTransid(),
    					   mailBody);	
    }

}
