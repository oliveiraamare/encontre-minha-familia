package enums;

public enum TipoUsuario {
	
	admin,
	contribuidor,
	moderador;
	
	 public static TipoUsuario getTipoUsuario(String tipoUsuario){
         for(TipoUsuario tipo:TipoUsuario.values()){
        	 
             if(tipo.toString().equals(tipoUsuario)){
                 return tipo;
             }
             
         }
         
         return null;
     }
}