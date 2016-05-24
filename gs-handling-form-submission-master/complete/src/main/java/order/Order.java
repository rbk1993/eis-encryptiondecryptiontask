package order;

import mail.mailCredentials;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Max;

public class Order {

	//Client shipping details
	@Size(min=1, max=50)
	@Pattern(regexp = "^(?:(?!\\$).)*$\r?\n?", message = "'$' character forbidden")
    private String name;
	@Size(min=1, max=100)
	@Pattern(regexp = "^(?:(?!\\$).)*$\r?\n?", message = "'$' character forbidden")
    private String addr1;
	@Size(min=1, max=100)
	@Pattern(regexp = "^(?:(?!\\$).)*$\r?\n?", message = "'$' character forbidden")
    private String addr2;
	@Size(min=1, max=100)
	@Pattern(regexp = "^(?:(?!\\$).)*$\r?\n?", message = "'$' character forbidden")
    private String addr3;
	@NotNull
	@Min(1000)
	@Max(99999)
    private Integer post;
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Please write a valid email (example@mailbox.com)")
    private String clientEmail;
    
    //Transaction details
    private String transid;
    private String transdate;
    
    //Items details
    private String item1, item2, item3;
    @NotNull
    @Min(0)
    @Max(10)
    private Integer qty1;
    @NotNull
    @Min(0)
    @Max(10)
    private Integer qty2;
    @NotNull
    @Min(0)
    @Max(10)
    private Integer qty3;
    private final Integer pr1 = 3;
    private final Integer pr2 = 2;
    private final Integer pr3 = 5;
  
    //Credit card details
    @Size(min=8, max=30)
	@Pattern(regexp = "^(?:(?!\\$).)*$\r?\n?", message = "'$' character forbidden")
    private String ccno;
    @Size(min=1, max=100)
	@Pattern(regexp = "^(?:(?!\\$).)*$\r?\n?", message = "'$' character forbidden")
    private String expiry;
    @Size(min=1, max=100)
	@Pattern(regexp = "^(?:(?!\\$).)*$\r?\n?", message = "'$' character forbidden")
    private String ccname;
    @NotNull
    @Min(100)
    @Max(999)
    private Integer security;
    
    //Details for the transaction file
    private String keyCode;
    private Integer totalPrice;
    private String encryptedData;
    
    public String getEncryptedData(){
    	return encryptedData;
    }
    
    public void setEncryptedData(String encryptedData){
    	this.encryptedData = encryptedData;
    }
    
    public Integer getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(Integer totalPrice) {
    	this.totalPrice=totalPrice;
    }

    public String getKeyCode() {
        return keyCode;
    }
    
    public void setKeyCode(String keyCode) {
    	this.keyCode=keyCode;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
    	this.name=name;
    }
    
    public String getAddr1() {
        return addr1;
    }
    
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }
    
    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }
    
    public String getAddr3() {
        return addr3;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }
    
    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
    
    public String getTransdate() {
        return transdate;
    }
    
    public void setTransdate(String transdate) {
    	this.transdate=transdate;
    }
    
    public String getItem1() {
        return item1;
    }
    
    public void setItem1(String item1) {
    	this.item1=item1;
    }
    
    public String getItem2() {
        return item2;
    }
    
    public void setItem2(String item2) {
    	this.item2=item2;
    }
    
    public String getItem3() {
        return item3;
    }
    
    public void setItem3(String item3) {
    	this.item3=item3;
    }
    
    public String getCcname() {
        return ccname;
    }
    
    public void setCcname(String ccname) {
    	this.ccname=ccname;
    }
    
    public String getExpiry() {
        return expiry;
    }
    
    public void setExpiry(String expiry) {
    	this.expiry=expiry;
    }
    
    public Integer getQty1() {
        return qty1;
    }
    
    public void setQty1(Integer qty1) {
    	this.qty1=qty1;
    }
    
    public Integer getQty2() {
        return qty2;
    }
    
    public void setQty2(Integer qty2) {
    	this.qty2=qty2;
    }
    
    public Integer getQty3() {
        return qty3;
    }
    
    public void setQty3(Integer qty3) {
    	this.qty3=qty3;
    }
    
    public Integer getPr1() {
        return pr1;
    }
    
    public Integer getPr2() {
        return pr2;
    }
    
    public Integer getPr3() {
        return pr3;
    }
    
    public Integer getSecurity() {
        return security;
    }
    
    public void setSecurity(Integer security) {
    	this.security=security;
    }
    
    public Integer getPost() {
        return post;
    }
    
    public void setPost(Integer post) {
    	this.post=post;
    }
    
    public String getTransid() {
        return transid;
    }
    
    public void setTransid(String transid) {
    	this.transid=transid;
    }
    
    public String getCcno() {
        return ccno;
    }
    
    public void setCcno(String ccno) {
    	this.ccno=ccno;
    }
    
}
