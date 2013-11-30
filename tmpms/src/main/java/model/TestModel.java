package model;

public class TestModel extends BaseModel {

    private String id; //id
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>id<br>
	 * @return id
	 */
		
	public String getId(){
	   return id;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>id<br>
	 * @param id
	 */
	public void setId(String id){
	   this.id=id;
	}
    
	/**
	 * 
	 * <br>
	 * <b>功能：</b>重写<br>
	 * @return
	 */
    public String toString(){
	  return com.alibaba.fastjson.JSON.toJSONString(this);
	  // return ${SQL.toString}
    }
	
	///////////////////////////增加/////////////////////////
	
   

}
