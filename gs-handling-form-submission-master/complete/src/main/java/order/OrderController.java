package order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import decryption.Decryption;
import encryption.*;
import mail.*;
import net.iharder.Base64;

@Controller
public class OrderController extends WebMvcConfigurerAdapter {

	String keyCode = "";
	Integer transID = 1000;
	String filePath = "./transaction/";
	static byte[] prevArray;
	
	public static byte[] getPrevArray(){
		return prevArray;
	}
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/result").setViewName("result");
    }
	
    @RequestMapping(value="/retailer", method=RequestMethod.GET)
    public String retailerDecrypt(Model model) {
    	model.addAttribute("order", new Order());
        return "decryption";
    }
    
    @RequestMapping(value="/order", method=RequestMethod.GET)
    public String orderForm(Order order) {
        return "order";
    }
    
    @RequestMapping(value="/retailer", method=RequestMethod.POST)
    public String retailerDecryptResult(@ModelAttribute Order order, Model model) throws IOException{
    	
    	model.addAttribute(order);
    	
    	String encryptedData = order.getEncryptedData();
    	
		String[] arrayOfFields = Decryption.decryptMessage(encryptedData);
	
		Decryption.arrayToOrder(order,arrayOfFields);

    	return "decryptionresult";
    }

    @RequestMapping(value="/order", method=RequestMethod.POST)
    public String orderSubmit(@Valid Order order, BindingResult bindingResult) throws FileNotFoundException {
        
        if (bindingResult.hasErrors()) {
            return "order";
        }
    	
        orderEncrypt(order);
        orderStamp(order);
        String fileName = orderWrapInTransactionFile(order);
        
        String mailBodyForClient =
        		"Dear "+order.getName()+", \n"
        		+ "\n"
        		+ "Your order has been sent to the retailer \n"
        		+ "Please keep the following customer ticket as it may be asked by your retailer if any problem occurs: \n"
        		+ "Please note that the transaction file you received will be used by your retailer to process your order \n"
        		+ "\n"
        		+ "------------------ \n"
        		+ " - - - \n"
        		+ "ID: "+order.getTransid()+"\n"
        		+ "Date: "+order.getTransdate()+"\n"
        		+ " - - - \n"
        		+ "Shipping address: "+order.getAddr1()+"/"+order.getAddr2()+"/"+order.getAddr3()+"\n"
        		+ "Postal code: "+order.getPost()+"\n"
        		+ "------------------ \n"
        		+ "\n"
        		+ "Thank you, \n"
        		+ "Your Order Platform";
        
        String mailSubjectForClient = "Your order number "+order.getTransid();
        
        String mailBodyForRetailer =
        		"Dear retailer,\n"
        		+ "\n"
        		+ "A new order has been received\n"
        		+ "at the following date: "+order.getTransdate()+"\n"
        		+ "The ID of the order is: "+order.getTransid()+"\n"
        		+ "\n"
        		+ "------------------ \n"
        		+ "For security purpose, the transaction sensitive details"
        		+ "have been crypted and stored in the attached \n"+fileName+"\n"
        		+ "To decrypt it please use your web platform at localhost:8080/retailer \n"
        		+ "You will need to provide the transaction file \n"
        		+ "------------------ \n"
        		+ "Thank you, \n"
        		+ "Your Order Platform";
        
        String mailSubjectForRetailer = "New order received. Transaction number: "+order.getTransid();
        		
        SendEmail.sendMail(
        		order.getClientEmail(),
        		mailBodyForClient,
        		mailSubjectForClient,
        		filePath, fileName
        		);
        
        SendEmail.sendMail(
        		mailCredentials.retailerEmail,
        		mailBodyForRetailer,
        		mailSubjectForRetailer,
        		filePath, fileName
        		);
        
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
    	
    	compositeMessage += order.getItem1()+separator
    							+order.getQty1()+separator
    							+order.getPr1()+separator;

    	compositeMessage += order.getItem2()+separator
    						 	+order.getQty2()+separator
    						 	+order.getPr2()+separator;
    	
    	compositeMessage += order.getItem3()+separator
    							+order.getQty3()+separator
    							+order.getPr3()+separator;
    	
    	compositeMessage += order.getKeyCode()+separator;
    	compositeMessage += order.getEncryptedData();
    						
    	String fileName = order.getTransid()+".trans";
    	
    	PrintWriter out = new PrintWriter(filePath+fileName);
    
    	out.println(compositeMessage);
	    out.close();

    	return fileName;
    }

    /**
     * orderEncrypt(Order order) encrypts the sensitive fields of the Order POJO.
     *
     * @param Order The Order POJO (Plain Old Java Object) with its fields
     * @return void
     */
    public void orderEncrypt(Order order) {
    	
		/* Create a cipher using the first 16 bytes of the passphrase */
		TEA tea = new TEA(keyStringSelector().getBytes());
		byte[] original = order.getName().getBytes();

		/* Set keystring and Total price fields */
		order.setKeyCode(keyCode);
		order.setTotalPrice(	 order.getPr1()*order.getQty1()
								+order.getPr2()*order.getQty2()
								+order.getPr3()*order.getQty3()
						   );
		
		/* Encrypt the sensitive fields into a single colon */
		String singleColon = order.getCcno()+":"+order.getExpiry()+":"+order.getCcname()+":"+order.getSecurity()+":"+order.getTotalPrice();
		
		byte[] encryptedColon = tea.encrypt(singleColon.getBytes());
		
    	//BigInteger bigint = new BigInteger(encryptedColon);
		//order.setEncryptedData(bigint.toString());
		//order.setEncryptedData(new String(encryptedColon,StandardCharsets.UTF_8));
    	order.setEncryptedData(Base64.encodeBytes(encryptedColon));
    }
    
    /**
     * The keyStringSelector() selects the key to be used for TEA encryption in the shared
     * library. The index of the key is simply the seconds since midnight Mod 255.
     * It also sets the keyCode to be wrapped in the composite message. They chosen keyCode
     * is the string representation of the Calendar object (exact time of the day).
     * 
     * @param void
     * @return The key to be used for TEA encryption
     */
    public String keyStringSelector(){
    	
    	Calendar c = Calendar.getInstance();
    	long now = c.getTimeInMillis();
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	long passed = now - c.getTimeInMillis();
    	long secondsPassed = passed / 1000;

    	KeyStringSharedLibrary ksLib = new KeyStringSharedLibrary();
    	
    	keyCode = String.valueOf(secondsPassed);
    	
    	return ksLib.getKeyString((int)secondsPassed%255);
    }
}
