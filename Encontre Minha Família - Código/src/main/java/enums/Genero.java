package enums;

public enum Genero {
	
	feminino,
	masculino;
	
	 public static Genero getGenero(String genero){
         for(Genero gen:Genero.values()){
        	 
             if(gen.toString().equals(genero)){
                 return gen;
             }
             
         }
         
         return null;
     }
}
