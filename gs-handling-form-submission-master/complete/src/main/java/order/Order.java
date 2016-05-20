package order;

public class Order {
	
    private String name;
    private String addr1;
    private String addr2;
    private String addr3;
    private Integer post;
    private String email;
    private String transid;
    private String transdate;
    private String item1, item2, item3;
    private Integer qty1, qty2, qty3;
    private final Integer pr1 = 3;
    private final Integer pr2 = 2;
    private final Integer pr3 = 5;
  
    //Credit card details
    private long ccno;
    private String expiry;
    private String ccname;
    private Integer security;
    
    
    private Integer keyCode;
    private Integer totalPrice;
    
    public Integer getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(Integer totalPrice) {
    	this.totalPrice=totalPrice;
    }

    public Integer getKeyCode() {
        return keyCode;
    }
    
    public void setKeyCode(Integer keyCode) {
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
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
    public long getCcno() {
        return ccno;
    }
    
    public void setCcno(long ccno) {
    	this.ccno=ccno;
    }
    
}
