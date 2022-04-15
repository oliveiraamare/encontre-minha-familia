package enums;

public enum Situacao {
	
	desconhecido,
	utilizaAbrigo,
	emSituacaoDeRua;

	public static Situacao getSituacao(String situacao){
        for(Situacao sit:Situacao.values()){
       	 
            if(sit.toString().equals(situacao)){
                return sit;
            }
            
        }
        
        return null;
    }
}
