package enums;

public enum StatusFeedback {
	
	otima,
	boa,
	ruim,
	pessima;

	public static StatusFeedback getStatusFeedback(String statusFeedback){
        for(StatusFeedback status:StatusFeedback.values()){
       	 
            if(status.toString().equals(statusFeedback)){
                return status;
            }
            
        }
        
        return null;
    }
}
