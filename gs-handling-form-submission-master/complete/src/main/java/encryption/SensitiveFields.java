package encryption;

public class SensitiveFields {

    private String qty1, qty2, qty3;
    private String pr1, pr2, pr3;
    private String ccno;
    private String expiry;
    private String ccname;
    private String security;
    private String totalPrice;
    
    public String getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(String totalPrice) {
    	this.totalPrice=totalPrice;
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
    
    public String getQty1() {
        return qty1;
    }
    
    public void setQty1(String qty1) {
    	this.qty1=qty1;
    }
    
    public String getQty2() {
        return qty2;
    }
    
    public void setQty2(String qty2) {
    	this.qty2=qty2;
    }
    
    public String getQty3() {
        return qty3;
    }
    
    public void setQty3(String qty3) {
    	this.qty3=qty3;
    }
    
    public String getPr1() {
        return pr1;
    }
    
    public void setPr1(String pr1) {
    	this.pr1=pr1;
    }
    
    public String getPr2() {
        return pr2;
    }
    
    public void setPr2(String pr2) {
    	this.pr2=pr2;
    }
    
    public String getPr3() {
        return pr3;
    }
    
    public void setPr3(String pr3) {
    	this.pr3=pr3;
    }
    
    public String getSecurity() {
        return security;
    }
    
    public void setSecurity(String security) {
    	this.security=security;
    }
    
    public String getCcno() {
        return ccno;
    }
    
    public void setCcno(String ccno) {
    	this.ccno=ccno;
    }
    
}
