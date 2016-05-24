package decryption;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import encryption.KeyStringSharedLibrary;
import encryption.TEA;
import net.iharder.Base64;
import order.Order;
import order.OrderController;

public class Decryption {

	public static String[] decryptMessage(String compositeMessage) throws IOException{
		
		System.out.println("Sent composite message = "+compositeMessage);
		String[] fields = compositeMessage.split("\\$");
		
    	//byte[] byteArray = new BigInteger(encryptedData).toByteArray();
		//byte[] byteArray = Base64.decode(encryptedData);
		
		KeyStringSharedLibrary ksLib = new KeyStringSharedLibrary();
		Integer keyStringIndex = Integer.valueOf(fields[16])%255;
		TEA tea = new TEA(ksLib.getKeyString(keyStringIndex).getBytes());
		
		byte[] teaCryptedBytes = Base64.decode(fields[17]);
		
		byte[] resultDecryption = tea.decrypt(teaCryptedBytes);
        String decryptionString = new String(resultDecryption);
        
        String[] sensitiveFields = decryptionString.split(":");
        
        String[] arrayOfFields = new String[fields.length+sensitiveFields.length-1];
        
        //Set the originally CLEAR fields in the array to return
        for(int i=0;i<fields.length-1;i++){
        	arrayOfFields[i]=fields[i];
        }
        
        //Now set the fields that were ENCRYPTED in the array to return
        for(int i=fields.length-1;i<arrayOfFields.length;i++){
        	arrayOfFields[i]=sensitiveFields[i-fields.length+1];
        }
        
        return arrayOfFields;
	}
	
	public static void arrayToOrder(Order order, String[] array) {
		
		int i=0;
		order.setName(array[i++]);
		order.setAddr1(array[i++]);
		order.setAddr2(array[i++]);
		order.setAddr3(array[i++]);
		order.setPost(Integer.valueOf(array[i++]));
		order.setTransid(array[i++]);
		order.setTransdate(array[i++]);
		order.setItem1(array[i++]);
		order.setQty1(Integer.valueOf(array[i++]));
		i++;
		order.setItem2(array[i++]);
		order.setQty2(Integer.valueOf(array[i++]));
		i++;
		order.setItem3(array[i++]);
		order.setQty3(Integer.valueOf(array[i++]));
		i++;
		order.setKeyCode(array[i++]);
		order.setCcno(array[i++]);
		order.setExpiry(array[i++]);
		order.setCcname(array[i++]);
		order.setSecurity(Integer.valueOf(array[i++]));
		order.setTotalPrice(Integer.valueOf(array[i++]));
	}
	
	
}
